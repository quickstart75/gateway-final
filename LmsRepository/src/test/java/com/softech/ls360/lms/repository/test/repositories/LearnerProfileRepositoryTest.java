package com.softech.ls360.lms.repository.test.repositories;

import com.softech.ls360.lms.repository.entities.*;
import com.softech.ls360.lms.repository.projection.custom.reporting.field.SingleSelectCustomFieldOptionProjection;
import com.softech.ls360.lms.repository.repositories.LearnerProfileRepository;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;
import com.softech.ls360.lms.repository.repositories.SingleSelectCustomFieldOptionRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

public class LearnerProfileRepositoryTest extends LmsRepositoryAbstractTest  {

	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LearnerRepository learnerRepository;

	@Inject
	private LearnerProfileRepository learnerProfileRepository;
	
	@Inject
	private SingleSelectCustomFieldOptionRepository singleSelectCustomFieldOptionRepository;
	
	//@Test
	//@Transactional
	public void findLearnerProfileWithLearnerAndUserAndCustomerAndCustomerCustomFieldsAndDistributorAndDistributorCustomFields() {
		
		//String username = "SQA_demo_customer@360training.com";
		String username = "admin";
		
		LearnerProfile learnerProfile = learnerProfileRepository.findByLearner_Vu360User_Username(username);
		if (learnerProfile != null) {
			 Set<CustomFieldValue> customFieldValues = learnerProfile.getCustomFieldValues();
			 if (!CollectionUtils.isEmpty(customFieldValues)) {
					for (CustomFieldValue customFieldValue : customFieldValues) {
					    Long customFieldValueId = customFieldValue.getId();
						CustomField customField = customFieldValue.getCustomField();
						if (customField != null) {
					        Long customFieldId = customField.getId();
							String customFieldType = customField.getFieldType();
							if (StringUtils.isNotBlank(customFieldType)) {
								String learnerSelectedValue = null;
								customFieldType = customFieldType.toUpperCase();
								switch(customFieldType) {
								    case "SINGLELINETEXTCUSTOMFIELD":
									case "NUMERICCUSTOMFIELD":
									case "DATETIMECUSTOMFIELD":
										learnerSelectedValue = customFieldValue.getValue();
									    break;
									case "MULTISELECTCUSTOMFIELD":
										List<SingleSelectCustomFieldOption> learnerSelectedChoices = customFieldValue.getSingleSelectCustomFieldOptions();
								    	List<SingleSelectCustomFieldOptionProjection> multiSelectOptions = singleSelectCustomFieldOptionRepository.findByCustomFieldId(customFieldId);
								    	if (!CollectionUtils.isEmpty(multiSelectOptions)) {
								    		
								    	}
								    	
								    	if (!CollectionUtils.isEmpty(learnerSelectedChoices)) {
								    		
								    	}
									    break;
									case "SINGLESELECTCUSTOMFIELD":
										learnerSelectedValue = customFieldValue.getValue();
								    	List<SingleSelectCustomFieldOptionProjection> singleSelectOptions = singleSelectCustomFieldOptionRepository.findByCustomFieldId(customFieldId);
								    	if (!CollectionUtils.isEmpty(singleSelectOptions)) {
								    		
								    	}
									    break;
								} //end switch
							}	
						}
					}
		        }
		    
		}
		logger.info(learnerProfile);
	}

	//@Test
	public void testFindLearnerProfileAddress(){
		Learner l = learnerRepository.findByVu360UserUsername("manager1");
		LearnerProfile a = learnerProfileRepository.findOne(l.getId());
		System.out.println(a);
	}
	
	//@Test
	public void findLearnerProfile(){
		
		String userName = "ali.waqas@360training.com";
		
		LearnerProfile learnerProfile = learnerProfileRepository.findByUserName(userName);
		if(learnerProfile != null){
			
			logger.info(learnerProfile.getMobilePhone());
		}
		
	}
	
}
