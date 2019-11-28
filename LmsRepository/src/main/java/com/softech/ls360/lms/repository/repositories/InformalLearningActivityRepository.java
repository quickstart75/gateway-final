package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.InformalLearningActivity;

// old one
public interface InformalLearningActivityRepository extends CrudRepository<InformalLearningActivity, Long>{
	/*
	InformalLearningActivity findByItemGuidAndVu360userIdAndStoreId(String itemGuid, Long userId, String storeId);
	
	List<InformalLearningActivity> findByVu360userId(Long userId);
	
	@Query(value = "select count(*), item_guid from InformalLearningActivity where storeId= :id  group by item_guid ", nativeQuery = true)
	List<Object[]> getInformalLearningActivityCount( @Param("id") int id);
	
	List<InformalLearningActivity> findByItemGuidAndStoreId(String guid, String storeId);
	
	@Query(value = "select sum(TIMESPENTINSECONDS) from InformalLearningActivity where vu360user_id=:userId", nativeQuery = true)
	Integer getGetTimeInSecondsByUserId(@Param("userId") long userId);
	
	@Query(value = "select sum(TIMESPENTINSECONDS) from LearnerInformalActivity  where vu360username= :username", nativeQuery = true)
	Integer getGetTimeInSecondsByUsername(@Param("username") String username);
	*/
}
