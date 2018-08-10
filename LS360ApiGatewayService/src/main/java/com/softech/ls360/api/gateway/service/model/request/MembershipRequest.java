package com.softech.ls360.api.gateway.service.model.request;

import java.util.List;

public class MembershipRequest {
	private String userId;
	private List topics;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List getTopics() {
		return topics;
	}
	public void setTopics(List topics) {
		this.topics = topics;
	}
	
	
}
