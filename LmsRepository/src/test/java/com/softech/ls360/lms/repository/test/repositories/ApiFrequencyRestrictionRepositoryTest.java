package com.softech.ls360.lms.repository.test.repositories;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.lms.repository.entities.ApiFrequencyRestriction;
import com.softech.ls360.lms.repository.repositories.ApiFrequencyRestrictionRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class ApiFrequencyRestrictionRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private ApiFrequencyRestrictionRepository apiFrequencyRestrictionRepository;
	
	@Test
	public void test1() {
		
	}
	
	//@Test
	public void findByDistributorIdAndOperation() {
		
		Long distributorId = new Long(26951);
		String operation = "getResellerLearnerEnrollments";
		try {
			ApiFrequencyRestriction apiFrequencyRestriction = apiFrequencyRestrictionRepository.findByDistributorIdAndOperation(distributorId, operation);
			System.out.println(apiFrequencyRestriction);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
