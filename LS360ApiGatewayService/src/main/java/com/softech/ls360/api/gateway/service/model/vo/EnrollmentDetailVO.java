package com.softech.ls360.api.gateway.service.model.vo;

public class EnrollmentDetailVO {
	private String firstName="";
	private String lastName="";
	private String emailAddress="";
	private String courseName="";
	private String learnerStatisticsStatus="";
	private String learnerStatisticsCompletionDate="";
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lirstName) {
		this.lastName = lirstName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
	public String getLearnerStatisticsStatus() {
		return learnerStatisticsStatus;
	}
	public void setLearnerStatisticsStatus(String learnerStatisticsStatus) {
		this.learnerStatisticsStatus = learnerStatisticsStatus;
	}
	
	public String getLearnerStatisticsCompletionDate() {
		return learnerStatisticsCompletionDate;
	}
	public void setLearnerStatisticsCompletionDate(String learnerStatisticsCompletionDate) {
		this.learnerStatisticsCompletionDate = learnerStatisticsCompletionDate;
	}
	
}
