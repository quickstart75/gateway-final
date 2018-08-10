package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

public class CarrierPathRest {
	Long categoryId;
	String categoryName;
	String categoryUrl;
	List sku;
	Boolean isEnrolled;
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryUrl() {
		return categoryUrl;
	}
	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}
	public List getSku() {
		return sku;
	}
	public void setSku(List sku) {
		this.sku = sku;
	}
	public Boolean getIsEnrolled() {
		return isEnrolled;
	}
	public void setIsEnrolled(Boolean isEnrolled) {
		this.isEnrolled = isEnrolled;
	}
}
