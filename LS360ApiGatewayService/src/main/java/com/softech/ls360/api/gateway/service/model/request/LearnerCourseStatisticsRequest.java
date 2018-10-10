package com.softech.ls360.api.gateway.service.model.request;


public class LearnerCourseStatisticsRequest {
	
	private String userName = null;
	private Long enrollmentId;
	private String completionDate;				
	private Long timeSpent;
	private Double score;					
	private Long percentComplete;
	private String status;					
	//String lastAccessDate
	//private String completed;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public String getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
	
	
	public Long getPercentComplete() {
		return percentComplete;
	}
	public void setPercentComplete(Long percentComplete) {
		this.percentComplete = percentComplete;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(Long timeSpent) {
		this.timeSpent = timeSpent;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
	

}
