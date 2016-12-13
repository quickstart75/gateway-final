package com.softech.ls360.storefront.api.model.response.productsummary;

import java.util.List;

public class ProductSummary {

	private String recordSetTotal;
	private String recordSetCount;
	private String resourceId;
	private List<CatalogEntryView> catalogEntryView;
	private String recordSetComplete;
	private String recordSetStartNumber;
	private String recordSetTotalMatches;
	private String resourceName;

	public String getRecordSetTotal() {
		return recordSetTotal;
	}

	public void setRecordSetTotal(String recordSetTotal) {
		this.recordSetTotal = recordSetTotal;
	}

	public String getRecordSetCount() {
		return recordSetCount;
	}

	public void setRecordSetCount(String recordSetCount) {
		this.recordSetCount = recordSetCount;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public List<CatalogEntryView> getCatalogEntryView() {
		return catalogEntryView;
	}

	public void setCatalogEntryView(List<CatalogEntryView> catalogEntryView) {
		this.catalogEntryView = catalogEntryView;
	}

	public String getRecordSetComplete() {
		return recordSetComplete;
	}

	public void setRecordSetComplete(String recordSetComplete) {
		this.recordSetComplete = recordSetComplete;
	}

	public String getRecordSetStartNumber() {
		return recordSetStartNumber;
	}

	public void setRecordSetStartNumber(String recordSetStartNumber) {
		this.recordSetStartNumber = recordSetStartNumber;
	}

	public String getRecordSetTotalMatches() {
		return recordSetTotalMatches;
	}

	public void setRecordSetTotalMatches(String recordSetTotalMatches) {
		this.recordSetTotalMatches = recordSetTotalMatches;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

}
