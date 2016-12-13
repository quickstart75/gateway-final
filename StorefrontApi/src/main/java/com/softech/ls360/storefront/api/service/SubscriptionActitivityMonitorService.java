package com.softech.ls360.storefront.api.service;

import java.util.List;

import com.softech.ls360.storefront.api.model.response.activitymonitor.SubscriptionActivityMonitorResponse;

public interface SubscriptionActitivityMonitorService {
	
	SubscriptionActivityMonitorResponse getSubscriptionActivityMonitorDetails(String storeId, List<String> subscriptionIds);

}
