package com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentRestRequest {

	private List<String> userName = new ArrayList<String>();
	private List<String> courses = new ArrayList<String>();
	private List<String> subscription = new ArrayList<String>();
	private List<String> userGroups = new ArrayList<String>();
	private List<String> orgGroups = new ArrayList<String>();
	private Boolean notifyLearnersByEmail;
	private String duplicatesEnrollment;
	private String enrollmentStartDate;
	private String enrollmentEndDate;

	
	
	public List<String> getSubscription() {
		return subscription;
	}

	public void setSubscription(List<String> subscription) {
		this.subscription = subscription;
	}

	public List<String> getUserName() {
		return userName;
	}

	public void setUserName(List<String> userName) {
		this.userName = userName;
	}

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}

	public List<String> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<String> userGroups) {
		this.userGroups = userGroups;
	}

	public List<String> getOrgGroups() {
		return orgGroups;
	}

	public void setOrgGroups(List<String> orgGroups) {
		this.orgGroups = orgGroups;
	}

	public Boolean getNotifyLearnersByEmail() {
		return notifyLearnersByEmail;
	}

	public void setNotifyLearnersByEmail(Boolean notifyLearnersByEmail) {
		this.notifyLearnersByEmail = notifyLearnersByEmail;
	}

	public String getDuplicatesEnrollment() {
		return duplicatesEnrollment;
	}

	public void setDuplicatesEnrollment(String duplicatesEnrollment) {
		this.duplicatesEnrollment = duplicatesEnrollment;
	}

	public String getEnrollmentStartDate() {
		return enrollmentStartDate;
	}

	public void setEnrollmentStartDate(String enrollmentStartDate) {
		this.enrollmentStartDate = enrollmentStartDate;
	}

	public String getEnrollmentEndDate() {
		return enrollmentEndDate;
	}

	public void setEnrollmentEndDate(String enrollmentEndDate) {
		this.enrollmentEndDate = enrollmentEndDate;
	}

}
