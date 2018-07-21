package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

public class UserGroupwithCourseUserRest {
		Long guid;
		String name;
		String userCount;
		List<CourseRest> courses;
		
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
		public String getUserCount() {
			return userCount;
		}
		public void setUserCount(String userCount) {
			this.userCount = userCount;
		}
		public List<CourseRest> getCourses() {
			return courses;
		}
		public void setCourses(List<CourseRest> courses) {
			this.courses = courses;
		}
		
		
		
}
