package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

public class EngagementTeamByMonth {
	String name;
	Integer year;
	List<UserGroupRest> userGroup;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<UserGroupRest> getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(List<UserGroupRest> userGroup) {
		this.userGroup = userGroup;
	}


}
