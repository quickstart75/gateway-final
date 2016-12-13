package com.softech.ls360.api.gateway.model;

public class SubscriptionEnrollmentRequest {   

    private String courseGuid;
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

   
	
	

  
}

