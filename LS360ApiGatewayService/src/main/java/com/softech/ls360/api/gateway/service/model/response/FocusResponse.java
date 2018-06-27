package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

public class FocusResponse {
	String categoryName;
	List<String> sku;
	double percentage;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	public List<String> getSku() {
		return sku;
	}
	public void setSku(List<String> sku) {
		this.sku = sku;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
}
