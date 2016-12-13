package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.AssessmentConfiguration;
import com.softech.ls360.lms.repository.entities.AssessmentConfigurationProjection;

public interface AssessmentConfigurationRepository extends CrudRepository<AssessmentConfiguration, Long> {
	
	@Query(value="EXEC UDP_SELECT_ASSESSMENTCONFIGURATION :enrollmentId ", nativeQuery = true)
	List<AssessmentConfigurationProjection> getAssessmentConfigurationByEnrollmentId(@Param("enrollmentId") String enrollmentId);
}
