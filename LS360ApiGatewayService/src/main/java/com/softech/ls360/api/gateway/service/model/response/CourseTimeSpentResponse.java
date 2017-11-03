package com.softech.ls360.api.gateway.service.model.response;

import java.util.HashMap;

public class CourseTimeSpentResponse {

	private Long enrollmentId;
	private HashMap<String , Long> timeSpent = new HashMap<String , Long>();
	
	
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public HashMap<String, Long> getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(HashMap<String, Long> timeSpent) {
		this.timeSpent = timeSpent;
	}
	
	
	
}
