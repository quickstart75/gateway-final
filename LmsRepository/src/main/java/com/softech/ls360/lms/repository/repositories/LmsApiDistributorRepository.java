package com.softech.ls360.lms.repository.repositories;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.LmsApiDistributor;

public interface LmsApiDistributorRepository extends CrudRepository<LmsApiDistributor, Long> {

	LmsApiDistributor findByUserNameAndPasswordAndApiKey(String userName, String password, String apiKey);
	LmsApiDistributor findByUserNameAndPassword(String userName, String password);
	LmsApiDistributor findByUserName(String userName);
	
}
