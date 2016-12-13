package com.softech.ls360.api.gateway.service;

import com.softech.ls360.api.gateway.service.model.request.SubscriptionEnrollmentRequest;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionEnrollmentResponse;


public interface SubscriptionEnrollmentService {
	
	SubscriptionEnrollmentResponse enrollLearnerSubscriptionCourse(SubscriptionEnrollmentRequest request, String userName, String token);

}
