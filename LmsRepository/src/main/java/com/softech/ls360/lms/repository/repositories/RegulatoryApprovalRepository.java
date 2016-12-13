package com.softech.ls360.lms.repository.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.RegulatoryApproval;

public interface RegulatoryApprovalRepository extends CrudRepository<RegulatoryApproval, Long> {

	@EntityGraph(value = "RegulatoryApproval.WithRegulatorCategoryAndCreditReportingFields", type = EntityGraphType.LOAD)
	List<RegulatoryApproval> findDistinctByIdInAndDeletedFalse(Collection<Long> courseApprovalIds);
	
}
