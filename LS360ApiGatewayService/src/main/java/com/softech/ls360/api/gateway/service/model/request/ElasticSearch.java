package com.softech.ls360.api.gateway.service.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElasticSearch {
	private int pageSize;
	private int pageNumber;
	List contentFilter;
	List contentTypeFilter;
	List keywords;
	Map guidCollection;
	List summary;
	List origins;
	List custom_fields = new ArrayList();
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public List getContentFilter() {
		return contentFilter;
	}
	public void setContentFilter(List contentFilter) {
		this.contentFilter = contentFilter;
	}
	public List getContentTypeFilter() {
		return contentTypeFilter;
	}
	public void setContentTypeFilter(List contentTypeFilter) {
		this.contentTypeFilter = contentTypeFilter;
	}
	public List getKeywords() {
		return keywords;
	}
	public void setKeywords(List keywords) {
		this.keywords = keywords;
	}
	public Map getGuidCollection() {
		return guidCollection;
	}
	public void setGuidCollection(Map guidCollection) {
		this.guidCollection = guidCollection;
	}
	public List getSummary() {
		return summary;
	}
	public void setSummary(List summary) {
		this.summary = summary;
	}
	public List getOrigins() {
		return origins;
	}
	public void setOrigins(List origins) {
		this.origins = origins;
	}
	public List getCustom_fields() {
		return custom_fields;
	}
	public void setCustom_fields(List custom_fields) {
		this.custom_fields = custom_fields;
	}
	
}
