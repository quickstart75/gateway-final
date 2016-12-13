package com.softech.ls360.api.gateway.service;

import java.util.Collection;
import java.util.List;

import com.softech.ls360.lms.repository.entities.CourseApproval;

public interface LearningSessionService {

	List<CourseApproval> findCourseApprovals(Long learnerId, Long courseApprovalId, Collection<String> courseGuids) throws Exception;
	
}
