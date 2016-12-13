package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.CreditReportingFieldValueChoice;
import com.softech.ls360.lms.repository.projection.credit.reporting.filed.CreditReportingFieldValueChoiceProjection;

public interface CreditReportingFieldValueChoiceRepository extends CrudRepository<CreditReportingFieldValueChoice, Long> {

	List<CreditReportingFieldValueChoiceProjection> findByCreditReportingFieldId(Long creditReportingFieldId);
	
}
