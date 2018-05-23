package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;
import java.util.Map;

public class LearnersEnrollmentResponse {
	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private long totalEnrollments;
	
	Map<String, ClassInfo> classes;
	List<EnrollmentInfo> enrollments;
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
	public List<EnrollmentInfo> getEnrollments() {
		return enrollments;
	}
	public void setEnrollments(List<EnrollmentInfo> enrollments) {
		this.enrollments = enrollments;
	}
	public long getTotalEnrollments() {
		return totalEnrollments;
	}
	public void setTotalEnrollments(long totalEnrollments) {
		this.totalEnrollments = totalEnrollments;
	}
}
