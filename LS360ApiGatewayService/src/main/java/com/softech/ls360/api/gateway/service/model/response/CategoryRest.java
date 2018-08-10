package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

public class CategoryRest {
	Long id;
	String name;
	String desc;
	String url;
	
	List<CategoryCourseRest> topCourses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<CategoryCourseRest> getTopCourses() {
		return topCourses;
	}

	public void setTopCourses(List<CategoryCourseRest> topCourses) {
		this.topCourses = topCourses;
	}
}
