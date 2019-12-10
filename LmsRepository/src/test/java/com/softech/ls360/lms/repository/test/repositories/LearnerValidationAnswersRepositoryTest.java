package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.repository.entities.LearnerValidationAnswers;
import com.softech.ls360.lms.repository.entities.ValidationQuestion;
import com.softech.ls360.lms.repository.repositories.LearnerValidationAnswersRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class LearnerValidationAnswersRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LearnerValidationAnswersRepository learnerValidationAnswersRepository;
	

	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void findLearnerValidationAnswers() {
		
		logger.info("findLearnerValidationAnswers");
		
		try {
			Long learnerId = 2504L;
			List<LearnerValidationAnswers> learnerValidationAnswers = learnerValidationAnswersRepository.findByLearnerId(learnerId);
			if (!CollectionUtils.isEmpty(learnerValidationAnswers)) {
				List<Long> validationQuestionids = learnerValidationAnswers
						.stream()
						.map(LearnerValidationAnswers::getValidationQuestion)
						.filter(validationQuestion -> validationQuestion != null)
						.map(ValidationQuestion::getId)
						.collect(Collectors.toList());
				if (!CollectionUtils.isEmpty(validationQuestionids)) {
					Long minimumQuestionId = validationQuestionids.stream().min(Long::compare).get();
					logger.info("");
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
