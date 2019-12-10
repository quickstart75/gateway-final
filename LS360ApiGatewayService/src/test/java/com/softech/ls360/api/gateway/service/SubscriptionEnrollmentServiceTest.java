package com.softech.ls360.api.gateway.service;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.softech.ls360.api.gateway.service.config.spring.properties.ApiGatewayServicePropertiesConfig;
import com.softech.ls360.api.gateway.service.impl.EmailServiceImpl;
import com.softech.ls360.api.gateway.service.model.request.SubscriptionEnrollmentRequest;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionEnrollmentResponse;
import com.softech.ls360.util.json.JsonUtil;

public class SubscriptionEnrollmentServiceTest extends LS360ApiGatewayServiceAbstractTest {

	@Inject
	private SubscriptionEnrollmentService subscriptionEnrollmentService;

	@Autowired
	private ApiGatewayServicePropertiesConfig config;


	@Autowired
	private EmailServiceImpl emailService;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void enrollSubscriptionCourse(){
		SubscriptionEnrollmentRequest request = new SubscriptionEnrollmentRequest();
		String userName = "manager_learner@lms.com";
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE2LTExLTE5VDAzOjM3OjQxLjI3OCIsInVzZXJfbmFtZSI6Im1hbmFnZXJfbGVhcm5lckBsbXMuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9MRUFSTkVSIl0sImNsaWVudF9pZCI6IlRlc3RDbGllbnQiLCJzY29wZSI6WyJSRUFEIiwiVFJVU1QiLCJXUklURSJdfQ.vdhVv6j9Qj2KWx8u3MAtLiCVxaH6-5EIBur-wxJm6dI";
		String courseGroupGUID = "CE0E43FC0BDD40E0A3E47328327E3FCB";
		String courseGuid = "3F5D8322DE30473FB7DB6C063B3E068E";
		int subscriptionCode = 1300;
		request.setCourseGroupGUID(courseGroupGUID);
		request.setCourseGuid(courseGuid);
		request.setSubscriptionCode(subscriptionCode);
		System.out.println(JsonUtil.convertObjectToJson(request));
		SubscriptionEnrollmentResponse response = subscriptionEnrollmentService.enrollLearnerSubscriptionCourse(request, userName, token);
	}

}
