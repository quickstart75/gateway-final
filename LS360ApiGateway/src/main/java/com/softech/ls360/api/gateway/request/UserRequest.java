package com.softech.ls360.api.gateway.request;

import java.util.List;

public class UserRequest {
	String username;
	List courseguid; 
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List getCourseguid() {
		return courseguid;
	}

	public void setCourseguid(List courseguid) {
		this.courseguid = courseguid;
	}
	
	
}
