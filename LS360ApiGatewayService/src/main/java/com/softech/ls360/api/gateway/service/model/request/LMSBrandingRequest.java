package com.softech.ls360.api.gateway.service.model.request;

import java.util.ArrayList;
import java.util.List;

public class LMSBrandingRequest {
	private List<String> brandKeyValue = new ArrayList();

	public List<String> getBrandKeyValue() {
		return brandKeyValue;
	}

	public void setBrandKeyValue(List<String> brandKeyValue) {
		this.brandKeyValue = brandKeyValue;
	}
	

}
