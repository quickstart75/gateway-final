package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.CustomerEntitlement;
import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;

public interface LearnerCourseStatisticsRepository extends CrudRepository<LearnerCourseStatistics, Long> {

	
    int countByStatusInAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(List<String> status, String userName, String enrollmentStatus);
    
    int countByStatusInAndCompletedAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(List<String> status, boolean completed, String userName, String enrollmentStatus);
    
    int countByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(String userName, String enrollmentStatus);
    
    int countByStatusAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(String status, String userName, String enrollmentStatus);
    
    /*
    int sumOftotalTimeInSecondsAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(String userName, String enrollmentStatus);
   
	@Query("select ce from #{#entityName} ce "
    		+ "join ce.customer c "
    		+ "where c.customerGuid=:customerGuid")
    		*/
    
    
	@Query(value="select sum(totalTimeInSeconds) From learnercoursestatistics lcs " +
				"inner join learnerenrollment le on le.id = lcs.learnerenrollment_id " +
				"inner join learner l on l.id = le.learner_id " +
				"inner join vu360user u on u.id = l.vu360user_id " +
				"where u.username = :userName and le.enrollmentStatus = :enrollmentStatus",
	nativeQuery=true)
	Integer totalTimeSpentOfUserCourse(@Param("userName") String userName,@Param("enrollmentStatus") String enrollmentStatus);
    
	//,c.COURSETYPE,lcs.lastaccessdate,c.guid
	//List<Object[]> 
	@Query(value="select top (?3) le.id From learnercoursestatistics lcs " +
			"inner join learnerenrollment le on le.id = lcs.learnerenrollment_id " +
			"inner join course c on c.id = le.course_id " +
			"inner join learner l on l.id = le.learner_id " +
			"inner join vu360user u on u.id = l.vu360user_id " +
			"where u.username = ?1 and le.enrollmentStatus = ?2 " +
			"and lcs.STATUS != 'notstarted' order by lcs.lastaccessdate desc",
	nativeQuery=true)
	List<Long> getRecentActivityCourse(@Param("userName") String userName,@Param("enrollmentStatus") String enrollmentStatus,@Param("recordCount") Long recordCount);

	
	
	@Query(value="select le.id ,c.COURSETYPE,c.Name From learnercoursestatistics lcs " +
			"inner join learnerenrollment le on le.id = lcs.learnerenrollment_id " +
			"inner join course c on c.id = le.course_id " +
			"inner join learner l on l.id = le.learner_id " +
			"inner join vu360user u on u.id = l.vu360user_id " +
			"where u.username = ?1 and le.enrollmentStatus = ?2 and " +
			"le.id in (?3)",
	nativeQuery=true)
	List<Object[]> getCourseByEnrollmentId(@Param("userName") String userName,@Param("enrollmentStatus") String enrollmentStatus,@Param("enrollmentIds") List<Long> enrollmentIds);

	@Query(value="select  CAST(starttime AS date) SessionDate,ls.enrollment_id, " +
			"ls.coursecode,SUM(ISNULL(DATEDIFF(minute,starttime,endtime), 0)) Minutes  " +
			"From learningsession ls " +
			"where ls.enrollment_id in (?1) " +
			"and (CAST(starttime AS date) >= ?2 and CAST(starttime AS date) <= ?3) " +
			"group by ls.enrollment_id,ls.coursecode,CAST(starttime AS date)  " +
			"order by  ls.enrollment_id,CAST(starttime AS date) asc " ,
	nativeQuery=true)
	List<Object[]> getCourseTimeSpentDateWise(@Param("enrollmentId") List<Long> enrollmentId,@Param("startDate") String startDate,@Param("endDate")  String endDate);

	//List<Object[]> getCourseTimeSpentDateWise(@Param("enrollmentId") String enrollmentId,@Param("startDate") String startDate,@Param("endDate")  String endDate);
	//List<Object[]> getCourseTimeSpentDateWise(@Param("enrollmentId") String enrollmentId,@Param("startDate") String startDate,@Param("endDate")  String endDate);
	
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
