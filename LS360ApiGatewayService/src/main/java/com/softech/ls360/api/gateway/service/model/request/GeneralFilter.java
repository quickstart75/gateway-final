package com.softech.ls360.api.gateway.service.model.request;

import java.util.HashMap;
import java.util.Map;

public class GeneralFilter {
	Map<String, String> filter = new HashMap<String, String>();

	public Map<String, String> getFilter() {
		return filter;
	}

	public void setFilter(Map<String, String> filter) {
		this.filter = filter;
	}
	
	
}
