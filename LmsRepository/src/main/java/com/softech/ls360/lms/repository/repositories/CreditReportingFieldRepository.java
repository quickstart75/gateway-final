package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.CreditReportingField;
import com.softech.ls360.lms.repository.entities.RegulatorCategory;

public interface CreditReportingFieldRepository extends CrudRepository<CreditReportingField, Long> {
	
	@Query(value="SELECT DISTINCT CF.* "
			+" FROM COURSEAPPROVAL CA "              
			+" INNER JOIN REGULATORYAPPROVAL RA on CA.ID=RA.ID AND RA.DELETED != 1 "              
			+" INNER JOIN REGULATORCATEGORY RC on RA.REGULATORCATEGORY_ID = RC.ID "
			+" INNER JOIN REGULATORCATEGORY_CREDITREPORTINGFIELD RCCF ON RCCF.REGULATORCATEGORY_ID = RC.ID "
			+" INNER JOIN CREDITREPORTINGFIELD CF ON CF.ID = RCCF.CREDITREPORTINGFIELD_ID "
			+" INNER JOIN LEARNINGSESSION LS ON LS.COURSEAPPROVALID = CA.ID "
			+" INNER JOIN LEARNERENROLLMENT LE ON LE.ID = LS.ENROLLMENT_ID "
			+" where LE.LEARNER_ID = :learnerId", nativeQuery = true)
	List<CreditReportingField> findAllByLearnerId(@Param("learnerId") Long learnerId);

}
