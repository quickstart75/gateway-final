package com.softech.ls360.api.gateway.service;

import com.softech.ls360.api.gateway.service.model.response.MySubscriptionResponse;
import com.softech.ls360.storefront.api.model.request.subscriptioncourses.SubscriptionCourseRequest;
import com.softech.ls360.storefront.api.model.response.activitymonitor.SubscriptionActivityMonitorResponse;

public interface SubscriptionCourseService {
	
	SubscriptionActivityMonitorResponse getSubscriptionActivityMonitorDetails(String userName);
	
	MySubscriptionResponse getSubscriptionCoursesWithFacets(String userName, SubscriptionCourseRequest request);
}
