package com.softech.ls360.lms.api.model.request;

import java.util.List;

public class UserPermissionRequest {
	List<String> userName;
	Boolean locked;
	Boolean enabled;
	
	public List<String> getUserName() {
		return userName;
	}
	public void setUserName(List<String> userName) {
		this.userName = userName;
	}
	
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
