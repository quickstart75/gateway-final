package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.entities.ValidationQuestion;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;
import com.softech.ls360.lms.repository.repositories.ValidationQuestionRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class ValidationQuestionRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private ValidationQuestionRepository validationQuestionRepository;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void findValidationQuestionByIds() {
		
		logger.info("findByUserName");
		
		String userName = "admin";
		try {
			
			List<Long> learnerProfileValidationQuestionIds = LongStream.rangeClosed(101, 115).boxed().collect(Collectors.toList());
			List<ValidationQuestion> validationQuestions = validationQuestionRepository.findByIdIn(learnerProfileValidationQuestionIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
