package com.softech.ls360.api.gateway.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.softech.ls360.api.gateway.service.model.response.FocusResponse;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsResponse;
import com.softech.ls360.lms.repository.entities.Course;

public interface LearnerEnrollmentService {

	List<Course> getLearnerEnrollmentCourses(Long learnerId, Collection<String> enrollmentStatus, LocalDateTime dateTime) throws Exception;
	ROIAnalyticsResponse getROIAnalytics(long customerId, long distributorId);
	void updateLearnerEnrollmentStatus(String status, List<Long> enrollmentIds);
	
	List<String> getEnrolledCoursesGUIDByCustomer(long customerId);
	List<FocusResponse> getEnrolledCoursesPercentageByTopicByCustomer(long customerId, List<String> EnrolledCoursesGUID);
}
