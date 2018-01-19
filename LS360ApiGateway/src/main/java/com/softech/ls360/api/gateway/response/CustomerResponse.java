package com.softech.ls360.api.gateway.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.softech.ls360.api.gateway.response.model.UserGroupRest;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"name",
    "userGroup"
})
public class CustomerResponse {

	public CustomerResponse() {
		super();
	}
	
	public CustomerResponse(String name, List<UserGroupRest> userGroup) {
		super();
		this.name = name;
		this.userGroup = userGroup;
	}
	@JsonProperty("name")
	String name;
	
	@JsonProperty("userGroup")
	List<UserGroupRest> userGroup;
	
	@JsonProperty("name")
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
	
	
}
