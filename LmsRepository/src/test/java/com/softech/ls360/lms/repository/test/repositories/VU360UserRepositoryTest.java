package com.softech.ls360.lms.repository.test.repositories;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class VU360UserRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private VU360UserRepository vu360UserRepository;
	
	//@Test
	public void test1() {
		
	}
	
	@Test
	public void findByUserName() {
		
		logger.info("findByUserName");
		
		String userName = "admin";
		try {
			
			VU360User vu360User = vu360UserRepository.findByUsername(userName);
			logger.info(vu360User);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
