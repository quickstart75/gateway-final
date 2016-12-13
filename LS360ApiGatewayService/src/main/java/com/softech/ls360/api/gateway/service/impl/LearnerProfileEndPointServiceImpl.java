package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.api.gateway.service.CreditReportingFieldService;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.DistributorService;
import com.softech.ls360.api.gateway.service.LearnerProfileEndPointService;
import com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfileCreditReportingField;
import com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfileCustomField;
import com.softech.ls360.lms.repository.entities.CreditReportingField;
import com.softech.ls360.lms.repository.entities.CreditReportingFieldValue;
import com.softech.ls360.lms.repository.entities.CustomField;
import com.softech.ls360.lms.repository.entities.CustomFieldValue;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.Distributor;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.LearnerLicense;
import com.softech.ls360.lms.repository.entities.LearnerProfile;
import com.softech.ls360.lms.repository.entities.LearnerValidationAnswers;
import com.softech.ls360.lms.repository.entities.SingleSelectCustomFieldOption;
import com.softech.ls360.lms.repository.entities.ValidationQuestion;
import com.softech.ls360.lms.repository.projection.credit.reporting.filed.CreditReportingFieldValueChoiceProjection;
import com.softech.ls360.lms.repository.projection.custom.reporting.field.SingleSelectCustomFieldOptionProjection;
import com.softech.ls360.lms.repository.repositories.CreditReportingFieldValueChoiceRepository;
import com.softech.ls360.lms.repository.repositories.CreditReportingFieldValueRepository;
import com.softech.ls360.lms.repository.repositories.CustomFieldValueRepository;
import com.softech.ls360.lms.repository.repositories.LearnerLicenseRepository;
import com.softech.ls360.lms.repository.repositories.LearnerProfileRepository;
import com.softech.ls360.lms.repository.repositories.LearnerValidationAnswersRepository;
import com.softech.ls360.lms.repository.repositories.SingleSelectCustomFieldOptionRepository;
import com.softech.ls360.lms.repository.repositories.ValidationQuestionRepository;

@Service
public class LearnerProfileEndPointServiceImpl implements LearnerProfileEndPointService {

private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LearnerProfileRepository learnerProfileRepository;
	
	@Inject
	private CreditReportingFieldService creditReportingFieldService;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private DistributorService distributorService;
	
	@Inject
	private CreditReportingFieldValueRepository creditReportingFieldValueRepository;
	
	@Inject
	private CreditReportingFieldValueChoiceRepository creditReportingFieldValueChoiceRepository;
	
	@Inject
	private CustomFieldValueRepository customFieldValueRepository;
	
	@Inject
	private SingleSelectCustomFieldOptionRepository singleSelectCustomFieldOptionRepository;
	
	@Inject
	private LearnerValidationAnswersRepository learnerValidationAnswersRepository;
	
	@Inject
	private ValidationQuestionRepository validationQuestionRepository;
	
	@Inject
	private LearnerLicenseRepository learnerLicenseRepository;
	
	@Override
	public void getLearnerProfile(String userName) throws Exception {
		
		LearnerProfile learnerProfile = learnerProfileRepository.findByLearner_Vu360User_Username(userName);
		if (learnerProfile != null) {
			Learner learner = learnerProfile.getLearner();
			setCustomerCustomFields(learnerProfile);
			setDistributorCustomFields(learnerProfile);
			List<Long> customFieldIds = getCustomFieldIds(learnerProfile);
			if (!CollectionUtils.isEmpty(customFieldIds)) {
				//List<CustomFieldValue> customFieldValues = customFieldValueRepository.findByCustomFieldIdIn(customFieldIds);
				//learnerProfile.setCustomFieldValues(customFieldValues);
			}
			List<CreditReportingFieldValue> creditReportingFieldValues = getCreditReportingFieldValues(learnerProfile);
			List<LearnerValidationAnswers> learnerValidationAnswers = getLearnerValidationAnswers(learner);
			List<ValidationQuestion> validationQuestions = getLearnerProfileValidationQuestions();
			List<LearnerLicense> learnerLicenses = getLearnerLicenses(learner);
			Map<Long, LearnerProfileCreditReportingField> learnerPrifleCreditReportingFieldsMap = getLearnerPrifleCreditReportingFields(creditReportingFieldValues);
			Map<Long, LearnerProfileCustomField> learnerProfleCustomFieldsMap = getLearnerProfileCustomFields(learnerProfile);
		}
	}
	
	private Customer getCustomer(LearnerProfile learnerProfile) {
		
		Customer customer = null;
		if (learnerProfile != null)  {
			Learner learner = learnerProfile.getLearner();
			if (learner != null) {
				customer = learner.getCustomer();
			}
		}
		return customer;
	}
	
	private void setCustomerCustomFields(LearnerProfile learnerProfile) {
		Customer customer = getCustomer(learnerProfile);
		if (customer != null) {
			Long customerId = customer.getId();
			Set<CustomField> customerCustomFields = customerService.findCustomerCustomFields(customerId);
			customer.setCustomFields(customerCustomFields);
		}
	}
	
