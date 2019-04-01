package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

/**
*
* @author mahammed.rehan.rana
*
*/
@Entity
@Table(name = "BATCHIMPORT_FAILURE")
public class BatchimportFailure implements Serializable {
	
	private static final long serialVersionUID = -6389288389175175664L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "FILE_PATH")
	private String filePath = null;
	
	@Column(name = "ACTION")
	private String action = null;
	
	@Column(name = "MANAGER_ID")
	private String managerId = null;
	
	
	@Column(name = "NOTIFY_JMS_TF")
	private Boolean notifyJms = null;


	@Column(name = "websiteId")
	private String websiteId = null;
	
	@Column(name = "storeId")
	private String storeId = null;
	
	
	public BatchimportFailure() {
		super();
	}


	public BatchimportFailure(String filePath, String action, String managerId, Boolean notifyJms, String websiteId, String storeId) {
		super();
		this.filePath = filePath;
		this.action = action;
		this.managerId = managerId;
		this.notifyJms = notifyJms;
		this.websiteId= websiteId;
		this.storeId=storeId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getManagerId() {
		return managerId;
	}


	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}


	public Boolean getNotifyJms() {
		return notifyJms;
	}


	public void setNotifyJms(Boolean notifyJms) {
		this.notifyJms = notifyJms;
	}


	public String getWebsiteId() {
		return websiteId;
	}


	public void setWebsiteId(String websiteId) {
		this.websiteId = websiteId;
	}


	public String getStoreId() {
		return storeId;
	}


	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	
	

}
