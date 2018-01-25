package com.softech.ls360.api.gateway.service.model.response;

import java.util.HashMap;

public class CourseTimeSpentResponse {

	private Long enrollmentId;
	private String courseName;
	private String courseType;
	private HashMap<String , Long> timeSpent = new HashMap<String , Long>();
	
	
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
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
