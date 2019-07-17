package com.softech.ls360.api.gateway.endpoint.restful.manager.analytics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
import com.softech.ls360.api.gateway.service.LearnerCourseStatisticsService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.model.response.EngagementTeamByMonthResponse;
import com.softech.ls360.api.gateway.service.model.response.FocusResponse;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsResponse;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionSavingResponse;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithCourseUserRest;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithUserRest;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;

@RestEndpoint
@RequestMapping(value="/lms/customer")
public class ManagerAnalyticsRestEndPoint {

	@Autowired
	private LearnerEnrollmentService learnerEnrollmentService;
	
	@Autowired
	private LearnerRepository learnerRepository;
	
	@Autowired
	private LearnerCourseStatisticsService learnerCourseStatisticsService;
	
	@Inject
	private CustomerService customerService;
	
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
}
