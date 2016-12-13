package com.softech.ls360.storefront.api.model.request.subscriptioncourses;

import java.util.List;

public class SubscriptionCourseRequest {
	
	private List<String> subscriptionIds;
	private List<String> facetType;
	private String includeFacet;
	private String searchQuery;
	private String pNumber;
	private String pSize;
	private String sortOrder;
	private List<String> queryFacet;
	
	public List<String> getSubscriptionIds() {
		return subscriptionIds;
	}
	public void setSubscriptionIds(List<String> subscriptionIds) {
		this.subscriptionIds = subscriptionIds;
	}
	public List<String> getFacetType() {
		return facetType;
	}
	public void setFacetType(List<String> facetType) {
		this.facetType = facetType;
	}
	public String getIncludeFacet() {
		return includeFacet;
	}
	public void setIncludeFacet(String includeFacet) {
		this.includeFacet = includeFacet;
	}
	public String getSearchQuery() {
		return searchQuery;
	}
	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}
	public String getpNumber() {
		return pNumber;
	}
	public void setpNumber(String pNumber) {
		this.pNumber = pNumber;
	}
	public String getpSize() {
		return pSize;
	}
	public void setpSize(String pSize) {
		this.pSize = pSize;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public List<String> getQueryFacet() {
		return queryFacet;
	}
	public void setQueryFacet(List<String> queryFacet) {
		this.queryFacet = queryFacet;
	}
}
