package com.softech.ls360.lms.repository.test.repositories;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.repository.entities.CreditReportingField;
import com.softech.ls360.lms.repository.entities.CreditReportingFieldValue;
import com.softech.ls360.lms.repository.entities.CreditReportingFieldValueChoice;
import com.softech.ls360.lms.repository.projection.credit.reporting.filed.CreditReportingFieldValueChoiceProjection;
import com.softech.ls360.lms.repository.repositories.CreditReportingFieldValueChoiceRepository;
import com.softech.ls360.lms.repository.repositories.CreditReportingFieldValueRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class CreditReportingFieldValueRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private CreditReportingFieldValueRepository creditReportingFieldValueRepository;
	
	@Inject
	private CreditReportingFieldValueChoiceRepository creditReportingFieldValueChoiceRepository;
	
	//@Test
	public void test1() {
		
	}
	
	@Test
	@Transactional
	public void findCreditReportingFieldValue() {
		
		logger.info("findByUserName");
		
		Long learnerProfileId = 2504L;
		List<Long> creditReportingFieldIds = Arrays.asList(552L, 820L, 1003L, 1102L, 1103L, 1104L, 1104L, 363L, 810L, 811L,
				818L, 821L, 1402L, 1902L, 1352L, 1353L, 2652L, 2653L, 2654L);
		
		try {
			List<CreditReportingFieldValue> creditReportingFieldValues = creditReportingFieldValueRepository.findByLearnerProfile_IdAndCreditReportingField_IdIn(learnerProfileId, creditReportingFieldIds);
			if (!CollectionUtils.isEmpty(creditReportingFieldValues)) {
				for (CreditReportingFieldValue creditReportingFieldValue : creditReportingFieldValues) {
					CreditReportingField  creditReportingField = creditReportingFieldValue.getCreditReportingField();
					if (creditReportingField != null) {
						Long creditReportingFieldId = creditReportingField.getId();
						String creditReportingFieldType = creditReportingField.getFieldType();
						if (StringUtils.isNotBlank(creditReportingFieldType)) {
							String learnerSelectedValue = null;
							creditReportingFieldType = creditReportingFieldType.toUpperCase();
							switch(creditReportingFieldType) {
							    case "SINGLELINETEXTCREDITREPORTINGFIELD":
							    case "DATETIMECREDITREPORTINGFIELD":
							    case "NUMERICCREDITREPORTINGFIELD":
							    case "TELEPHONENUMBERCREDITREPORTINGFIELD":
							    	learnerSelectedValue = creditReportingFieldValue.getValue();
							    	break;
							    case "MULTISELECTCREDITREPORTINGFIELD":
							    	List<CreditReportingFieldValueChoice> learnerSelectedChoices = creditReportingFieldValue.getCreditReportingFieldValueChoices();
							    	List<CreditReportingFieldValueChoiceProjection> multiSelectOptions = creditReportingFieldValueChoiceRepository.findByCreditReportingFieldId(creditReportingFieldId);
							    	if (!CollectionUtils.isEmpty(multiSelectOptions)) {
							    		for (CreditReportingFieldValueChoiceProjection multiSelectOption : multiSelectOptions) {
							    			
							    		}
							    	}
							    	
							    	if (!CollectionUtils.isEmpty(learnerSelectedChoices)) {
							    		
							    	}
							    	
							        break;
							    case "SINGLESELECTCREDITREPORTINGFIELD" :
							    	learnerSelectedValue = creditReportingFieldValue.getValue();
							    	List<CreditReportingFieldValueChoiceProjection> singleSelectOptions = creditReportingFieldValueChoiceRepository.findByCreditReportingFieldId(creditReportingFieldId);
							    	if (!CollectionUtils.isEmpty(singleSelectOptions)) {
							    		for (CreditReportingFieldValueChoiceProjection singleSelectOption : singleSelectOptions) {
							    			
							    		}
							    	}
							    	//List<String> choices
							    	break;
							    case "SSNCREDITREPORTINGFIELD" :
							    	learnerSelectedValue = creditReportingFieldValue.getValue();
							    	break;
							} // end of switch
						}
					}	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
