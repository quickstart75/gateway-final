package com.softech.ls360.api.gateway.service.model.request;

import java.util.List;
import java.util.Map;

public class PersonalizationFilter {
	List<Map> competencies;

	public List<Map> getCompetencies() {
		return competencies;
	}

	public void setCompetencies(List<Map> competencies) {
		this.competencies = competencies;
	}
}
