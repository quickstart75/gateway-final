package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Map;

public interface CourseService {
	public Map<String, String> getCourseOutlineByGuids(List<String> guids);
}
