package com.softech.ls360.api.gateway.endpoint.restful;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.SubscriptionCourseService;
import com.softech.ls360.api.gateway.service.model.response.MySubscriptionResponse;
import com.softech.ls360.storefront.api.model.request.subscriptioncourses.SubscriptionCourseRequest;
import com.softech.ls360.storefront.api.model.response.activitymonitor.SubscriptionActivityMonitorResponse;

@RestEndpoint
@RequestMapping(value="/lms")
public class SubscriptionRestEndPoint {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private SubscriptionCourseService subscriptionCourseService;
	
	
	@RequestMapping(value = "/learner/subscription/activityMonitor", method = RequestMethod.GET)
	@ResponseBody
	public SubscriptionActivityMonitorResponse learnerClassroomDetails() throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner Subscription Activity Monitor");
		
		//get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		
		return subscriptionCourseService.getSubscriptionActivityMonitorDetails(userName);
	}
	
	@RequestMapping(value = "/learner/subscription/courses", method = RequestMethod.POST)
	@ResponseBody
	public MySubscriptionResponse mySubscriptionCourses(@RequestBody SubscriptionCourseRequest request) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for my Subscription Courses");
		
		//get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		
		return subscriptionCourseService.getSubscriptionCoursesWithFacets(userName, request);
			
	}

}
