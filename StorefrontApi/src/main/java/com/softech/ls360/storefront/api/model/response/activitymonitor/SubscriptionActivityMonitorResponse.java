package com.softech.ls360.storefront.api.model.response.activitymonitor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionActivityMonitorResponse {
	
	private List<SubscriptionStatistic> subscriptionStatistics;
	private int aggregateSubscriptionCoursesCount;
	private int enrolledSubscriptionCoursesCount;
	
	public List<SubscriptionStatistic> getSubscriptionStatistics() {
		return subscriptionStatistics;
	}
	public void setSubscriptionStatistics(
			List<SubscriptionStatistic> subscriptionStatistics) {
		this.subscriptionStatistics = subscriptionStatistics;
	}
	public int getAggregateSubscriptionCoursesCount() {
		return aggregateSubscriptionCoursesCount;
	}
	public void setAggregateSubscriptionCoursesCount(
			int aggregateSubscriptionCoursesCount) {
		this.aggregateSubscriptionCoursesCount = aggregateSubscriptionCoursesCount;
	}
	public int getEnrolledSubscriptionCoursesCount() {
		return enrolledSubscriptionCoursesCount;
	}
	public void setEnrolledSubscriptionCoursesCount(
			int enrolledSubscriptionCoursesCount) {
		this.enrolledSubscriptionCoursesCount = enrolledSubscriptionCoursesCount;
	}
}
