package com.softech.ls360.api.gateway.service.model.request;

import java.util.List;
import java.util.Map;


public class ElasticSearchAdvance {
	String query;
	private int pageSize;
	private int pageNumber;
	List origins;
	Map filter;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
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
	public Map getFilter() {
		return filter;
	}
	public void setFilter(Map filter) {
		this.filter = filter;
	}
	public List getOrigins() {
		return origins;
	}
	public void setOrigins(List origins) {
		this.origins = origins;
	}
	
}
