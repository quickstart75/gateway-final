package com.softech.ls360.api.gateway.service.model.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionSavingResponse {
	List<SubscriptionSavingCourses> courses = new ArrayList<SubscriptionSavingCourses>();
	Map<String, Float> saving = new HashMap<String, Float>();
	List<UserGroupRest> userGroup = new ArrayList<UserGroupRest>();
	
	public List<SubscriptionSavingCourses> getCourses() {
		return courses;
	}
	public void setCourses(List<SubscriptionSavingCourses> courses) {
		this.courses = courses;
	}

	public Map<String, Float> getSaving() {
		return saving;
	}
	public void setSaving(Map<String, Float> saving) {
		this.saving = saving;
	}
	public List<UserGroupRest> getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(List<UserGroupRest> userGroup) {
		this.userGroup = userGroup;
	}
	
	
}
