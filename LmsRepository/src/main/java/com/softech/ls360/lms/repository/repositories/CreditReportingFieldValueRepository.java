package com.softech.ls360.lms.repository.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.CreditReportingFieldValue;

public interface CreditReportingFieldValueRepository extends CrudRepository<CreditReportingFieldValue, Long> {

	@EntityGraph(value = "CreditReportingFieldValue.WithCreditReportingFieldsAndCreditReportingFieldValueChoice", type = EntityGraphType.LOAD)
	List<CreditReportingFieldValue> findByLearnerProfile_IdAndCreditReportingField_IdIn(Long learnerProfileId, Collection<Long> creditReportingFieldIds);
	
	@EntityGraph(value = "CreditReportingFieldValue.WithCreditReportingFieldsAndCreditReportingFieldValueChoice", type = EntityGraphType.LOAD)
	List<CreditReportingFieldValue> findByLearnerProfile_Id(Long learnerProfileId);
	
}
