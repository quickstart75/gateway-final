package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

public class CourseRest {
	String name;
	String courseGuid;
	List<UserRest> users;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourseGuid() {
		return courseGuid;
	}
	public void setCourseGuid(String courseGuid) {
		this.courseGuid = courseGuid;
	}
	public List<UserRest> getUsers() {
		return users;
	}
	public void setUsers(List<UserRest> users) {
		this.users = users;
	}
}
