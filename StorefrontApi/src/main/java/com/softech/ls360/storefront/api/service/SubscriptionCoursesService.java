package com.softech.ls360.storefront.api.service;

import com.softech.ls360.storefront.api.model.request.subscriptioncourses.SubscriptionCourseRequest;
import com.softech.ls360.storefront.api.model.response.subscriptioncourses.SubscriptionCourseResponse;

public interface SubscriptionCoursesService {
	
	SubscriptionCourseResponse getSubscriptionCourses(String storeId, SubscriptionCourseRequest request);
}
