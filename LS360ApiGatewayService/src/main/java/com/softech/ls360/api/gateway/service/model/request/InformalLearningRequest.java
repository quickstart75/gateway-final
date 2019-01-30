package com.softech.ls360.api.gateway.service.model.request;

public class InformalLearningRequest {
	private int storeId;
	private int websiteId;
	private String emailAddress;
	private String securityCode;
	
	private String searchType;
	private int pageSize;
	private int pageNumber;
	private String sort ;
	private String searchText ;
	private Filter filter;
	private InformalLearningFilter informalLearning;
	private PersonalizationFilter personalization;
	private String subsCode;
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Filter getFilter() {
		return filter;
	}
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public InformalLearningFilter getInformalLearning() {
		return informalLearning;
	}
	public void setInformalLearning(InformalLearningFilter informalLearning) {
		this.informalLearning = informalLearning;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public int getWebsiteId() {
		return websiteId;
	}
	public void setWebsiteId(int websiteId) {
		this.websiteId = websiteId;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public PersonalizationFilter getPersonalization() {
		return personalization;
	}
	public void setPersonalization(PersonalizationFilter personalization) {
		this.personalization = personalization;
	}
	public String getSubsCode() {
		return subsCode;
	}
	public void setSubsCode(String subsCode) {
		this.subsCode = subsCode;
	}
	

	
}
