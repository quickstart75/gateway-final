package com.softech.ls360.api.gateway.service.model.request;

import java.util.List;

public class InformalLearningFilter {
	String searchText; 
	List<String> contentFilters;
	List<String> sourceFilters;
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public List<String> getContentFilters() {
		return contentFilters;
	}
	public void setContentFilters(List<String> contentFilters) {
		this.contentFilters = contentFilters;
	}
	public List<String> getSourceFilters() {
		return sourceFilters;
	}
	public void setSourceFilters(List<String> sourceFilters) {
		this.sourceFilters = sourceFilters;
	}
	
	
}