	private void setDistributorCustomFields(LearnerProfile learnerProfile) {
		Customer customer = getCustomer(learnerProfile);
		if (customer != null) {
			Distributor distributor = customer.getDistributor();
			if (distributor != null) {
				Long distributorId = distributor.getId();
				Set<CustomField> distributorCustomFields = distributorService.findDistributorCustomFields(distributorId);
				distributor.setCustomFields(distributorCustomFields);
			}
		}
	}
	
	private List<CreditReportingFieldValue> getCreditReportingFieldValues(LearnerProfile learnerProfile) throws Exception {
		
		List<CreditReportingFieldValue> creditReportingFieldValues  = null;
		if (learnerProfile != null)  {
			Learner learner = learnerProfile.getLearner();
			Long learnerId = learner.getId();
			Set<CreditReportingField> creditReportingFields = creditReportingFieldService.getCreditReportingFields(learnerId);
			List<Long> creditReportingFieldIds = creditReportingFieldService.getCreditReportingFieldIds(creditReportingFields);
			if (!CollectionUtils.isEmpty(creditReportingFieldIds)) {
				Long learnerProfileId = learnerProfile.getId();
				creditReportingFieldValues = creditReportingFieldValueRepository.findByLearnerProfile_IdAndCreditReportingField_IdIn(learnerProfileId, creditReportingFieldIds);
			}	 
		}
		return creditReportingFieldValues;
	}
	
	private List<Long> getCustomFieldIds(LearnerProfile learnerProfile) {
		
		List<Long> customerCustomFieldIds = null;
		List<Long> distributorCustomFieldIds = null;
		Customer customer = getCustomer(learnerProfile);
		if (customer != null) {
			Set<CustomField> customerCustomFields = customer.getCustomFields();
			customerCustomFieldIds = getCustomFieldIds(customerCustomFields);
			Distributor distributor = customer.getDistributor();
			if (distributor != null) {
				Set<CustomField> distributorCustomFields = distributor.getCustomFields();
				distributorCustomFieldIds = getCustomFieldIds(distributorCustomFields);
			}
		}
		List<Long> customFieldIds = getAllCustomFieldIds(customerCustomFieldIds, distributorCustomFieldIds);
		return customFieldIds;
	}
	
	private List<Long> getCustomFieldIds(Set<CustomField> customFields) {
		List<Long> customFieldIds = null;
		if (!CollectionUtils.isEmpty(customFields)) {
			customFieldIds = customFields
					.stream()
					.map(CustomField::getId)
					.collect(Collectors.toList());
		}
		return customFieldIds;
	}
	
	private List<Long> getAllCustomFieldIds(List<Long> customerCustomFieldIds, List<Long> distributorCustomFieldIds) {
		
		List<Long> allCustomFieldIds = null;
		
		if (!CollectionUtils.isEmpty(customerCustomFieldIds) || !CollectionUtils.isEmpty(distributorCustomFieldIds)) {
			allCustomFieldIds = new ArrayList<>();
			
			if (!CollectionUtils.isEmpty(customerCustomFieldIds)) {
				allCustomFieldIds.addAll(customerCustomFieldIds);
			}
			
			if (!CollectionUtils.isEmpty(distributorCustomFieldIds)) {
				allCustomFieldIds.addAll(distributorCustomFieldIds);
			}
		}
		return allCustomFieldIds;
	}
	
	private List<LearnerValidationAnswers> getLearnerValidationAnswers(Learner learner) {
		List<LearnerValidationAnswers> learnerValidationAnswers = null;
		if (learner != null) {
			Long learnerId = learner.getId();
			learnerValidationAnswers = learnerValidationAnswersRepository.findByLearnerId(learnerId);
		}
		return 	learnerValidationAnswers;
	}
	
	private List<ValidationQuestion> getLearnerProfileValidationQuestions() {
		List<Long> learnerProfileValidationQuestionIds = LongStream.rangeClosed(101, 115).boxed().collect(Collectors.toList());
		return validationQuestionRepository.findByIdIn(learnerProfileValidationQuestionIds);	
			
	}
	
	private List<LearnerLicense> getLearnerLicenses(Learner learner) {
	    List<LearnerLicense> learnerLicenses = null;
		if (learner != null) {
			Long learnerId = learner.getId();
			learnerLicenses = learnerLicenseRepository.findByLearnerId(learnerId);
		}
		return learnerLicenses;
	}
	
