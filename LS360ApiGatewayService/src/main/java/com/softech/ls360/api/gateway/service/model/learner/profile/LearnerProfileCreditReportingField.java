package com.softech.ls360.api.gateway.service.model.learner.profile;

import java.util.List;

import com.softech.ls360.lms.repository.entities.CreditReportingFieldValue;
import com.softech.ls360.lms.repository.projection.credit.reporting.filed.CreditReportingFieldValueChoiceProjection;

public class LearnerProfileCreditReportingField {

	private CreditReportingFieldValue creditReportingFieldValue;
	private List<CreditReportingFieldValueChoiceProjection> multiSelectOptions;

	public LearnerProfileCreditReportingField(CreditReportingFieldValue creditReportingFieldValue,
			List<CreditReportingFieldValueChoiceProjection> multiSelectOptions) {
		super();
		this.creditReportingFieldValue = creditReportingFieldValue;
		this.multiSelectOptions = multiSelectOptions;
	}

	public CreditReportingFieldValue getCreditReportingFieldValue() {
		return creditReportingFieldValue;
	}

	public List<CreditReportingFieldValueChoiceProjection> getMultiSelectOptions() {
		return multiSelectOptions;
	}

}
