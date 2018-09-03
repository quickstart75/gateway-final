package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.InformalLearning;

public interface InformalLearningRepository extends CrudRepository<InformalLearning, Long>{
	
	@Query(value="select  ActivityTypeId, " +
			"SUM(ISNULL(TimeSpentInSeconds, 0)) TimeSpentInSeconds  " +
			"From LearnerInformalActivity ia " +
			"where ia.VU360Username=:username " +
			"group by ActivityTypeId  ", 
	nativeQuery=true)
	List<Object[]> getActivityTimeSpent(@Param("username") String username);

	@Query(value="select  ia.TopicId, " + 
			"SUM(ISNULL(ia.TimeSpentInSeconds, 0)) TimeSpentInSeconds,ISNULL(mc.CATEGORY_NAME,'') categoryname " + 
			"From LearnerInformalActivity ia " +
			"left outer join MAGENTO_CATEGORY mc on mc.CATEGORY_ID = ia.TopicId " +
			"where ia.VU360Username=:username " +
			"group by ia.TopicId,mc.CATEGORY_NAME ", 
	nativeQuery=true)
	List<Object[]> getActivityTimeSpentByTopic(@Param("username") String username);
}