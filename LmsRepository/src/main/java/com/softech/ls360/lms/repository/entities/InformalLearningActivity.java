package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "InformalLearningActivity")
public class InformalLearningActivity extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 4135841566275459295L;
	
	private long vu360userId;
    private String itemGuid;
    private long timespentinseconds;
    private String comments;
    private String status;
    private Date createddate;
    private Date updateddate;
    private String storeId;
    
    @Column(name="VU360USER_ID")
	public long getVu360userId() {
		return vu360userId;
	}
	public void setVu360userId(long vu360userId) {
		this.vu360userId = vu360userId;
	}
	
	@Column(name="ITEM_GUID")
	public String getItemGuid() {
		return itemGuid;
	}
	public void setItemGuid(String itemGuid) {
		this.itemGuid = itemGuid;
	}
	
	@Column(name="TIMESPENTINSECONDS")
	public long getTimespentinseconds() {
		return timespentinseconds;
	}
	public void setTimespentinseconds(long timespentinseconds) {
		this.timespentinseconds = timespentinseconds;
	}
	
	@Column(name="COMMENTS")
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="CREATEDDATE")
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	
	@Column(name="UPDATEDDATE")
	public Date getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}
	
	@Column(name="STOREID")
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
}
