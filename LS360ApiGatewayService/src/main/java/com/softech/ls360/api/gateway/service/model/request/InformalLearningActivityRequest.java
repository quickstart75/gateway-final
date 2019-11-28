package com.softech.ls360.api.gateway.service.model.request;

import java.util.Date;

public class InformalLearningActivityRequest {
	private long id;
	private long vu360userId;
	private String username;
    private String itemGuid;
    private long timespentinseconds;
    private String comments;
    private String status;
    private Date createddate;
    private Date updateddate;
    private String storeId;
    
	private int pageSize;
	private int pageNumber;

	
	private int activityTypeId;
	private int topicId;
    private String title;
    private String externalResourceURL;
    
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVu360userId() {
		return vu360userId;
	}
	public void setVu360userId(long vu360userId) {
		this.vu360userId = vu360userId;
	}
	public String getItemGuid() {
		return itemGuid;
	}
	public void setItemGuid(String itemGuid) {
		this.itemGuid = itemGuid;
	}
	public long getTimespentinseconds() {
		return timespentinseconds;
	}
	public void setTimespentinseconds(long timespentinseconds) {
		this.timespentinseconds = timespentinseconds;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public Date getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
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

	
	
	
}
