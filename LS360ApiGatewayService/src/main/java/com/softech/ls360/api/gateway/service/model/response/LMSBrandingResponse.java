package com.softech.ls360.api.gateway.service.model.response;

import java.util.Map;

public class LMSBrandingResponse {
	private Map<String, String> brandKeyValue;

	public Map<String, String> getBrandKeyValue() {
		return brandKeyValue;
	}

	public void setBrandKeyValue(Map<String, String> brandKeyValue) {
		this.brandKeyValue = brandKeyValue;
	}

}
