package com.softech.ls360.api.gateway.service.model.response;

public class MOCDetail {
	private String type;
	private String enrollmentStatus;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEnrollmentStatus() {
		if(enrollmentStatus==null){
			return "Unassigned"; 
		}
		return enrollmentStatus;
	}
	public void setEnrollmentStatus(String enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}
}
