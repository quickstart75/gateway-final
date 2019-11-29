package com.softech.ls360.api.gateway.service;

import java.util.Collection;
import java.util.List;

import com.softech.ls360.lms.repository.entities.CourseApproval;
import com.softech.ls360.lms.repository.entities.LearningSession;

public interface LearningSessionService {

	List<CourseApproval> findCourseApprovals(Long learnerId, Long courseApprovalId, Collection<String> courseGuids) throws Exception;
	LearningSession saveLearnerSession(Long enrollmentId);
	LearningSession getLatestSessionByUsernameAndCourseKey(String username, String courseGuid);
	boolean updateSessionEndTime(LearningSession session);
	
}
