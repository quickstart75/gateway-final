package com.softech.ls360.api.gateway.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.softech.ls360.api.gateway.response.model.EntitlementRest;
import com.softech.ls360.api.gateway.response.model.UserGroupRest;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"organizationGroupID",
	"organizationGroup",
    "userGroup",
    "userCount",
    "userGroupCount",
    "entitlement"
})
public class OrganizationResponse {

	@JsonProperty("organizationGroupID")
	long id;
	
	@JsonProperty("organizationGroup")
	String name;
	
	@JsonProperty("userGroup")
	List<UserGroupRest> userGroup;
	
	@JsonProperty("userCount")
	String userCount;
	
	@JsonProperty("userGroupCount")
	String userGroupCount;
	
	@JsonProperty("entitlement")
	List<EntitlementRest> entitlementRest;
	
	public OrganizationResponse() {
		super();
	}
	
	public OrganizationResponse(long id,String name, List<UserGroupRest> userGroup, String userCount, String userGroupCount, List<EntitlementRest> entitlementRest) {
		super();
		this.id=id;
		this.name = name;
		this.userGroup = userGroup;
		this.userCount = userCount;
		this.userGroupCount = userGroupCount;
		this.entitlementRest = entitlementRest;
	}
	
	@JsonProperty("organizationGroupID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	@JsonProperty("organizationGroup")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("userGroup")
	public List<UserGroupRest> getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(List<UserGroupRest> userGroup) {
		this.userGroup = userGroup;
	}

	@JsonProperty("userCount")
	public String getUserCount() {
		return userCount;
	}

	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}

	@JsonProperty("userGroupCount")
	public String getUserGroupCount() {
		return userGroupCount;
	}

	public void setUserGroupCount(String userGroupCount) {
		this.userGroupCount = userGroupCount;
	}

	@JsonProperty("entitlement")
	public List<EntitlementRest> getEntitlementRest() {
		return entitlementRest;
	}

	public void setEntitlementRest(List<EntitlementRest> entitlementRest) {
		this.entitlementRest = entitlementRest;
	}
}
