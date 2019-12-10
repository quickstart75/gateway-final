package com.softech.ls360.lms.repository.test.repositories;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.projection.learner.enrollment.LearnerEnrollmentCourses;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class LearnerEnrollmentRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void findLearnerEnrollmentCourses() {
		
		logger.info("findLearnerEnrollmentCourses");
		
		Long learnerId = 2504L;
		List<String> enrollmentStatus = Arrays.asList("Dropped", "Swapped");
		LocalDateTime todayDate = LocalDateTime.now();
		try {
			Optional<List<LearnerEnrollmentCourses>> optionalLearnerEnrollmentCourses = learnerEnrollmentRepository.findDistinctCoursesByLearner_IdAndEnrollmentStatusNotInAndStartDateLessThanEqual(learnerId, enrollmentStatus, todayDate);
			if (optionalLearnerEnrollmentCourses.isPresent()) {
				List<LearnerEnrollmentCourses> courseApprovals = optionalLearnerEnrollmentCourses.get();
				if (!CollectionUtils.isEmpty(courseApprovals)) {
					Set<String> courseGuids = courseApprovals
							.stream()
							.map(LearnerEnrollmentCourses::getCourse)
							.filter(p -> p != null)
							.map(Course::getCourseGuid)
							.collect(Collectors.toSet());
					
					logger.info("" + courseGuids.size());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
