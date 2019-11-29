package com.softech.ls360.lms.repository.repositories;

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
			"	c.guid=(select guid from course where THIRDPARTYGUID =:courseGuid) order by ls.starttime desc", nativeQuery = true)
	LearningSession getLatestSessionByUsernameAndCourseKey(@Param("username")String username, @Param("courseGuid")String courseGuid);
	
	
}
