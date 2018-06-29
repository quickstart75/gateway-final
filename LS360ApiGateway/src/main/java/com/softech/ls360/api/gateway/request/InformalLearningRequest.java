package com.softech.ls360.api.gateway.request;

public class InformalLearningRequest {
		
	private int activityTypeId;
	private int topicId;
	private long timeSpentInSeconds; //in secs
    private String title;
    private String externalResourceURL;
    private String notes;
    
	public int getActivityTypeId() {
		return activityTypeId;
	}
	public void setActivityTypeId(int activityTypeId) {
		this.activityTypeId = activityTypeId;
	}
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	public long getTimeSpentInSeconds() {
		return timeSpentInSeconds;
	}
	public void setTimeSpentInSeconds(long timeSpentInSeconds) {
		this.timeSpentInSeconds = timeSpentInSeconds;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExternalResourceURL() {
		return externalResourceURL;
	}
	public void setExternalResourceURL(String externalResourceURL) {
		this.externalResourceURL = externalResourceURL;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
