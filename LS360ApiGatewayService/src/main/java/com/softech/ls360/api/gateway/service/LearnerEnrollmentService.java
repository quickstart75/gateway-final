package com.softech.ls360.api.gateway.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.softech.ls360.api.gateway.service.model.request.LearnerInstruction;
import com.softech.ls360.api.gateway.service.model.response.FocusResponse;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsResponse;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionSavingResponse;
import com.softech.ls360.lms.repository.entities.Course;

public interface LearnerEnrollmentService {

	List<Course> getLearnerEnrollmentCourses(Long learnerId, Collection<String> enrollmentStatus, LocalDateTime dateTime) throws Exception;
	ROIAnalyticsResponse getROIAnalytics(long customerId, long distributorId);
	void updateLearnerEnrollmentStatus(String status, List<Long> enrollmentIds);
	
	List<String> getEnrolledCoursesGUIDByCustomer(long customerId);
	List<String> getEnrolledCoursesGUID(Long learnerId);
	List<FocusResponse> getEnrolledCoursesPercentageByTopicByCustomer(long customerId, List<String> EnrolledCoursesGUID);
	List<FocusResponse> getEnrolledCoursesPercentageByTopic(String userName,Long learnerId, List<String> EnrolledCoursesGUID);
	SubscriptionSavingResponse getSubscriptionSavingStates(Long customerId,  List<Long> userGroup);
	String getLearnerEnrollmentInstruction(Long enrollmentId);
	boolean saveLearnerEnrollmentInstruction(List<LearnerInstruction> instructionRequest);
	boolean isAllowMOCSubscriptionEnrollment(String username, Long subscriptionId);
	List<Object[]> getEnrolledCoursesInfoByUsername(String username);
	void updateLearnerEnrollmentStatisticsStatus(String status, List<Long> enrollmentIds);
	Map<Object, Object> getEnrollmentCoursesMapWithstatus(String username);
	
	Map<String, Long> getEnrollmentByUsersByCourse(String username, List<String> guid);
}
