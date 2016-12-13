package com.softech.ls360.api.gateway.service.model.learner.profile;

import java.util.List;

import com.softech.ls360.lms.repository.entities.CustomFieldValue;
import com.softech.ls360.lms.repository.projection.custom.reporting.field.SingleSelectCustomFieldOptionProjection;

public class LearnerProfileCustomField {

	private CustomFieldValue customFieldValue;
	private List<SingleSelectCustomFieldOptionProjection> multiSelectOptions;

	public LearnerProfileCustomField(CustomFieldValue customFieldValue,
			List<SingleSelectCustomFieldOptionProjection> multiSelectOptions) {
		super();
		this.customFieldValue = customFieldValue;
		this.multiSelectOptions = multiSelectOptions;
	}

	public CustomFieldValue getCustomFieldValue() {
		return customFieldValue;
	}

	public List<SingleSelectCustomFieldOptionProjection> getMultiSelectOptions() {
		return multiSelectOptions;
	}

}
