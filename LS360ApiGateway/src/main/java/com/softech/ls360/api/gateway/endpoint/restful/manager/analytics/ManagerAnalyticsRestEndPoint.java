package com.softech.ls360.api.gateway.endpoint.restful.manager.analytics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.InformalLearningService;
import com.softech.ls360.api.gateway.service.LearnerCourseStatisticsService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.model.response.EngagementTeamByMonthResponse;
import com.softech.ls360.api.gateway.service.model.response.FocusResponse;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsResponse;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionSavingResponse;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithCourseUserRest;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithUserRest;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.InformalLearningActivity;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.InformalLearningActivityRepository;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;

@RestEndpoint
@RequestMapping(value="/lms/customer")
public class ManagerAnalyticsRestEndPoint {

	@Autowired
	private LearnerEnrollmentService learnerEnrollmentService;
	
	@Autowired
	private LearnerRepository learnerRepository;
	
	@Autowired
	private LearnerCourseStatisticsService learnerCourseStatisticsService;
	
	@Autowired
	private InformalLearningService informalLearningService;
	
	@Autowired
	private VU360UserRepository vu360UserRepository;
	
	@Inject
	private CustomerService customerService;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Value( "${megasite.distributor.id}" )
    private String megasiteDistributorId;
	
	@RequestMapping(value = "/roi-analytics", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> roiAnalytics() throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		
		ROIAnalyticsResponse result = learnerEnrollmentService.getROIAnalytics(learner.getCustomer().getId().longValue() , Long.valueOf(megasiteDistributorId) );
		
        map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", result);
		return map;
	}

	@RequestMapping(value = "/enrollementPersentageByTopic", method = RequestMethod.GET)
	@ResponseBody
	// for Focus widget 
	public Map<Object, Object>  sendEnrollementPersentageByTopic(){
		Map<Object, Object> map = new HashMap<Object, Object>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		List<String> lstCourseGuid = learnerEnrollmentService.getEnrolledCoursesGUIDByCustomer(learner.getCustomer().getId());
		
		List<FocusResponse> calculated = learnerEnrollmentService.getEnrolledCoursesPercentageByTopicByCustomer(learner.getCustomer().getId(), lstCourseGuid);
		
		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", calculated);
		return map;
	}
	
