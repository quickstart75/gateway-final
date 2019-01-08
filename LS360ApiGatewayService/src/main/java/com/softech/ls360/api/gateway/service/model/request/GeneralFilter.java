package com.softech.ls360.api.gateway.service.model.request;

import java.util.HashMap;
import java.util.Map;

public class GeneralFilter {
	Map<String, Object> filter = new HashMap<String, Object>();

	public Map<String, Object> getFilter() {
		return filter;
	}

	public void setFilter(Map<String, Object> filter) {
		this.filter = filter;
	}
	
	
}
