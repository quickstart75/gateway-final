package com.softech.ls360.lms.repository.projection.custom.reporting.field;

import org.springframework.beans.factory.annotation.Value;

public interface SingleSelectCustomFieldOptionProjection {

    Long getId();
	String getLabel();
	Integer getDisplayOrder();
	String getValue();
	 
	@Value("#{target.customField.id}")
	Long getCustomFieldId();
	
}
