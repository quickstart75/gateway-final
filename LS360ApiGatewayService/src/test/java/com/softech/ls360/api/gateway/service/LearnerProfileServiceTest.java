package com.softech.ls360.api.gateway.service;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfile;
import com.softech.ls360.lms.repository.entities.CreditReportingField;
import com.softech.ls360.lms.repository.repositories.CreditReportingFieldRepository;
import com.softech.ls360.util.json.JsonUtil;

public class LearnerProfileServiceTest extends LS360ApiGatewayServiceAbstractTest {

	@Inject
	private LearnerProfileService learnerProfileService;
	@Inject 
	private CreditReportingFieldRepository creditReportingFieldRepository;
	
	@Test
	public void test1() {
		
	}
	
	@Test
	public void getLearnerProfile() {
		String userName = "subscription_learner1@lms.com";
		
		LearnerProfile learnerProfile = learnerProfileService.getLearnerProfile(userName);
		
		System.out.println(JsonUtil.convertObjectToJson(learnerProfile));
		
	}
	
	//@Test
	public void getReportingFields(){
		Long learnerId = 572405L;
		
		List<CreditReportingField> crf = creditReportingFieldRepository.findAllByLearnerId(learnerId);
	}
}
