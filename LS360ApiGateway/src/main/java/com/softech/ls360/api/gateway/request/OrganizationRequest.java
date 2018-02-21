package com.softech.ls360.api.gateway.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softech.ls360.api.gateway.response.model.UserGroupRest;

public class OrganizationRequest {
	
	String organizationName;

	@JsonProperty("userGroup")
	List<UserGroupRest> userGroup;
	
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	@JsonProperty("userGroup")
	public List<UserGroupRest> getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(List<UserGroupRest> userGroup) {
		this.userGroup = userGroup;
	}
}
