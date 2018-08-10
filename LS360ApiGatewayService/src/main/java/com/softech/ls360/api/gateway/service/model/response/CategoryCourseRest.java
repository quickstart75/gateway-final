package com.softech.ls360.api.gateway.service.model.response;

public class CategoryCourseRest {
	String name;
	String courseGuid;
	Boolean isEnrolled=false;
	
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
	public Boolean getIsEnrolled() {
		return isEnrolled;
	}
	public void setIsEnrolled(Boolean isEnrolled) {
		this.isEnrolled = isEnrolled;
	}
	
}
