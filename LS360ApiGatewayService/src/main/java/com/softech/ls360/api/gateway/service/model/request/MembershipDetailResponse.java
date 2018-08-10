package com.softech.ls360.api.gateway.service.model.request;

public class MembershipDetailResponse {
	private String name;
	private String url;
	private Boolean isMember;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getIsMember() {
		return isMember;
	}
	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}
}
