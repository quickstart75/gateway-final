package com.softech.ls360.api.gateway.service.impl;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.SubscriptionEnrollmentService;
import com.softech.ls360.api.gateway.service.model.request.SubscriptionEnrollemntLMSRequest;
import com.softech.ls360.api.gateway.service.model.request.SubscriptionEnrollmentRequest;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionEnrollmentResponse;
import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.repositories.CourseRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;
import com.softech.ls360.util.api.call.RestApiCall;
import com.softech.ls360.util.json.JsonUtil;

@Service
public class SubscriptionEnrollmentServiceImpl implements SubscriptionEnrollmentService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private CourseRepository courseRepository;
	
	@Inject
	private SubscriptionRepository subscriptionRepository;

	@Value("${lms.subscription.enrollment}")
	private String enrollSubscriptionCourseURL;
	
	@Value("${lms.launch.course.url}")
	private String launchCourseURL;
	
	@Override
	public SubscriptionEnrollmentResponse enrollLearnerSubscriptionCourse(SubscriptionEnrollmentRequest request, String userName, String token) {
		// TODO Auto-generated method stub
		
		SubscriptionEnrollemntLMSRequest lmsRequest = new SubscriptionEnrollemntLMSRequest();
		SubscriptionEnrollmentResponse response = new SubscriptionEnrollmentResponse();
		
		String courseGUID = request.getCourseGuid();
		String courseGroupGUID = request.getCourseGroupGUID();
		String subscriptionCode = request.getSubscriptionCode()+"";
		String classGuid = request.getClassGuid()+"";
		
		Course course = courseRepository.findByCourseGuid(courseGUID);
		Long subscriptionId = null;
		Subscription subscription = subscriptionRepository.findBySubscriptionCodeAndSubscriptionStatus(subscriptionCode, "Active");
		if(subscription != null)
			subscriptionId = subscription.getId();
		
		if(subscriptionId != null){
			lmsRequest.setUserName(userName);
			lmsRequest.setCourseId(course.getId().toString());
			lmsRequest.setSubscriptionId(subscriptionId.toString());
			lmsRequest.setCourseGroupGUID(courseGroupGUID);
			lmsRequest.setClassGuid(classGuid);
			
			String requestJson = JsonUtil.convertObjectToJson(lmsRequest);
			
			try{
				String responseJson = RestApiCall.callPOST(enrollSubscriptionCourseURL, requestJson, token);
				response = JsonUtil.convertJsonToObject(responseJson, SubscriptionEnrollmentResponse.class);
				response.setLaunchURL(launchCourseURL+response.getEnrollmentId());
			}catch(Exception e){
				logger.info("Error:: " + e.getMessage());
			}
		}
		
		return response;
	}

}
