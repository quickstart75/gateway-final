package com.softech.ls360.api.gateway.service.model.request;

import java.util.List;

public class UpdateEnrollmentStatusRequest {
	List<Long> enrollmentId;
	String status;
	
	public List<Long> getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(List<Long> enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
