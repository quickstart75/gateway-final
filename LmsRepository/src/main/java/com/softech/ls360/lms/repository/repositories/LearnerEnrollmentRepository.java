package com.softech.ls360.lms.repository.repositories;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.LearnerEnrollment;
import com.softech.ls360.lms.repository.projection.learner.enrollment.LearnerEnrollmentCourses;

public interface LearnerEnrollmentRepository extends CrudRepository<LearnerEnrollment, Long>, LearnerEnrollmentRepositoryCustom {
	
	@EntityGraph(value = "LearnerEnrollment.Classroom", type = EntityGraphType.LOAD)
	LearnerEnrollment findEnrolledClassroomById(Long Id);
	
	int countByEnrollmentStatusAndLearner_IdAndSubscription_SubscriptionCodeIn(String enrollmentStatus, Long learnerId, List<String> subscriptionIds);
	
	@EntityGraph(value = "LearnerEnrollment.Course", type = EntityGraphType.LOAD)
	List<LearnerEnrollment> findAllByEnrollmentStatusAndLearner_Vu360User_UsernameAndSubscription_SubscriptionCodeIn(String enrollmentStatus, String userName, List<String> subscriptionIds);
	
	Optional<List<LearnerEnrollmentCourses>> findDistinctCoursesByLearner_IdAndEnrollmentStatusNotInAndStartDateLessThanEqual(Long learnerId, Collection<String> enrollmentStatus, LocalDateTime dateTime);
	
	@Modifying
	@Transactional
   	@Query(value="update LEARNERENROLLMENT set ENROLLMENTSTATUS=:status  where ID in :ids", nativeQuery = true )
   	void updateEnrollmentStatus(@Param("status") String status, @Param("ids") List<Long> ids);

	
}