	@RequestMapping(value = "/subscriptionSaving", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	// for Focus widget 
	public Map<Object, Object>  subscriptionSaving(@RequestBody Map<String, Object> userGroup){
		Map<Object, Object> map = new HashMap<Object, Object>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		List<Integer> lstUserGroup = (List<Integer>)userGroup.get("userGroup");
		//get the customer's subscription code
		
		List<Long> lstuserGroup2 = new ArrayList<Long>();
		for (int i=0;i<lstUserGroup.size();++i) {
			lstuserGroup2.add(lstUserGroup.get(i).longValue());
		}
		
		SubscriptionSavingResponse objResponse = learnerEnrollmentService.getSubscriptionSavingStates(learner.getCustomer().getId(), lstuserGroup2);
		
		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", objResponse);
		return map;
	}
	
	
	@RequestMapping(value = "/engagement-team-bymonth", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	// Report 1- Team Engagement by Month 
	public Map<Object, Object>   EngagementTeamByMonth(){
		Map<Object, Object> map = new HashMap<Object, Object>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		Date st = new Date();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1); // to get previous year add -1
		Date previousYear = cal.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String endDate= sdf.format(st);
		String startDate  = sdf.format(previousYear);
		
		EngagementTeamByMonthResponse response = learnerCourseStatisticsService.LearnerGroupCourseStatisticsByMonth(learner.getCustomer().getId(), startDate, endDate);
		
		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", response);
		return map;
	}
	
	
	@RequestMapping(value = "/user-engagement-byteam", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	// Report 2: Total User Engagement by team
	public Map<Object, Object>   UserEngagementByTeam(){
		Map<Object, Object> map = new HashMap<Object, Object>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		List<UserGroupwithUserRest>  response = learnerCourseStatisticsService.getUsersTimespentByLearnerGroup(learner.getCustomer().getId());
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("userGroup", response);
		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", responseMap);
		return map;
	}
	
	
	@RequestMapping(value = "/engagement-team-bycourse", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	// Report 2: Total User Engagement by team
	public Map<Object, Object>   getCourseEngagementByTeam(){
		Map<Object, Object> map = new HashMap<Object, Object>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		List<UserGroupwithCourseUserRest>  response = learnerCourseStatisticsService.getCourseEngagementByTeam(learner.getCustomer().getId());
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("userGroup", response);
		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", responseMap);
		return map;
	}
	
	@RequestMapping(value = "/vpa-orders", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	// Report: 
	public Map<Object, Object>   getVPAReport(@RequestBody Map<String, Object> order){
		Map<Object, Object> map = new HashMap<Object, Object>();
		List  response;
		
		String managerusername = order.get("managerusername").toString();
		String vpaCode = order.get("vpaCode").toString();
		Object teamId = order.get("teamId");
		
		Customer customer = customerService.findByUsername(managerusername);
		
		if(teamId!=null && !teamId.toString().equals("")){
			response = customerService.getVPAOrdersByCustomer(vpaCode, customer.getId(), managerusername, teamId.toString());
		}else{
			response = customerService.getVPAOrdersByCustomer(vpaCode, customer.getId(), managerusername, "");
		}

		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", response);
		return map;
	}
	
	@RequestMapping(value = "/course-stats", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> learnerStatistics(@RequestBody Map<Object, Object> request){
		Map<Object, Object> responseBody = new HashMap<Object, Object>();
		
		try {
			String username = request.get("username").toString();
			String enrollmentStatus = (String) request.get("enrollmentStatus");
			Boolean withStats = (Boolean) request.get("withStats");
			Map<Object, Object> result=new HashMap<>();
			VU360User user = vu360UserRepository.findByUsername(username);
			
			if(username != null && enrollmentStatus != null && withStats != null && user!=null) {

				List<Object[]> enrolledCourses = null;
				
				if(enrollmentStatus.equals("completed"))
					enrolledCourses = learnerCourseStatisticsService.getLearnerCourseStatisticsByUsernameAndComplete(username);
					
				else if(enrollmentStatus.equals("enrolled"))
					enrolledCourses = learnerCourseStatisticsService.getLearnerCourseStatisticsByUsername(username);
				
				Map<Object, Object> states=new HashMap<>();
				Map<Object, Object> allCourse = new HashMap<>();
				
				enrolledCourses = enrolledCourses == null ?  new ArrayList<>() : enrolledCourses;
				
				int selfPlaced=0, classroom=0;
				
				for(Object[] record : enrolledCourses) {
					Map<String, Object> course = new HashMap<>();
					course.put("bussinessUnit", record[3]);
					course.put("name", record[0]);
					course.put("timespent", record[6]);
					
					Object orderStatus=record[5] == null ? "" : record[5];
							
					course.put("status", orderStatus.equals("pending") ? "pending" : record[4]);
					allCourse.put(record[1], course);
					
					if(record[2].toString().equalsIgnoreCase("Classroom Course")) 
						classroom += Integer.parseInt(record[6].toString());
					else
						selfPlaced += Integer.parseInt(record[6].toString());
					
			
				}
				result.put("courses", allCourse);
				
				if(withStats) {
					states.put("selfPaced", selfPlaced);
					states.put("classroom", classroom);
					
					Integer a=informalLearningService.getGetTimeInSecondsByUserId(user.getId());
					a=a==null ? 0 : a;
					Integer b=informalLearningService.getGetTimeInSecondsByUsername(user.getUsername());
					b=b==null ? 0 : b;
					states.put("informalLearning", a+b);
					result.put("states", states);
					
				}
				
				
			}
			else {
				responseBody.put("status", Boolean.TRUE);
		        responseBody.put("message", "Invalid data");
		        responseBody.put("result", "");
		        return responseBody;
			}
			responseBody.put("result", result);
			
			
			
		}catch (Exception e) {
			logger.info(">>>>>>>>>>>>>>>>>> EXCEPTION >>>>>>>>> :: learnerStatistics() ");
			logger.info(">>>>>>>>>>>>>>>>>> MESSAGE >>>>>>>>>>> " + e.getLocalizedMessage());
			logger.info(">>>>>>>>>>>>>>>>>> END ");
			responseBody.put("status", Boolean.FALSE);
	        responseBody.put("message", e.getMessage());
	        responseBody.put("result", "");
			e.printStackTrace();
		}
		
		return responseBody;
	}
}
