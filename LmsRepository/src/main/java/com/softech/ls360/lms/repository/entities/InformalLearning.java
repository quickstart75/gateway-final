package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LearnerInformalActivity")
public class InformalLearning extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -8147390863224265540L;

	private int activityTypeId;
	private int topicId;
	private long timeSpentInSeconds; //in secs
    private String title;
    private String externalResourceURL;
    private String notes;
    private String userName;
	
    @Column(name="ActivityTypeId")
	public int getActivityTypeId() {
		return activityTypeId;
	}
	public void setActivityTypeId(int activityTypeId) {
		this.activityTypeId = activityTypeId;
	}
	
	@Column(name="TopicId")
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	
	@Column(name="TimeSpentInSeconds")
	public long getTimeSpentInSeconds() {
		return timeSpentInSeconds;
	}
	public void setTimeSpentInSeconds(long timeSpentInSeconds) {
		this.timeSpentInSeconds = timeSpentInSeconds;
	}
	
	@Column(name="Title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="ExternalResourceURL")
	public String getExternalResourceURL() {
		return externalResourceURL;
	}
	public void setExternalResourceURL(String externalResourceURL) {
		this.externalResourceURL = externalResourceURL;
	}
	
	@Column(name="Notes")
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}	
	
	@Column(name="VU360Username")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	
}
