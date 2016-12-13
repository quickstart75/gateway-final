package com.softech.ls360.lms.repository.test.repositories;

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

import com.softech.ls360.lms.repository.entities.CourseApproval;
import com.softech.ls360.lms.repository.projection.learning.session.LearningSessionCourseApprovals;
import com.softech.ls360.lms.repository.repositories.LearningSessionRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class LearningSessionRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LearningSessionRepository learningSessionRepository;
	
	//@Test
	public void test1() {
		
	}
	
	@Test
	public void findCourseApprovals() {
		
		logger.info("findCourseApprovals");
		
		Long learnerId = 2504L;
	    List<String> courseGuids = Arrays.asList("ee8bc87cd5904fd0925177eadfa14dc6",
	    		"b503eca4cef945d4b453e67595513b8c",
	    		"c63fa340447d4d5ab801591270262445",
	    		"32fda42f7b184e4eb727712ccf08e2c5",
	    		"1c516cd937c04ce4bf5569635ffb1bef",
	    		"0-69-122169001",
	    		"0-15-57815671",
	    		"0-15-185415001",
	    		"0-7-81707001",
	    		"43-6-38406661",
	    		"0-87-182184001",
	    		"0-87-182284001",
	    		"0-73-100175",
	    		"0-55-100152",
	    		"0-55-100153",
	    		"0-55-100156",
	    		"0-55-100157",
	    		"0-55-100158",
	    		"0-55-100159",
	    		"0-55-100160");
		Long courseApprovalId = 0L;
		
		Optional<List<LearningSessionCourseApprovals>> OptionalLearningSessionCourseApprovals = learningSessionRepository.findDistinctCourseApprovalsByLearnerEnrollment_Learner_IdAndCourseApproval_IdGreaterThanAndCourse_CourseGuidIn(learnerId, courseApprovalId, courseGuids);
		if (OptionalLearningSessionCourseApprovals.isPresent()) {
			List<LearningSessionCourseApprovals> courseApprovals = OptionalLearningSessionCourseApprovals.get();
			if (!CollectionUtils.isEmpty(courseApprovals)) {
				Set<Long> courseApprovalIds = courseApprovals
						.stream()
						.map(LearningSessionCourseApprovals::getCourseApproval)
						.filter(p -> p != null)
						.map(CourseApproval::getId)
						.collect(Collectors.toSet());
				
				logger.info("");
			}
		}
		
		
	}
	
}
