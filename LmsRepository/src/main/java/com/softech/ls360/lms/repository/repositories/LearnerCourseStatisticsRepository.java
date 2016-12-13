package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;

public interface LearnerCourseStatisticsRepository extends CrudRepository<LearnerCourseStatistics, Long> {

	
    int countByStatusInAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(List<String> status, String userName, String enrollmentStatus);
    
    int countByStatusInAndCompletedAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(List<String> status, boolean completed, String userName, String enrollmentStatus);
    
    int countByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(String userName, String enrollmentStatus);
    
    int countByStatusAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(String status, String userName, String enrollmentStatus);
    
    //@EntityGraph(value = "LearnerCourseStatistics.LearnerEnrollments", type = EntityGraphType.LOAD)
    //List<LearnerCourseStatistics> findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(String userName, String enrollmentStatus);
    
    @EntityGraph(value = "LearnerCourseStatistics.LearnerEnrollments", type = EntityGraphType.LOAD)
    Page<LearnerCourseStatistics> findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(String userName, String enrollmentStatus, Pageable pageable);
    
    @EntityGraph(value = "LearnerCourseStatistics.LearnerEnrollments", type = EntityGraphType.LOAD)
    Page<LearnerCourseStatistics> findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatusAndLearnerEnrollment_course_nameLike(String userName, String enrollmentStatus, String courseName, Pageable pageable);
    
    @EntityGraph(value = "LearnerCourseStatistics.LearnerEnrollments", type = EntityGraphType.LOAD)
    Page<LearnerCourseStatistics> findAllByStatusInAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(List<String> courseStatus, String userName, String enrollmentStatus, Pageable pageable);
    
    @EntityGraph(value = "LearnerCourseStatistics.LearnerEnrollments", type = EntityGraphType.LOAD)
    Page<LearnerCourseStatistics> findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatusAndLearnerEnrollment_subscriptionNotNull(String userName, String enrollmentStatus, Pageable pageable);

    List<LearnerCourseStatistics> findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollmentIdIn(String userName, List<Long> learnerEnrollmentIdList);
}
