package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.LearningSession;

public interface LearningSessionRepository extends CrudRepository<LearningSession, Long> {

	//Optional<List<LearningSessionCourseApprovals>> findDistinctCourseApprovalsByLearnerEnrollment_Learner_IdAndCourseApproval_IdGreaterThanAndCourse_CourseGuidIn(Long learnerId, Long courseApprovalId, Collection<String> courseGuids);
	
	@Query(value = "Select top 1 ls.* from vu360user u" + 
			"	Inner Join Learner l on l.vu360user_id=u.id" + 
			"	Inner Join LEARNERENROLLMENT le on le.LEARNER_ID=l.id" + 
			"	inner join learningsession ls on ls.enrollment_id=le.id" +  
			"	Inner Join COURSE c on c.id=le.course_id" + 
			"	Where u.username=:username AND " + 
			"	c.THIRDPARTYGUID= :courseGuid order by id desc", nativeQuery = true)
	LearningSession getLatestSessionByUsernameAndCourseKey(@Param("username")String username, @Param("courseGuid")String courseGuid);
	
	@Query(value = "Select SUM (DATEDIFF(SECOND,ls.STARTTIME ,ls.endtime )) from vu360user u " + 
				"Inner Join Learner l on l.vu360user_id=u.id " + 
				"Inner Join LEARNERENROLLMENT le on le.LEARNER_ID=l.id " + 
				"inner join learningsession ls on ls.enrollment_id=le.id " + 
				"Inner Join COURSE c on c.id=le.course_id " + 
				"Where u.username= :username AND c.THIRDPARTYGUID = :courseGuid", nativeQuery = true)
	Integer getTotalSecondFromSessions(@Param("username")String username, @Param("courseGuid")String courseGuid);
	
	
}