    private Map<Long, LearnerProfileCreditReportingField> getLearnerPrifleCreditReportingFields(List<CreditReportingFieldValue> creditReportingFieldValues) {
	    Map<Long, LearnerProfileCreditReportingField> learnerPrifleCreditReportingFieldsMap = null;
		if (!CollectionUtils.isEmpty(creditReportingFieldValues)) {
			learnerPrifleCreditReportingFieldsMap = new LinkedHashMap<>();
			for (CreditReportingFieldValue creditReportingFieldValue : creditReportingFieldValues) {
			    Long creditReportingFieldValueId = creditReportingFieldValue.getId();
			    CreditReportingField  creditReportingField = creditReportingFieldValue.getCreditReportingField();
				if (creditReportingField != null) {
					Long creditReportingFieldId = creditReportingField.getId();
					String creditReportingFieldType = creditReportingField.getFieldType();
					if (StringUtils.isNotBlank(creditReportingFieldType)) {
						creditReportingFieldType = creditReportingFieldType.toUpperCase();
						LearnerProfileCreditReportingField learnerProfileCreditReportingField = null;
						switch(creditReportingFieldType) {
							case "SINGLELINETEXTCREDITREPORTINGFIELD":
							case "DATETIMECREDITREPORTINGFIELD":
							case "NUMERICCREDITREPORTINGFIELD":
							case "TELEPHONENUMBERCREDITREPORTINGFIELD":
							    learnerProfileCreditReportingField = new LearnerProfileCreditReportingField(creditReportingFieldValue, null);
							    learnerPrifleCreditReportingFieldsMap.put(creditReportingFieldValueId, learnerProfileCreditReportingField);
							    break;
							case "MULTISELECTCREDITREPORTINGFIELD":
							    if (!learnerPrifleCreditReportingFieldsMap.containsKey(creditReportingFieldValueId)) {
							    	List<CreditReportingFieldValueChoiceProjection> multiSelectOptions = creditReportingFieldValueChoiceRepository.findByCreditReportingFieldId(creditReportingFieldId);
							    	learnerProfileCreditReportingField = new LearnerProfileCreditReportingField(creditReportingFieldValue, multiSelectOptions);
							    	learnerPrifleCreditReportingFieldsMap.put(creditReportingFieldValueId, learnerProfileCreditReportingField);
							    }
							    break;
							case "SINGLESELECTCREDITREPORTINGFIELD" :
							    List<CreditReportingFieldValueChoiceProjection> singleSelectOptions = creditReportingFieldValueChoiceRepository.findByCreditReportingFieldId(creditReportingFieldId);
							    learnerProfileCreditReportingField = new LearnerProfileCreditReportingField(creditReportingFieldValue, singleSelectOptions);
							    learnerPrifleCreditReportingFieldsMap.put(creditReportingFieldValueId, learnerProfileCreditReportingField);
							    break;
							case "SSNCREDITREPORTINGFIELD" :
							    learnerProfileCreditReportingField = new LearnerProfileCreditReportingField(creditReportingFieldValue, null);
							    learnerPrifleCreditReportingFieldsMap.put(creditReportingFieldValueId, learnerProfileCreditReportingField);
							    break;
						} // end of switch
					}
				}	
			}
		 }
		 return learnerPrifleCreditReportingFieldsMap;
	}
	
	private Map<Long, LearnerProfileCustomField> getLearnerProfileCustomFields(LearnerProfile learnerProfile) {

		Map<Long, LearnerProfileCustomField> learnerProfleCustomFieldsMap = null;
		if (learnerProfile != null) {
	        Set<CustomFieldValue> customFieldValues = learnerProfile.getCustomFieldValues();
			if (!CollectionUtils.isEmpty(customFieldValues)) {
			    learnerProfleCustomFieldsMap = new LinkedHashMap<>();
				for (CustomFieldValue customFieldValue : customFieldValues) {
				    Long customFieldValueId = customFieldValue.getId();
					CustomField customField = customFieldValue.getCustomField();
					if (customField != null) {
				        Long customFieldId = customField.getId();
						String customFieldType = customField.getFieldType();
						if (StringUtils.isNotBlank(customFieldType)) {
							customFieldType = customFieldType.toUpperCase();
							LearnerProfileCustomField learnerProfileCustomField = null;
							switch(customFieldType) {
							    case "SINGLELINETEXTCUSTOMFIELD":
								case "NUMERICCUSTOMFIELD":
								case "DATETIMECUSTOMFIELD":
								    learnerProfileCustomField = new LearnerProfileCustomField(customFieldValue, null);
								    learnerProfleCustomFieldsMap.put(customFieldValueId, learnerProfileCustomField);
								    break;
								case "MULTISELECTCUSTOMFIELD":
									List<SingleSelectCustomFieldOption> learnerSelectedChoices = customFieldValue.getSingleSelectCustomFieldOptions();
								    if (!learnerProfleCustomFieldsMap.containsKey(customFieldValueId)) {
								    	List<SingleSelectCustomFieldOptionProjection> multiSelectOptions = singleSelectCustomFieldOptionRepository.findByCustomFieldId(customFieldId);
								    	learnerProfileCustomField = new LearnerProfileCustomField(customFieldValue, multiSelectOptions);
								    	learnerProfleCustomFieldsMap.put(customFieldValueId, learnerProfileCustomField);
								    }
								    break;
								case "SINGLESELECTCUSTOMFIELD":
								    List<SingleSelectCustomFieldOptionProjection> singleSelectOptions = singleSelectCustomFieldOptionRepository.findByCustomFieldId(customFieldId);
								    learnerProfileCustomField = new LearnerProfileCustomField(customFieldValue, singleSelectOptions);
								    learnerProfleCustomFieldsMap.put(customFieldValueId, learnerProfileCustomField);
								    break;
							} //end switch
						}	
					}
				}
	        }
        }
		return learnerProfleCustomFieldsMap;
	}
	
}
