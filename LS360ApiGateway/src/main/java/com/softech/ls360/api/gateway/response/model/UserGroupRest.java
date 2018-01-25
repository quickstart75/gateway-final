package com.softech.ls360.api.gateway.response.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserGroupRest {
	Long guid;
	String name;
	String userCount;
	
	@JsonProperty("users")
	List<UserRest> users;
	
	
	public UserGroupRest() {
		super();
	}
	
	public UserGroupRest(Long guid, String name) {
		super();
		this.guid = guid;
		this.name = name;
	}
	
	
	public UserGroupRest(Long guid, String name, String userCount, List<UserRest> users) {
		super();
		this.guid = guid;
		this.name = name;
		this.userCount = userCount;
		this.users = users;
	}

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
	
	@JsonProperty("users")
	public List<UserRest> getUsers() {
		return users;
	}
	public void setUsers(List<UserRest> users) {
		this.users = users;
	}
	public String getUserCount() {
		return userCount;
	}
	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}
	
}
