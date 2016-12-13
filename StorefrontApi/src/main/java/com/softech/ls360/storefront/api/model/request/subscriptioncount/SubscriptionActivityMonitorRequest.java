package com.softech.ls360.storefront.api.model.request.subscriptioncount;

import java.util.List;

public class SubscriptionActivityMonitorRequest {
	
	private List<String> subscriptionIds;
	private List<String> statisticsType;
	private List<String> facetType;
	
	public List<String> getSubscriptionIds() {
		return subscriptionIds;
	}
	public void setSubscriptionIds(List<String> subscriptionIds) {
		this.subscriptionIds = subscriptionIds;
	}
	public List<String> getStatisticsType() {
		return statisticsType;
	}
	public void setStatisticsType(List<String> statisticsType) {
		this.statisticsType = statisticsType;
	}
	public List<String> getFacetType() {
		return facetType;
	}
	public void setFacetType(List<String> facetType) {
		this.facetType = facetType;
	}
}
