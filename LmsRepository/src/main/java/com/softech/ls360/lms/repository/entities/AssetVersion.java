package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AssetVersion extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Asset asset;
	private VU360User user;
	private LocalDateTime createdOn;
	private String location;
	private Integer version;
	private String comments;
	private String assetVersionGuid;
	private LocalDateTime createdDate;
	private VU360User createuserId;
	private LocalDateTime lastUpdatedDate;
	private VU360User lastUpdateUser;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ASSET_ID", nullable=false)
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	public VU360User getUser() {
		return user;
	}

	public void setUser(VU360User user) {
		this.user = user;
	}

	@Column(nullable=false)
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name="ASSETVERSION_GUID")
	public String getAssetVersionGuid() {
		return assetVersionGuid;
	}

	public void setAssetVersionGuid(String assetVersionGuid) {
		this.assetVersionGuid = assetVersionGuid;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public VU360User getCreateuserId() {
		return createuserId;
	}

	public void setCreateuserId(VU360User createuserId) {
		this.createuserId = createuserId;
	}

	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LastUpdateUser")
	public VU360User getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(VU360User lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

}
