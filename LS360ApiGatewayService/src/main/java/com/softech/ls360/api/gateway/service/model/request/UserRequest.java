package com.softech.ls360.api.gateway.service.model.request;

public class UserRequest{
	private String userName;
	private String courseGuid;
	private int storeId;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCourseGuid() {
		return courseGuid;
	}

	public void setCourseGuid(String courseGuid) {
		this.courseGuid = courseGuid;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
}
