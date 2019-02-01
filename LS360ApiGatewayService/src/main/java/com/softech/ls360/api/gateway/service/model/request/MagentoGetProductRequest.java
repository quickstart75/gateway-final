package com.softech.ls360.api.gateway.service.model.request;

public class MagentoGetProductRequest {
	private String sku;
	private int storeId;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
}
