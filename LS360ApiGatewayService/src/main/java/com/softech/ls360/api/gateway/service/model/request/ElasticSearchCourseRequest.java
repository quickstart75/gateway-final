package com.softech.ls360.api.gateway.service.model.request;

import java.util.List;
import java.util.Map;

public class ElasticSearchCourseRequest {
	private int pageSize;
	private int pageNumber;
	private List courseGuids;
	private List categories;
	private List attributes;
	private List durations;
	private List subscriptions;
	private List keywords;
	private Map filters;
	private List fields;
	
	public List getCourseGuids() {
		return courseGuids;
	}
	public void setCourseGuids(List courseGuids) {
		this.courseGuids = courseGuids;
	}
	public List getCategories() {
		return categories;
	}
	public void setCategories(List categories) {
		this.categories = categories;
	}
	public List getAttributes() {
		return attributes;
	}
	public void setAttributes(List attributes) {
		this.attributes = attributes;
	}
	public List getDurations() {
		return durations;
	}
	public void setDurations(List durations) {
		this.durations = durations;
	}
	public List getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(List subscriptions) {
		this.subscriptions = subscriptions;
	}
	public List getKeywords() {
		return keywords;
	}
	public void setKeywords(List keywords) {
		this.keywords = keywords;
	}
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
	public Map getFilters() {
		return filters;
	}
	public void setFilters(Map filters) {
		this.filters = filters;
	}
	public List getFields() {
		return fields;
	}
	public void setFields(List fields) {
		this.fields = fields;
	}
	
	
}
