package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Map;

import com.softech.ls360.api.gateway.service.model.request.CourseTimeSpentRequest;
import com.softech.ls360.api.gateway.service.model.request.LearnerCourseCountRequest;
import com.softech.ls360.api.gateway.service.model.request.LearnersEnrollmentRequest;
import com.softech.ls360.api.gateway.service.model.request.UserCoursesRequest;
import com.softech.ls360.api.gateway.service.model.request.UserRequest;
import com.softech.ls360.api.gateway.service.model.response.CourseTimeSpentResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerEnrollmentStatistics;
import com.softech.ls360.api.gateway.service.model.response.LearnersEnrollmentResponse;

public interface LearnerCourseService {
	
	Map<String, Integer> getCourseCount(LearnerCourseCountRequest request, String userName);
	
	LearnerCourseResponse getLearnerCourses(UserCoursesRequest user);
	
	List<CourseTimeSpentResponse> getCourseTimeSpent(CourseTimeSpentRequest request, String userName);

	LearnersEnrollmentResponse getLearnersEnrollment(LearnersEnrollmentRequest userCoursesRequest);
	
	LearnersEnrollmentResponse getMOCLearnersEnrollment(LearnersEnrollmentRequest userCoursesRequest);
	
	List<String> getSubscriptionId(String userName);
	
	LearnerEnrollmentStatistics getLearnerCourse(UserRequest userRequest);
}
