package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Map;

import com.softech.ls360.api.gateway.service.model.request.CourseTimeSpentRequest;
import com.softech.ls360.api.gateway.service.model.request.LearnerCourseCountRequest;
import com.softech.ls360.api.gateway.service.model.request.UserCoursesRequest;
import com.softech.ls360.api.gateway.service.model.response.CourseTimeSpentResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseResponse;

public interface LearnerCourseService {
	
	Map<String, Integer> getCourseCount(LearnerCourseCountRequest request, String userName);
	
	LearnerCourseResponse getLearnerCourses(UserCoursesRequest user);
	
	List<CourseTimeSpentResponse> getCourseTimeSpent(CourseTimeSpentRequest request, String userName);

}
