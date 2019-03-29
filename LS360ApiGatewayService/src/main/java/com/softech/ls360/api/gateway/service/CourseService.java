package com.softech.ls360.api.gateway.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface CourseService {
	public Map<String, String> getCourseOutlineByGuids(List<String> guids);
	public String getSampleCertificateByGuid(String guid);
	List<Object> getCourseByGUIDs(List<String> guids);
	
	List<Map<String, String>> findSlideAndLessonByGuids(List<String> slideguids, List<String> lessonguids, Long courseId);
	public LinkedHashMap<String, String> findLessonWithFirstSlideIdByGuids(List<String> lessonguids);
	Long findIdByGuid(String guid);
	public Object[] getCourseMaterialByGuid(String guids, String searchText);
}
