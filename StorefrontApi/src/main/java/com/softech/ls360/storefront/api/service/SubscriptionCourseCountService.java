package com.softech.ls360.storefront.api.service;

import java.util.List;

import com.softech.ls360.storefront.api.model.response.subscriptioncount.SubscriptionCourseCountResponse;


public interface SubscriptionCourseCountService {

	SubscriptionCourseCountResponse getProductSubscriptionCourseCount(String storeId, List<String> partNumbers);
	
}
