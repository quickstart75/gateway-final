package com.softech.ls360.api.gateway.service.model.request;

import java.util.ArrayList;
import java.util.List;

public class LearnersEnrollmentRequest {
	
	private String sortBy;
	private String sortDirection;
	private int pageNumber;
	private int pageSize;
	private AttendanceFilter filter = new AttendanceFilter();
	
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public AttendanceFilter getFilter() {
		return filter;
	}
	public void setFilter(AttendanceFilter filter) {
		this.filter = filter;
	}
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	

}
