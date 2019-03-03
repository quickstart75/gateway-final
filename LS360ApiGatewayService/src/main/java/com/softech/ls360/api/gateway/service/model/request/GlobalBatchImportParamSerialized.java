package com.softech.ls360.api.gateway.service.model.request;

import java.io.Serializable;

public class GlobalBatchImportParamSerialized implements Serializable {
    
    private static final long serialVersionUID = 2L;
    
    String customerEmail;
    String filePath;
    String action;
    String websiteId;
    
	public GlobalBatchImportParamSerialized() {
		super();
	}
	
	public GlobalBatchImportParamSerialized(String customerEmail, String filePath, String action, String websiteId) {
		super();
		this.customerEmail = customerEmail;
		this.filePath = filePath;
		this.action = action;
		this.websiteId = websiteId;
	}
	
	
	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
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

	public String getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(String websiteId) {
		this.websiteId = websiteId;
	}
}
