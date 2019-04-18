package com.softech.ls360.lms.repository.repositories;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.InformalLearningActivity;

public interface InformalLearningActivityRepository extends CrudRepository<InformalLearningActivity, Long>{
	
	InformalLearningActivity findByItemGuidAndVu360userIdAndStoreId(String itemGuid, Long userId, String storeId);
}
