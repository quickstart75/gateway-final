package com.softech.ls360.api.gateway.service.model.response;

public class UserGroupRest {
	private Long guid;
	private String name;
	private Long timeSpent;
	
	public Long getGuid() {
		return guid;
	}
	public void setGuid(Long guid) {
		this.guid = guid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(Long timeSpent) {
		this.timeSpent = timeSpent;
	}
	
	
}
