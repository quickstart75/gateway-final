package com.softech.ls360.api.gateway.service.model.request;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-12-20T10:22:59.333Z")
public class CourseRequest {

	private List<String> courseGuids = new ArrayList<String>();

	@JsonProperty("courseGuids")
	public List<String> getCourseGuids() {
		return courseGuids;
	}

	public void setCourseGuids(List<String> courseGuids) {
		this.courseGuids = courseGuids;
	}
}
