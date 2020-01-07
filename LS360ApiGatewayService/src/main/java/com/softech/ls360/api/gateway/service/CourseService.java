package com.softech.ls360.api.gateway.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.Course;

public interface CourseService {
	public Map<String, String> getCourseOutlineByGuids(List<String> guids);
	public String getSampleCertificateByGuid(String guid);
	List<Object> getCourseByGUIDs(List<String> guids);
	
	List<Map<String, String>> findSlideAndLessonByGuids(List<String> slideguids, List<String> lessonguids, Long courseId);
	public LinkedHashMap<String, String> findLessonWithFirstSlideIdByGuids(List<String> lessonguids);
	Long findIdByGuid(String guid);
	public Object[] getCourseMaterialByGuid(String guids, String searchText);
	
	public Course findEdxCourse(String guid);
	public Boolean findDemoByCourseGuid(String guid);
}
