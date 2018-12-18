package com.softech.ls360.api.gateway.service.model.request;

import java.util.List;
import java.util.Map;

public class LearnerRequest {
	private String firstName;
	private String lastName;
	private String email;
	private List<CourseDetail> enrollmentsDetail;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CourseDetail> getEnrollmentsDetail() {
		return enrollmentsDetail;
	}

	public void setEnrollmentsDetail(List<CourseDetail> enrollmentsDetail) {
		this.enrollmentsDetail = enrollmentsDetail;
	}
	
	
	
}
