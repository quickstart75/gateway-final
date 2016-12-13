package com.softech.ls360.lms.repository.test.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.repository.entities.CustomField;
import com.softech.ls360.lms.repository.entities.CustomFieldValue;
import com.softech.ls360.lms.repository.entities.SingleSelectCustomFieldOption;
import com.softech.ls360.lms.repository.projection.custom.reporting.field.SingleSelectCustomFieldOptionProjection;
import com.softech.ls360.lms.repository.repositories.CustomFieldValueRepository;
import com.softech.ls360.lms.repository.repositories.SingleSelectCustomFieldOptionRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class CustomFieldValueRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private CustomFieldValueRepository customFieldValueRepository;
	
	@Inject
	private SingleSelectCustomFieldOptionRepository singleSelectCustomFieldOptionRepository;
	
	//@Test
	public void test1() {
		
	}
	
	@Test
	public void findCustomFieldValue() {
		
		logger.info("findCustomFieldValue");
		
		try {
			List<Long> customFieldIds = Arrays.asList(16321L, 16401L, 16402L, 16403L, 16404L);
			List<CustomFieldValue> customFieldValues = customFieldValueRepository.findByCustomFieldIdIn(customFieldIds);
			if (!CollectionUtils.isEmpty(customFieldValues)) {
				for (CustomFieldValue customFieldValue : customFieldValues) {
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
							}
						}	
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
