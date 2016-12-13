package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.DistributorService;
import com.softech.ls360.api.gateway.service.LearnerProfileService;
import com.softech.ls360.api.gateway.service.model.learner.profile.FieldOption;
import com.softech.ls360.api.gateway.service.model.learner.profile.LearnerCreditReportingField;
import com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfileAddress;
import com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfileTimeZone;
import com.softech.ls360.api.gateway.service.model.learner.profile.PersonalInformation;
import com.softech.ls360.api.gateway.service.model.learner.profile.Question;
import com.softech.ls360.api.gateway.service.model.learner.profile.ValidationQuestionSet;
import com.softech.ls360.lms.repository.entities.CreditReportingField;
import com.softech.ls360.lms.repository.entities.CreditReportingFieldValue;
import com.softech.ls360.lms.repository.entities.CreditReportingFieldValueChoice;
import com.softech.ls360.lms.repository.entities.CustomField;
import com.softech.ls360.lms.repository.entities.CustomFieldValue;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.Distributor;
import com.softech.ls360.lms.repository.entities.LearnerProfile;
import com.softech.ls360.lms.repository.entities.LearnerValidationAnswers;
import com.softech.ls360.lms.repository.entities.SingleSelectCustomFieldOption;
import com.softech.ls360.lms.repository.entities.TimeZone;
import com.softech.ls360.lms.repository.entities.ValidationQuestion;
import com.softech.ls360.lms.repository.repositories.CreditReportingFieldRepository;
import com.softech.ls360.lms.repository.repositories.CreditReportingFieldValueRepository;
import com.softech.ls360.lms.repository.repositories.LearnerProfileRepository;
import com.softech.ls360.lms.repository.repositories.LearnerValidationAnswersRepository;
import com.softech.ls360.lms.repository.repositories.TimeZoneRepository;
import com.softech.ls360.lms.repository.repositories.ValidationQuestionRepository;

@Service
public class LearnerProfileServiceImpl implements LearnerProfileService{

	public static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LearnerProfileRepository learnerProfileRepository;
	
	@Inject
	private LearnerValidationAnswersRepository learnerValidationAnswersRepository;
	
	@Inject
	private ValidationQuestionRepository validationQuestionRepository;
		
	@Inject 
	private CreditReportingFieldRepository creditReportingFieldRepository;
	
	@Inject
	private CreditReportingFieldValueRepository creditReportingFieldValueRepository;
	
	@Inject
	private TimeZoneRepository timeZoneRepository;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private DistributorService distributorService;
	
	@Override
	@Transactional
	public com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfile getLearnerProfile(String userName) {
		
		logger.info("Call for Learner Profile " + getClass().getName());
		
		com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfile lProfile = new com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfile();
		
		try{
		LearnerProfile learnerProfile = learnerProfileRepository.findByLearner_Vu360User_Username(userName);
		Long learnerId = learnerProfile.getLearner().getId();
		
		PersonalInformation personalInformation = getPersonalInformation(learnerProfile);
		List<ValidationQuestionSet> validationQuestionSets = getValidationQuestions(learnerId);
		
		List<LearnerCreditReportingField> learnerCreditReportingFields = getCreditReportingFields(learnerProfile);
		
		List<LearnerCreditReportingField> customFields = getCustomFields(learnerProfile);

	
		lProfile.setPersonalInformation(personalInformation);
		lProfile.setValidationQuestions(validationQuestionSets);
		lProfile.setReportingFields(learnerCreditReportingFields);
		lProfile.setCustomFields(customFields);
		}catch(Exception e){
			logger.info("Error::" + e.getMessage());
		}
		return lProfile;
	}
	
