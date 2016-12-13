package com.softech.ls360.storefront.api.model.request.subscriptioncount;

import java.util.List;

public class SubscriptionCourseCountRequest {
	
	private List<String> subscriptionIds;

	public List<String> getSubscriptionIds() {
		return subscriptionIds;
	}

	public void setSubscriptionIds(List<String> subscriptionIds) {
		this.subscriptionIds = subscriptionIds;
	}
}
