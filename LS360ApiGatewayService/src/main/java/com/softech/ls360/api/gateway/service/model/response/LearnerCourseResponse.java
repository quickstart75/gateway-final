package com.softech.ls360.api.gateway.service.model.response;

import java.util.ArrayList;
import java.util.List;

public class LearnerCourseResponse {
	
	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private List<LearnerEnrollmentStatistics> learnerEnrollments = new ArrayList<LearnerEnrollmentStatistics>();
	private List<LearnerSubscription> subscriptions = new ArrayList<LearnerSubscription>();
	
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
	public List<LearnerEnrollmentStatistics> getLearnerEnrollments() {
		return learnerEnrollments;
	}
	public void setLearnerEnrollments(
			List<LearnerEnrollmentStatistics> learnerEnrollments) {
		this.learnerEnrollments = learnerEnrollments;
	}
	public List<LearnerSubscription> getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(List<LearnerSubscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

}