	public PersonalInformation getPersonalInformation(LearnerProfile learnerProfile){
		
		logger.info("Call for Learner Personal Information " + getClass().getName());
		
		PersonalInformation personalInformation = new PersonalInformation();
		
		try{
		personalInformation.setFirstName(learnerProfile.getLearner().getVu360User().getFirstName());
		personalInformation.setMiddleName(learnerProfile.getLearner().getVu360User().getMiddleName());
		personalInformation.setLastName(learnerProfile.getLearner().getVu360User().getLastName());
		personalInformation.setEmailAddress(learnerProfile.getLearner().getVu360User().getEmailAddress());
		personalInformation.setPhone(learnerProfile.getMobilePhone());
		personalInformation.setOfficePhone(learnerProfile.getOfficePhone());
		personalInformation.setOfficeExtension(learnerProfile.getOfficePhoneExt());
		personalInformation.setUserName(learnerProfile.getLearner().getVu360User().getUsername());
		personalInformation.setPassword(learnerProfile.getLearner().getVu360User().getPassword());
		
		com.softech.ls360.lms.repository.entities.Address address = learnerProfile.getAddress1(); 
		LearnerProfileAddress leanerAddress = new LearnerProfileAddress();		
		if(address != null){
			leanerAddress.setStreetAddress(address.getStreetAddress());
			leanerAddress.setCity(address.getCity());
			leanerAddress.setState(address.getState());
			leanerAddress.setZipCode(address.getZipcode());
			leanerAddress.setCountry(address.getCountry());
		}
		personalInformation.setAddress1(leanerAddress);
		
		address = learnerProfile.getAddress2(); 
		leanerAddress = new LearnerProfileAddress();		
		if(address != null){
			leanerAddress.setStreetAddress(address.getStreetAddress());
			leanerAddress.setCity(address.getCity());
			leanerAddress.setState(address.getState());
			leanerAddress.setZipCode(address.getZipcode());
			leanerAddress.setCountry(address.getCountry());
			
			personalInformation.setAddress2(leanerAddress);
		}
		
		personalInformation.setTimeZone(getLearnerTimeZone(learnerProfile));
		
		}catch(Exception e){
			logger.info("Error::" + e.getMessage());
		}
		
		return personalInformation;
	}
	
	public List<LearnerProfileTimeZone> getLearnerTimeZone(LearnerProfile learnerProfile){
		logger.info("Call for Time Zone " + getClass().getName());
		
		List<LearnerProfileTimeZone> learnerTimeZones = new ArrayList<LearnerProfileTimeZone>();
		
		try{
		
		Long learnerSelectedTimeZone = learnerProfile.getTimeZone().getId();
		
		List<TimeZone> timeZones = timeZoneRepository.findAll();
		
		for(TimeZone tz: timeZones){
			LearnerProfileTimeZone learnerProfileTimeZone = new LearnerProfileTimeZone();
			String timeZone = "( " + tz.getCode() + " " + tz.getHours() + " : " + tz.getMinutes() + " ) " + tz.getZone();
			learnerProfileTimeZone.setValue(tz.getId().toString());
			learnerProfileTimeZone.setText(timeZone);
			
			if(learnerSelectedTimeZone == tz.getId())
				learnerProfileTimeZone.setSelected(true);
			
			learnerTimeZones.add(learnerProfileTimeZone);
		}
		}catch(Exception e){
			logger.info("Error::" + e.getMessage());
		}
		return learnerTimeZones;
	}

