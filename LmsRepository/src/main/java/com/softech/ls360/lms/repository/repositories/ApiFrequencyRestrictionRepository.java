package com.softech.ls360.lms.repository.repositories;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.ApiFrequencyRestriction;

public interface ApiFrequencyRestrictionRepository extends CrudRepository<ApiFrequencyRestriction, Long> {

	ApiFrequencyRestriction findByDistributorIdAndOperation(Long distributorId, String operation);
	
}
