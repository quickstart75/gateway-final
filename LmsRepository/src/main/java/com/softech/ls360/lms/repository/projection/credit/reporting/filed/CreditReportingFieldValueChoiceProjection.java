package com.softech.ls360.lms.repository.projection.credit.reporting.filed;

import org.springframework.beans.factory.annotation.Value;

public interface CreditReportingFieldValueChoiceProjection {

	Long getId();
	String getLabel();
	Integer getDisplayOrder();
	String getValue();
	
	@Value("#{target.creditReportingField.id}")
	Long getCreditReportingFieldId();
	
	
}
