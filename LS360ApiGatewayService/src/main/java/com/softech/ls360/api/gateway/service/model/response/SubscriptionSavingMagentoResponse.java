package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

public class SubscriptionSavingMagentoResponse {
	List<SubscriptionSavingMagentoCourses> subscription;
	List<SubscriptionSavingMagentoCourses> course;
	
	public List<SubscriptionSavingMagentoCourses> getSubscription() {
		return subscription;
	}
	public void setSubscription(List<SubscriptionSavingMagentoCourses> subscription) {
		this.subscription = subscription;
	}
	public List<SubscriptionSavingMagentoCourses> getCourse() {
		return course;
	}
	public void setCourse(List<SubscriptionSavingMagentoCourses> course) {
		this.course = course;
	}
	
	
}
