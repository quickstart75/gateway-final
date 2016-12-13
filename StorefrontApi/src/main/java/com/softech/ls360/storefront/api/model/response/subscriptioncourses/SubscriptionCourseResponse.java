package com.softech.ls360.storefront.api.model.response.subscriptioncourses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionCourseResponse {
	
	private int recordSetTotalMatches = 0;
	private int recordSetTotal = 0;
	private String resourceName = "";
	private String resourceId = "";
	private int recordSetStartNumber = 0;
	private String recordSetComplete = "";
	private List<FacetView> facetView;
	private int recordSetCount = 0;
	private List<CatalogEntryView> catalogEntryView;
	public int getRecordSetTotalMatches() {
		return recordSetTotalMatches;
	}
	public void setRecordSetTotalMatches(int recordSetTotalMatches) {
		this.recordSetTotalMatches = recordSetTotalMatches;
	}
	public int getRecordSetTotal() {
		return recordSetTotal;
	}
	public void setRecordSetTotal(int recordSetTotal) {
		this.recordSetTotal = recordSetTotal;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public int getRecordSetStartNumber() {
		return recordSetStartNumber;
	}
	public void setRecordSetStartNumber(int recordSetStartNumber) {
		this.recordSetStartNumber = recordSetStartNumber;
	}
	public String getRecordSetComplete() {
		return recordSetComplete;
	}
	public void setRecordSetComplete(String recordSetComplete) {
		this.recordSetComplete = recordSetComplete;
	}
	public List<FacetView> getFacetView() {
		return facetView;
	}
	public void setFacetView(List<FacetView> facetView) {
		this.facetView = facetView;
	}
	public int getRecordSetCount() {
		return recordSetCount;
	}
	public void setRecordSetCount(int recordSetCount) {
		this.recordSetCount = recordSetCount;
	}
	public List<CatalogEntryView> getCatalogEntryView() {
		return catalogEntryView;
	}
	public void setCatalogEntryView(List<CatalogEntryView> catalogEntryView) {
		this.catalogEntryView = catalogEntryView;
	}
}
