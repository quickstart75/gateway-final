package com.softech.ls360.api.gateway.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.softech.ls360.api.gateway.service.model.response.ClassroomCourseInfo;
import com.softech.ls360.api.gateway.service.model.response.ClassroomStatistics;
import com.softech.ls360.api.gateway.service.model.response.LearnerClassroomDetailResponse;
import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.entities.SynchronousSession;

public interface ClassroomCourseService {
	
	LearnerClassroomDetailResponse getClassroomDetails(String userName, Long enrollmentId);
	Map<String, String> getStartEndDate(List<SynchronousSession> synchronousSession);
	String getStatus(List<SynchronousSession> synchronousSession);
	ClassroomStatistics getClassroomStatistics(Long classId,com.softech.ls360.lms.repository.entities.Course course);
	ClassroomStatistics getClassroomStatistics(Long classId);
	List<ClassroomCourseInfo> getClassroomCourseScheduleStatistics(String courseGuid);
	List<Object> getCourseSession(String storeId,Integer timeZone);
	Map<Object,Object> getCourseSessionWithDetails(String storeId,Integer timeZone,String username);
	
	//For testing purpose
	List<Object[]> testAPILog();
}
