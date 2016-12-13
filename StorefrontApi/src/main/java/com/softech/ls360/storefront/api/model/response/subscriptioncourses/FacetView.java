package com.softech.ls360.storefront.api.model.response.subscriptioncourses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softech.ls360.storefront.api.model.response.activitymonitor.Entry;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacetView {
	
	private String value = "";
	private List<Entry> entry;
	private String name = "";
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<Entry> getEntry() {
		return entry;
	}
	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
