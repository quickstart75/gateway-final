package com.softech.ls360.api.gateway.service.model.response;

public class ROIAnalyticsResponse {
	ROIAnalyticsLearner learner;
	ROIAnalyticsEnrollment enrollment;
	ROIAnalyticsTimeSpent timeSpent;
	
	public ROIAnalyticsLearner getLearner() {
		return learner;
	}
	public void setLearner(ROIAnalyticsLearner learner) {
		this.learner = learner;
	}
	public ROIAnalyticsEnrollment getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(ROIAnalyticsEnrollment enrollment) {
		this.enrollment = enrollment;
	}
	public ROIAnalyticsTimeSpent getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(ROIAnalyticsTimeSpent timeSpent) {
		this.timeSpent = timeSpent;
	}
	
	
}
