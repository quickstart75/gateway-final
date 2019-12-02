package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.InformalLearning;
import com.softech.ls360.lms.repository.entities.InformalLearningActivity;

public interface InformalLearningRepository extends CrudRepository<InformalLearning, Long>{
	
	@Query(value="select  ActivityTypeId, " +
			"SUM(ISNULL(TimeSpentInSeconds, 0)) TimeSpentInSeconds,  count(id)  " +
			"From LearnerInformalActivity ia " +
			"where ia.VU360Username=:username " +
			"group by ActivityTypeId  ", 
	nativeQuery=true)
	List<Object[]> getActivityTimeSpent(@Param("username") String username);

	@Query(value="select  ia.TopicId, " + 
			"SUM(ISNULL(ia.TimeSpentInSeconds, 0)) TimeSpentInSeconds " + 
			"From LearnerInformalActivity ia " +
			"where ia.VU360Username=:username " +
			"group by ia.TopicId ", 
	nativeQuery=true)
	List<Object[]> getActivityTimeSpentByTopic(@Param("username") String username);
	
	
	
	InformalLearning findTopByItemGuidAndUserNameAndStoreId(String itemGuid, String username, String storeId);
	
	List<InformalLearning> findByUserNameAndTypeId(String username, Integer typeId);
	
	@Query(value = "select count(*), item_guid from LearnerInformalActivity where storeId=:id and typeId=2  group by item_guid  ", nativeQuery = true)
	List<Object[]> getInformalLearningActivityCount( @Param("id") int id);
	
	List<InformalLearning> findByItemGuidAndStoreId(String guid, String storeId);
	
	//@Query(value = " select sum(TIMESPENTINSECONDS) from InformalLearning where userName=:username", nativeQuery = true)
	//Integer getGetTimeInSecondsByUserId(@Param("username") String username);
	
	@Query(value = " select isNull(sum(TIMESPENTINSECONDS), 0) from LearnerInformalActivity  where VU360Username= :username", nativeQuery = true)
	Integer getGetTimeInSecondsByUsername(@Param("username") String username);
}