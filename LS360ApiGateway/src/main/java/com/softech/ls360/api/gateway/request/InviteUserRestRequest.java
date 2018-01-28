package com.softech.ls360.api.gateway.request;

import com.softech.ls360.api.gateway.service.model.response.LearnerSubscription;

public class InviteUserRestRequest {

	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private String teamGuid;
	LearnerSubscription license;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTeamGuid() {
		return teamGuid;
	}
	public void setTeamGuid(String teamGuid) {
		this.teamGuid = teamGuid;
	}
	public LearnerSubscription getLicense() {
		return license;
	}
	public void setLicense(LearnerSubscription license) {
		this.license = license;
	}
	
	
	
	
}
