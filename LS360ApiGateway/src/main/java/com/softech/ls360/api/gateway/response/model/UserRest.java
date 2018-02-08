package com.softech.ls360.api.gateway.response.model;

import java.util.Date;

public class UserRest {
	String userName;
	String guid;
	String firstName;
	String lastName;
	String email;
	String lastLogin;
	Long startedCourses;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Long getStartedCourses() {
		return startedCourses;
	}
	public void setStartedCourses(Long startedCourses) {
		this.startedCourses = startedCourses;
	}
	
	
}
