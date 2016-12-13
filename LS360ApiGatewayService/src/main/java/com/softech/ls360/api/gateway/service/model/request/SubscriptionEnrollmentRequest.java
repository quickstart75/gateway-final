package com.softech.ls360.api.gateway.service.model.request;

public class SubscriptionEnrollmentRequest {   

    private String courseGuid;
    private String courseGroupGUID;
    private int subscriptionCode;
    
	public String getCourseGuid() {
		return courseGuid;
	}
	public void setCourseGuid(String courseGuid) {
		this.courseGuid = courseGuid;
	}
	public int getSubscriptionCode() {
		return subscriptionCode;
	}
	public void setSubscriptionCode(int subscriptionCode) {
		this.subscriptionCode = subscriptionCode;
	}
	public String getCourseGroupGUID() {
		return courseGroupGUID;
	}
	public void setCourseGroupGUID(String courseGroupGUID) {
		this.courseGroupGUID = courseGroupGUID;
	}
}

