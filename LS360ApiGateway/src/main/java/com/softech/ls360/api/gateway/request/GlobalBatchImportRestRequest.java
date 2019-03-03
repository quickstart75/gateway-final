package com.softech.ls360.api.gateway.request;

public class GlobalBatchImportRestRequest {
	String filePath;
    String action;
    String websiteId;
	
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
