package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LearnerInformalActivity")
public class InformalLearning extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -8147390863224265540L;

	private Integer activityTypeId;
	private Integer topicId;
	private Long timeSpentInSeconds; //in secs
    private String title;
    private String externalResourceURL;
    private String notes;
    private String username;
	
    private String itemGuid;
    private String status;
    private String storeId;
    
    private Integer typeId;
    
    @Column(name="ActivityTypeId")
	public Integer getActivityTypeId() {
		return activityTypeId;
	}
	public void setActivityTypeId(Integer activityTypeId) {
		this.activityTypeId = activityTypeId;
	}
	
	@Column(name="TopicId")
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	
	@Column(name="TimeSpentInSeconds")
	public Long getTimeSpentInSeconds() {
		return timeSpentInSeconds;
	}
	public void setTimeSpentInSeconds(Long timeSpentInSeconds) {
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
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	
	@Column(name="ITEM_GUID")
	public String getItemGuid() {
		return itemGuid;
	}
	public void setItemGuid(String itemGuid) {
		this.itemGuid = itemGuid;
	}
	
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="STOREID")
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@Column(name="typeId")
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	
}
