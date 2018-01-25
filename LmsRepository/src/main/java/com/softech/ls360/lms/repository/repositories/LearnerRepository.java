package com.softech.ls360.lms.repository.repositories;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.Learner;

public interface LearnerRepository extends CrudRepository<Learner, Long> {

	Learner findByVu360UserUsername(String userName);
	Long countByCustomerId(Long customerId);
}
