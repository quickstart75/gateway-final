package com.softech.ls360.api.gateway.service.model.request;

import java.util.List;
import java.util.Map;

public class PersonalizationFilter {
	List<Map<String, String>> competencies;

	public List<Map<String, String>> getCompetencies() {
		return competencies;
	}

	public void setCompetencies(List<Map<String, String>> competencies) {
		this.competencies = competencies;
	}
}
