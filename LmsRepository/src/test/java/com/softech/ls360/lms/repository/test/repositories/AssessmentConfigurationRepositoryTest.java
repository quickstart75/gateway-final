package com.softech.ls360.lms.repository.test.repositories;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.AssessmentConfiguration;
import com.softech.ls360.lms.repository.entities.AssessmentConfigurationProjection;
import com.softech.ls360.lms.repository.repositories.AssessmentConfigurationRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class AssessmentConfigurationRepositoryTest extends LmsRepositoryAbstractTest {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private AssessmentConfigurationRepository assessmentConfigurationRepository;
	
	@Test
	public void test1() {
		
	}
	
	@Test
	@Transactional
	public void findLockedCourse()
	{
		String enrollmentId = "271411,271412,271413,271414,271415,271416,271417,271418";
		
		try {
			
			List<AssessmentConfigurationProjection> ac = assessmentConfigurationRepository.getAssessmentConfigurationByEnrollmentId(enrollmentId);
			logger.info("View Assessment status is :: " + ac.get(0).getEnrollmentID() +"---"+ac.get(0).getIsViewAssessmentAllowed());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}