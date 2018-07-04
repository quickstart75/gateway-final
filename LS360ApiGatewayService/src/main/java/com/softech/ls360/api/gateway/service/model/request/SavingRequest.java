package com.softech.ls360.api.gateway.service.model.request;

import java.util.Set;

public class SavingRequest {
	Set<Long> subscriptionCode;
	Set<String> courseGuid;
	Integer storeId;
	
	public Set<Long> getSubscriptionCode() {
		return subscriptionCode;
	}
	public void setSubscriptionCode(Set<Long> subscriptionCode) {
		this.subscriptionCode = subscriptionCode;
	}
	public Set<String> getCourseGuid() {
		return courseGuid;
	}
	public void setCourseGuid(Set<String> courseGuid) {
		this.courseGuid = courseGuid;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	
	
}
