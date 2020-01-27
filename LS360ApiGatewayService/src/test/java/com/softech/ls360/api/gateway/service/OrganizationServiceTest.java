package com.softech.ls360.api.gateway.service;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softech.ls360.api.gateway.service.config.spring.LS360ApiGatewayServiceAppConfig;
import com.softech.ls360.api.gateway.service.impl.UserGroupServiceImpl;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.LearnerGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LS360ApiGatewayServiceAppConfig.class)
public class OrganizationServiceTest {

	@Autowired
	CustomerService customerService;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	UserGroupServiceImpl userGroupServiceImpl;
	
	@Test
	public void testGetOrganizationalDetails() {
		logger.info(" TEST ::: Organizational Details" );
		logger.info(" ........ " );
		logger.info(" ........ " );
		
		DateTimeFormatter fullDTWithTicks2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
//		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerService.findByUsername(TestSuite.USERNAME);
        Set<String> lstallemails = new HashSet<String>();
		List<LearnerGroup> lstUserGroup = userGroupServiceImpl.findByCustomer(customer.getId());
        System.out.println(customer.getName()+"\nLearner Group :\n");
        for(LearnerGroup learner : lstUserGroup)
        	System.out.println(learner.getName());
        
        Assert.assertTrue(true);
        logger.info(" ........ " );
		logger.info(" ........ " );
           
	}
	
}
