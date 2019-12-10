package com.softech.ls360.lms.repository.test.repositories;

import java.math.BigInteger;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


import com.softech.ls360.lms.repository.entities.LCMSAuthor;

import com.softech.ls360.lms.repository.repositories.LCMSAuthorRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class LCMSAuthorRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LCMSAuthorRepository lcmsAuthorRepositoryTest;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void findByUserID() {
		
		logger.info("findByUserID");
		
		BigInteger user_id2 = BigInteger.valueOf(11);
		try {
			
			LCMSAuthor author = lcmsAuthorRepositoryTest.findUserByUserID(user_id2);
			logger.info(author.getUserID());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}


