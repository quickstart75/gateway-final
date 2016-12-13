package com.softech.ls360.api.gateway.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.api.gateway.service.LearningSessionService;
import com.softech.ls360.lms.repository.entities.CourseApproval;
import com.softech.ls360.lms.repository.projection.learning.session.LearningSessionCourseApprovals;
import com.softech.ls360.lms.repository.repositories.LearningSessionRepository;

@Service
public class LearningSessionServiceImpl implements LearningSessionService {

	@Inject
	private LearningSessionRepository learningSessionRepository;
	
	@Override
	public List<CourseApproval> findCourseApprovals(Long learnerId, Long courseApprovalId, Collection<String> courseGuids) throws Exception {
		List<CourseApproval> courseApprovals = null;
		Optional<List<LearningSessionCourseApprovals>> optionalLearningSessionCourseApprovals = learningSessionRepository.findDistinctCourseApprovalsByLearnerEnrollment_Learner_IdAndCourseApproval_IdGreaterThanAndCourse_CourseGuidIn(learnerId, courseApprovalId, courseGuids);
		if (optionalLearningSessionCourseApprovals.isPresent()) {
			List<LearningSessionCourseApprovals> learningSessionCourseApprovals = optionalLearningSessionCourseApprovals.get();
			if (!CollectionUtils.isEmpty(learningSessionCourseApprovals)) {
				courseApprovals = learningSessionCourseApprovals
						.stream()
						.map(LearningSessionCourseApprovals::getCourseApproval)
						.filter(p -> p != null)
						.collect(Collectors.toList());
				
			}
		}
		return courseApprovals;
	}

}
