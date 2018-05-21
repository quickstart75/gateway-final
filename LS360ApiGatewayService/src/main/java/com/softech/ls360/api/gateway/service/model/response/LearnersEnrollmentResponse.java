package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;
import java.util.Map;

public class LearnersEnrollmentResponse {
	private int pageNumber;
	private int pageSize;
	private int totalPages;
	
	
	Map<String, ClassInfo> classes;
	List<EnrollmentInfo> enrollmentInfo;
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
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public Map<String, ClassInfo> getClasses() {
		return classes;
	}
	public void setClasses(Map<String, ClassInfo> classes) {
		this.classes = classes;
	}
	public List<EnrollmentInfo> getEnrollmentInfo() {
		return enrollmentInfo;
	}
	public void setEnrollmentInfo(List<EnrollmentInfo> enrollmentInfo) {
		this.enrollmentInfo = enrollmentInfo;
	}
	
	

}
