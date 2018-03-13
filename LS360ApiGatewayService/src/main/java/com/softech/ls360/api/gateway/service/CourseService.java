package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Map;

import com.softech.ls360.lms.repository.entities.Course;

public interface CourseService {
	public Map<String, String> getCourseOutlineByGuids(List<String> guids);
	List<Object> getCourseByGUIDs(List<String> guids);
}