	public List<ValidationQuestionSet> getValidationQuestions(Long learnerId){
		
		logger.info("Call for Validation Questions and Learner Answers " + getClass().getName());
		
		List<ValidationQuestionSet> validationQuestionSets = new ArrayList<ValidationQuestionSet>();
		
		try{
		
		List<Long> learnerProfileValidationQuestionIds = LongStream.rangeClosed(101, 115).boxed().collect(Collectors.toList());
		
		List<ValidationQuestion> validationQuestions = validationQuestionRepository.findByIdIn(learnerProfileValidationQuestionIds);
		List<LearnerValidationAnswers> learnerValidationAnswers = learnerValidationAnswersRepository.findByLearnerId(learnerId);
		
		List<Question> questionSet1 = new ArrayList<Question>();
		List<Question> questionSet2 = new ArrayList<Question>();
		List<Question> questionSet3 = new ArrayList<Question>();
		List<Question> questionSet4 = new ArrayList<Question>();
		List<Question> questionSet5 = new ArrayList<Question>();
		
		String answerSet1 = "";
		String answerSet2 = "";
		String answerSet3 = "";
		String answerSet4 = "";
		String answerSet5 = "";
		
		Map<Long,String> learnerAnswers = new HashMap<Long, String>();
		
		if(learnerValidationAnswers != null)
		for(LearnerValidationAnswers vla: learnerValidationAnswers){
			learnerAnswers.put(vla.getValidationQuestion().getId(), vla.getAnswer());
		}
		
		Question question = new Question();
		question.setValue("0");
		question.setText("Select a question");
		question.setSelected(false);
		questionSet1.add(question);
		questionSet2.add(question);
		questionSet3.add(question);
		questionSet4.add(question);
		questionSet5.add(question);
		
		for(ValidationQuestion vq: validationQuestions){
			question = new Question();
			switch ((int)(long)vq.getId()){
			case 101:
			case 102:
			case 103:
				question.setValue(vq.getId().toString());
				question.setText(vq.getQuestionsItem());
				if(learnerAnswers.size()>0){
					if(learnerAnswers.get(vq.getId()) != null){
						question.setSelected(true);
						answerSet1 =  learnerAnswers.get(vq.getId());
					}
				}
				questionSet1.add(question);
				break;
			case 104:
			case 105:
			case 106:
				question.setValue(vq.getId().toString());
				question.setText(vq.getQuestionsItem());
				if(learnerAnswers.size()>0){
					if(learnerAnswers.get(vq.getId()) != null){
						question.setSelected(true);
						answerSet2 =  learnerAnswers.get(vq.getId());
					}
				}
				questionSet2.add(question);
				break;
			case 107:
			case 108:
			case 109:
				question.setValue(vq.getId().toString());
				question.setText(vq.getQuestionsItem());
				if(learnerAnswers.size()>0){
					if(learnerAnswers.get(vq.getId()) != null){
						question.setSelected(true);
						answerSet3 =  learnerAnswers.get(vq.getId());
					}
				}
				questionSet3.add(question);
				break;
			case 110:
			case 111:
			case 112:
				question.setValue(vq.getId().toString());
				question.setText(vq.getQuestionsItem());
				if(learnerAnswers.size()>0){
					if(learnerAnswers.get(vq.getId()) != null){
						question.setSelected(true);
						answerSet4 =  learnerAnswers.get(vq.getId());
					}
				}
				questionSet4.add(question);
				break;
			case 113:
			case 114:
			case 115:
				question.setValue(vq.getId().toString());
				question.setText(vq.getQuestionsItem());
				if(learnerAnswers.size()>0){
					if(learnerAnswers.get(vq.getId()) != null){
						question.setSelected(true);
						answerSet5 =  learnerAnswers.get(vq.getId());
					}
				}
				questionSet5.add(question);
			}
		}
		
		ValidationQuestionSet validationQuestionSet = new ValidationQuestionSet();
		
		validationQuestionSet.setQuestions(questionSet1);
		validationQuestionSet.setAnswer(answerSet1);
		validationQuestionSets.add(validationQuestionSet);
		
		validationQuestionSet = new ValidationQuestionSet();
		validationQuestionSet.setQuestions(questionSet2);
		validationQuestionSet.setAnswer(answerSet2);
		validationQuestionSets.add(validationQuestionSet);

		validationQuestionSet = new ValidationQuestionSet();
		validationQuestionSet.setQuestions(questionSet3);
		validationQuestionSet.setAnswer(answerSet3);
		validationQuestionSets.add(validationQuestionSet);

		validationQuestionSet = new ValidationQuestionSet();
		validationQuestionSet.setQuestions(questionSet4);
		validationQuestionSet.setAnswer(answerSet4);
		validationQuestionSets.add(validationQuestionSet);
		
		validationQuestionSet = new ValidationQuestionSet();
		validationQuestionSet.setQuestions(questionSet5);
		validationQuestionSet.setAnswer(answerSet5);
		validationQuestionSets.add(validationQuestionSet);
		
		//System.out.println(JsonUtil.convertObjectToJson(validationQuestionSets));
		}catch(Exception e){
			logger.info("Error::" + e.getMessage());
		}
		return validationQuestionSets;
	}
	
