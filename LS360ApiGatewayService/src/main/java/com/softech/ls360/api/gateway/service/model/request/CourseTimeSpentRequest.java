package com.softech.ls360.api.gateway.service.model.request;

import java.util.ArrayList;
import java.util.List;

public class CourseTimeSpentRequest {

	List<Long> enrollmentId = new ArrayList<Long>();
	Long latestCount ;
	String startDate;
	String endDate;

		
	public List<Long> getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(List<Long> enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public Long getLatestCount() {
		return latestCount;
	}
	public void setLatestCount(Long latestCount) {
		this.latestCount = latestCount;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
	
	
			
}
