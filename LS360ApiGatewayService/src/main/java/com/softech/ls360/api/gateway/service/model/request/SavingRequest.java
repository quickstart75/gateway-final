package com.softech.ls360.api.gateway.service.model.request;

import java.util.List;

public class SavingRequest {
	List<Long> subscriptionCode;
	List<String> courseGuid;
	Integer storeId;
	
	public List<Long> getSubscriptionCode() {
		return subscriptionCode;
	}
	public void setSubscriptionCode(List<Long> subscriptionCode) {
		this.subscriptionCode = subscriptionCode;
	}
	public List<String> getCourseGuid() {
		return courseGuid;
	}
	public void setCourseGuid(List<String> courseGuid) {
		this.courseGuid = courseGuid;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	
	
}
