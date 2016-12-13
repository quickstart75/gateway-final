package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.LearnerLicense;

public interface LearnerLicenseRepository extends CrudRepository<LearnerLicense, Long> {

	List<LearnerLicense> findByLearnerId(Long learnerId);
	
}
