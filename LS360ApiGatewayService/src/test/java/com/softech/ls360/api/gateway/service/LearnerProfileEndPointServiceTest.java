package com.softech.ls360.api.gateway.service;

import javax.inject.Inject;

import org.junit.Test;

public class LearnerProfileEndPointServiceTest extends LS360ApiGatewayServiceAbstractTest {

	@Inject
	private LearnerProfileEndPointService learnerProfileEndPointService;;
	
	//@Test
	public void test1() {
		
	}
	
	@Test
	public void getLearnerProfileByUserName() {
		String userName = "SQA_demo_customer@360training.com";
		try {
			learnerProfileEndPointService.getLearnerProfile(userName);;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
