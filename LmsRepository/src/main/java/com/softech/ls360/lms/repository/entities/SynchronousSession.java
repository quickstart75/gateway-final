package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SynchronousSession extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SynchronousClass synchronousClass;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private Long locationId;
	private String status;
	private LocalDateTime lastUpdatedDate;
	private LocalDateTime createdDate;
	private String guid;
	private String sessionKey;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SYNCHRONOUSCLASS_ID")
	public SynchronousClass getSynchronousClass() {
		return synchronousClass;
	}
	public void setSynchronousClass(SynchronousClass synchronousClass) {
		this.synchronousClass = synchronousClass;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}
	
	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	@Column(name="LOCATION_ID")
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Column(name="SESSION_KEY")
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
