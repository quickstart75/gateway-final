package com.softech.ls360.api.gateway.service.model.request;

import java.util.ArrayList;
import java.util.List;

public class FocusRequest {
	
	private List<String> sku = new ArrayList<String>();

	public List<String> getSku() {
		return sku;
	}

	public void setSku(List<String> sku) {
		this.sku = sku;
	}
	
	
}
