package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Set;

import com.softech.ls360.lms.repository.entities.CreditReportingField;

public interface CreditReportingFieldService {

	Set<CreditReportingField> getCreditReportingFields(Long learnerId) throws Exception;
	List<Long> getCreditReportingFieldIds(Set<CreditReportingField> creditReportingFields) throws Exception;
	
}
