package com.softech.ls360.api.gateway.service.model.request;

public class SubscriptionEnrollemntLMSRequest {

	private String userName;
	private String courseId;
    private String subscriptionId;
    private String courseGroupGUID;
    private String classGuid;
    
    
	public String getClassGuid() {
		return classGuid;
	}
	public void setClassGuid(String classGuid) {
		this.classGuid = classGuid;
	}

    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getCourseGroupGUID() {
		return courseGroupGUID;
	}
	public void setCourseGroupGUID(String courseGroupGUID) {
		this.courseGroupGUID = courseGroupGUID;
	}
}
