package com.softech.ls360.api.gateway.endpoint.restful.manager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.request.UserRequest;
import com.softech.ls360.api.gateway.response.UserAnalyticsResponse;
import com.softech.ls360.api.gateway.service.CourseService;
import com.softech.ls360.api.gateway.service.LearnerService;

@RestEndpoint
@RequestMapping(value="/lms/learner")
public class LearnerRestEndPoint {

	@Autowired
	private LearnerService learnerService;
	
	@Autowired
	private CourseService courseService;
	
	public final String COURSE_STATUS_COMPLETED = "completed";
	
	@RequestMapping(value = "/analytics-bycourse", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> getUserAnalytics2(@RequestBody UserRequest userRequest) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if(userRequest.getUsername()==null || userRequest.getUsername().equalsIgnoreCase("") || userRequest.getCourseguid()==null || userRequest.getCourseguid().size()==0)
			return map;
		
		List<Object[]> UserCourseAnalytics = learnerService.findUserCourseAnalyticsByUserNameByCourseGUIDs(userRequest.getUsername(), userRequest.getCourseguid());
		int totalViewTime = 0;
		int completedCount =0;
		double totalcourseDuration = 0;
		
		for(Object[]  objCE : UserCourseAnalytics){
        	try{
        		if(objCE[0]!=null)
        			totalViewTime += Long.valueOf(objCE[0].toString());
	        	
        		if((objCE[1]!=null && Boolean.parseBoolean(objCE[1].toString()))  &&  (objCE[2] !=null && objCE[2].toString().equalsIgnoreCase(COURSE_STATUS_COMPLETED)))
        			completedCount += 1;
        		
        	}catch(Exception ex){
        		
        	}
        }
		
		List<Object> lstCourse = courseService.getCourseByGUIDs(userRequest.getCourseguid());
		
		for (Iterator itr = lstCourse.iterator(); itr.hasNext();) {
        	try{
        		String objCE = itr.next().toString();
        		if(objCE!=null)
        			totalcourseDuration += Double.valueOf(objCE);
        	}catch(Exception ex){
        		System.out.println(ex);
        	}
        }
		
		
		UserAnalyticsResponse objuca = new UserAnalyticsResponse();
		objuca.setTotalViewTime(totalViewTime);
		objuca.setCompleteCourse(completedCount);
		objuca.setCareerPathTotalTime((int)(totalcourseDuration*60*60));
        map.put("status", Boolean.TRUE);
        map.put("result", objuca);
		return map;
	}

	
}
