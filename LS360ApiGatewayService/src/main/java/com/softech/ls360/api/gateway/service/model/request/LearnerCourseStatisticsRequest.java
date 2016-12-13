package com.softech.ls360.api.gateway.service.model.request;
import java.util.ArrayList;
import java.util.List;

public class LearnerCourseStatisticsRequest {
	
	private String userName = null;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<Long> getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(List<Long> enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	private List<Long> enrollmentId = new ArrayList<Long>();

}
