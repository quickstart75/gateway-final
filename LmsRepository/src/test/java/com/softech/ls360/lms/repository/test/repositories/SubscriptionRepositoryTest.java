package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class SubscriptionRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private SubscriptionRepository subscriptionRepository;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	@Transactional
	public void findByUserName() {
		
		logger.info("findByUserName");
		
		String userName = "ssadiq_qa_lnr_cancel_29062016";
		try {
			
			List<Subscription> subsceriptions = subscriptionRepository.findByVu360User_usernameAndSubscriptionStatus(userName, "CANCEL");
			
			logger.info(subsceriptions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