	public List<LearnerCreditReportingField> getCustomFields(LearnerProfile learnerProfile){
		
		logger.info("Call for Custom Fields " + getClass().getName());
		List<LearnerCreditReportingField> learnerCustomFields = new ArrayList<LearnerCreditReportingField>();
		try{
		Customer customer = learnerProfile.getLearner().getCustomer();
		Distributor distributor = customer.getDistributor();
		Set<CustomField> customFields = null;
		
		
		if (customer != null) {
			Long customerId = customer.getId();
			customFields = customerService.findCustomerCustomFields(customerId);
		}
		
		if (distributor != null) {
			Long distributorId = distributor.getId();
			Set<CustomField> distributorCustomFields = distributorService.findDistributorCustomFields(distributorId);
			customFields.addAll(distributorCustomFields);
		}
		
		Set<CustomFieldValue> learnerValues = learnerProfile.getCustomFieldValues();

		for(CustomField cf:customFields){
			if(cf == null)
				continue;
			List<SingleSelectCustomFieldOption> fieldOptions = cf.getSingleSelectCustomFieldOptions();
			LearnerCreditReportingField lcf = new LearnerCreditReportingField();
			lcf.setValue( cf.getId().toString());
			lcf.setRequired(cf.isFieldRequiredTf());
			lcf.setLabel(cf.getFieldLabel());
			lcf.setEncrypted(cf.isFieldEncryptedTf());
			lcf.setAlignment(cf.getAlignment());
			
			String type = cf.getFieldType();
			lcf.setType(type);
			switch(type) {
		    	case "SINGLELINETEXTCUSTOMFIELD":
		    	case "NUMERICCUSTOMFIELD":
		    	case "DATETIMECUSTOMFIELD":
		    	case "SSNCUSTOMFIELD":
		    	case "MULTIPLELINETEXTCUSTOMFIELD":
		    		for(CustomFieldValue cfv: learnerValues){
		    			if(cfv.getCustomField().getId() == cf.getId()){
		    				lcf.setText(cfv.getValue());
		    				break;
		    			}
		    		}
		    	break;
		    	case "MULTISELECTCUSTOMFIELD":
		    		lcf.setCheckBox(cf.isCheckBoxTf());
		    		List<SingleSelectCustomFieldOption> selectedOptions = null;
		    		List<FieldOption> learnerFieldOptions = new ArrayList<FieldOption>();
		    		for(SingleSelectCustomFieldOption opt: fieldOptions){
		    			FieldOption option = new FieldOption();
		    			option.setValue(opt.getId().toString());
		    			option.setText(opt.getValue());
		    			option.setLabel(opt.getLabel());
		    			option.setDisplayOrder(opt.getDisplayOrder());
		    			for(CustomFieldValue cfv: learnerValues){
			    			if(cfv.getCustomField().getId() == cf.getId()){
			    				selectedOptions = cfv.getSingleSelectCustomFieldOptions();
			    				for(SingleSelectCustomFieldOption scf: selectedOptions){
			    					if(scf.getId() == opt.getId()){
			    						option.setSelected(true);
			    					}
			    				}
			    			}
			    		}
		    			learnerFieldOptions.add(option);
		    			
		    		}
		    		lcf.setOptions(learnerFieldOptions);
		    	break;
		    	case "SINGLESELECTCUSTOMFIELD":
		    		learnerFieldOptions = new ArrayList<FieldOption>();
		    		for(SingleSelectCustomFieldOption opt: fieldOptions){
		    			FieldOption option = new FieldOption();
		    			option.setValue(opt.getId().toString());
		    			option.setText(opt.getValue());
		    			option.setLabel(opt.getLabel());
		    			option.setDisplayOrder(opt.getDisplayOrder());
		    			for(CustomFieldValue cfv: learnerValues){
			    			if(cfv.getCustomField().getId() == cf.getId()){
			    				//selectedOptions = cfv.getSingleSelectCustomFieldOptions();
		    					if(cfv.getValue().equalsIgnoreCase(opt.getValue())){
			    						option.setSelected(true);
			    						lcf.setText(cfv.getValue());
			    				}
			    			}
			    		}
		    			learnerFieldOptions.add(option);
		    		}
		    		lcf.setOptions(learnerFieldOptions);
		    	break;
		    	
			} //end of switch
			learnerCustomFields.add(lcf);
		}
		}catch(Exception e){
			logger.info("Error::" + e.getMessage());
		}
		return learnerCustomFields;
	}
	
