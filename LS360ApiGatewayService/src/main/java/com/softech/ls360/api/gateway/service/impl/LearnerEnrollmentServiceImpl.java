package com.softech.ls360.api.gateway.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.projection.learner.enrollment.LearnerEnrollmentCourses;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;

@Service
public class LearnerEnrollmentServiceImpl implements LearnerEnrollmentService {

	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	@Override
	public List<Course> getLearnerEnrollmentCourses(Long learnerId, Collection<String> enrollmentStatus, LocalDateTime dateTime) throws Exception {
		
		List<Course> enrollmentCourses = null;
		Optional<List<LearnerEnrollmentCourses>> optionalLearnerEnrollmentCourses = learnerEnrollmentRepository.findDistinctCoursesByLearner_IdAndEnrollmentStatusNotInAndStartDateLessThanEqual(learnerId, enrollmentStatus, dateTime);
		if (optionalLearnerEnrollmentCourses.isPresent()) {
			List<LearnerEnrollmentCourses> learnerEnrollmentCourses = optionalLearnerEnrollmentCourses.get();
			if (!CollectionUtils.isEmpty(learnerEnrollmentCourses)) {
				enrollmentCourses = learnerEnrollmentCourses
						.stream()
						.map(LearnerEnrollmentCourses::getCourse)
						.filter(p -> p != null)
						.collect(Collectors.toList());
			}
		}
		return enrollmentCourses;
	}

}
