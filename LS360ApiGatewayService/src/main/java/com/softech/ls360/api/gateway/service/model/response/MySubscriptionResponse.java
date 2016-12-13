package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

import com.softech.ls360.storefront.api.model.response.subscriptioncourses.FacetView;

public class MySubscriptionResponse {
	
	private int pageNumber = 0;
	private int pageSize = 0;
	private int totalPages = 0;
	private List<FacetView> facetView;
	private List<SubscriptionCourse> subscriptionCourses;
	
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
	public List<SubscriptionCourse> getSubscriptionCourses() {
		return subscriptionCourses;
	}
	public void setSubscriptionCourses(List<SubscriptionCourse> subscriptionCourses) {
		this.subscriptionCourses = subscriptionCourses;
	}
	public List<FacetView> getFacetView() {
		return facetView;
	}
	public void setFacetView(List<FacetView> facetView) {
		this.facetView = facetView;
	}
}