	public List<LearnerCreditReportingField> getCreditReportingFields(LearnerProfile learnerProfile){
		
		logger.info("Call for Credit Reporting Fields " + getClass().getName());
		List<LearnerCreditReportingField> learnerCreditReportingFields = new ArrayList<LearnerCreditReportingField>();
		try{
		Long learnerId = learnerProfile.getLearner().getId();
		
		
		List<CreditReportingField> creditReportingFields = creditReportingFieldRepository.findAllByLearnerId(learnerId);
		List<CreditReportingFieldValue> creditReportingFieldValues = creditReportingFieldValueRepository.findByLearnerProfile_Id(learnerProfile.getId());
		
		for(CreditReportingField crf: creditReportingFields){
			
			LearnerCreditReportingField lcf = new LearnerCreditReportingField();
			lcf.setValue(crf.getId().toString());
			lcf.setRequired(crf.isFieldRequired());
			lcf.setLabel(crf.getFieldLabel());
			lcf.setEncrypted(crf.isFieldEncrypted());
			lcf.setAlignment(crf.getAlignment());
			
			String type = crf.getFieldType();
			lcf.setType(type);
			
			List<CreditReportingFieldValueChoice> fieldChoices = crf.getCreditReportingFieldValueChoices();
			
			switch(type) {
			case "SINGLELINETEXTCREDITREPORTINGFIELD":
			case "DATETIMECREDITREPORTINGFIELD":
			case "NUMERICCREDITREPORTINGFIELD":
			case "TELEPHONENUMBERCREDITREPORTINGFIELD":
			case "SSNCREDITREPORTINGFIELD":
				for(CreditReportingFieldValue crfv: creditReportingFieldValues){
	    			if(crfv.getCreditReportingField().getId() == crf.getId()){
	    				lcf.setText(crfv.getValue());
	    				break;
	    			}
	    		}				
			break;
			case "MULTISELECTCREDITREPORTINGFIELD":
				List<CreditReportingFieldValueChoice> creditReportingFieldValueChoice = null;
				List<FieldOption> learnerFieldOptions = new ArrayList<FieldOption>();
				
				for(CreditReportingFieldValueChoice crfvc: fieldChoices){
					FieldOption option = new FieldOption();
	    			option.setValue(crfvc.getId().toString());
	    			option.setText(crfvc.getValue());
	    			option.setLabel(crfvc.getLabel());
	    			option.setDisplayOrder(crfvc.getDisplayOrder());
	    			
	    			for(CreditReportingFieldValue cfv: creditReportingFieldValues){
		    			if(cfv.getCreditReportingField().getId() == crf.getId()){
		    				creditReportingFieldValueChoice = cfv.getCreditReportingFieldValueChoices();
		    				for(CreditReportingFieldValueChoice scf: creditReportingFieldValueChoice){
		    					if(scf.getId() == crfvc.getId()){
		    						option.setSelected(true);
		    					}
		    				}
		    			}
		    		}
	    			learnerFieldOptions.add(option);
				}
				lcf.setOptions(learnerFieldOptions);
			break;
			case "SINGLESELECTCREDITREPORTINGFIELD":
				learnerFieldOptions = new ArrayList<FieldOption>();
				
				for(CreditReportingFieldValueChoice crfvc: fieldChoices){
					FieldOption option = new FieldOption();
	    			option.setValue(crfvc.getId().toString());
	    			option.setText(crfvc.getValue());
	    			option.setLabel(crfvc.getLabel());
	    			option.setDisplayOrder(crfvc.getDisplayOrder());
	    			
	    			for(CreditReportingFieldValue cfv: creditReportingFieldValues){
		    			if(cfv.getCreditReportingField().getId() == crf.getId()){
		    				if(cfv.getValue().equalsIgnoreCase(crfvc.getValue())){
	    						option.setSelected(true);
	    						lcf.setText(cfv.getValue());
		    				}
		    			}
		    		}
	    			learnerFieldOptions.add(option);
				}
				lcf.setOptions(learnerFieldOptions);
			break;
			}
			learnerCreditReportingFields.add(lcf);
		}
		}catch(Exception e){
			logger.info("Error::" + e.getMessage());
		}
		return learnerCreditReportingFields;
	}
}
