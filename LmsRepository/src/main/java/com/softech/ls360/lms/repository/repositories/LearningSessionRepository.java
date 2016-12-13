package com.softech.ls360.lms.repository.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.LearningSession;
import com.softech.ls360.lms.repository.projection.learning.session.LearningSessionCourseApprovals;

public interface LearningSessionRepository extends CrudRepository<LearningSession, Long> {

	Optional<List<LearningSessionCourseApprovals>> findDistinctCourseApprovalsByLearnerEnrollment_Learner_IdAndCourseApproval_IdGreaterThanAndCourse_CourseGuidIn(Long learnerId, Long courseApprovalId, Collection<String> courseGuids);
	
}
