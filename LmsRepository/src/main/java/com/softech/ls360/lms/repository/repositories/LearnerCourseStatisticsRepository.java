package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;

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
    
    
	@Query(value="select isnull(sum(totalTimeInSeconds), 0) From learnercoursestatistics lcs " +
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
    
    List<LearnerCourseStatistics> findAllByLearnerEnrollment_IdIn(List<Long> learnerEnrollmentIdList);
    
    @Query(value=" SELECT  sum(DATEDIFF(SECOND, ls.STARTTIME, ls.ENDTIME)) " +
			" FROM vu360user u " +
			" inner join Learner l on l.vu360user_id=u.id " +
			" inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id " +
			" inner join LEARNINGSESSION ls on ls.ENROLLMENT_id = le.id " +
			" where u.username=:username " + 
			" and ls.STARTTIME>=DATEADD(DD, DATEDIFF(DY, 0, GETDATE()), -6) " ,
	nativeQuery=true)
	Long getAverageViewTimeByWeekByUserName(@Param("username") String username);
    
    /*
    @Modifying
	@Transactional
	@Query(value="update LearnerCourseStatistics set COMPLETIONDATE=:completionDate,COMPLETED = 1,STATUS = 'completed' where LEARNERENROLLMENT_ID in :enrollmentIds", nativeQuery = true )
	void markCompletion(@Param("enrollmentIds") List<Long> enrollmentIds,@Param("completionDate") String completionDate);
    */
    
    @Modifying
   	@Transactional
   	@Query(value="update LearnerCourseStatistics set COMPLETIONDATE=:completionDate, COMPLETED = 1, STATUS = 'completed', TOTALTIMEINSECONDS=:totalTimeSpent where LEARNERENROLLMENT_ID = :enrollmentIds", nativeQuery = true )
   	void markCompletionAndTotalTimeSpent(@Param("enrollmentIds") Long enrollmentIds, @Param("completionDate") String completionDate, @Param("totalTimeSpent") Long totalTimeSpent);

    @Modifying
   	@Transactional
   	@Query(value="update LearnerCourseStatistics set COMPLETIONDATE=:completionDate, COMPLETED = 1, STATUS = 'completed' where LEARNERENROLLMENT_ID = :enrollmentIds", nativeQuery = true )
   	void markCompletion(@Param("enrollmentIds") Long enrollmentIds, @Param("completionDate") String completionDate);

    @Modifying
   	@Transactional
   	@Query(value="update LearnerCourseStatistics set COMPLETIONDATE=:completionDate, COMPLETED =:completed, " + 
   					" STATUS =:status, TOTALTIMEINSECONDS=:totalTimeSpent,HIGHESTPOSTTESTSCORE=:highestPostTestScore, " +
   					" PERCENTCOMPLETE=:percentComplete,LASTACCESSDATE=:lastAccessDate " + 
   					 " where LEARNERENROLLMENT_ID = :enrollmentIds", nativeQuery = true )
   	void statsUpdate(@Param("enrollmentIds") Long enrollmentIds,
   					@Param("completionDate") String completionDate, @Param("completed") String completed, 
   					@Param("totalTimeSpent") Long totalTimeSpent,@Param("highestPostTestScore") Double highestPostTestScore,
   					@Param("percentComplete") Long percentComplete , @Param("status") String status,
   					@Param("lastAccessDate") String lastAccessDate );

    
//    COMPLETED	COMPLETIONDATE	LASTACCESSDATE	PERCENTCOMPLETE	STATUS	TOTALTIMEINSECONDS	LEARNERENROLLMENT_ID	HIGHESTPOSTTESTSCORE
    
    @Query(value=" select learnergroup_id,	name,	y,	m,	isnull(sum(second),0) second from ( " +
			" SELECT  lgm.learnergroup_id, lg.name, YEAR(completionDate) AS y, MONTH(completionDate) AS m, sum(TOTALTIMEINSECONDS) as second " +
			" FROM Learner l " +
			" inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id  " +
			" inner join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id " +
			" inner join course c on c.id=le.course_id  " + 
			" left outer join LEARNER_LEARNERGROUP lgm on  lgm.learner_id=l.id  " +
			" left outer join learnergroup lg on lg.customer_id = l.customer_id and lgm.learnergroup_id = lg.id   " +
			" where l.customer_id=:customerId and c.coursetype='Classroom Course'  " +
			" and lcs.completionDate>=:startDate and lcs.completionDate<=:endDate  " +
			" GROUP BY YEAR(completionDate),MONTH(completionDate),lgm.learnergroup_id, lg.name  " +
			"   " +
			" union all  " +
			"   " +
			" SELECT lgm.learnergroup_id, lg.name, YEAR(ls.starttime) AS y, MONTH(ls.starttime) AS m,  sum(DATEDIFF(second,starttime,endtime)) as second  " +
			" FROM  Learner l   " +
			" inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id   " +
			" inner join LEARNINGSESSION ls on ls.ENROLLMENT_id = le.id and le.LEARNER_ID = l.ID  " +
			" inner join course crs on crs.id=le.course_id  " +
			" left outer join LEARNER_LEARNERGROUP lgm on  lgm.learner_id=l.id   " +
			" left outer join learnergroup lg on lg.customer_id = l.customer_id and lgm.learnergroup_id = lg.id  " +
			" where  ls.starttime >= :startDate and ls.starttime<=:endDate  " +
			" and crs.coursetype!='Classroom Course' "+ 
			" and l.customer_id=:customerId  " +
			" GROUP BY YEAR(ls.starttime), MONTH(ls.starttime), lgm.learnergroup_id,  lg.name  " +
			" ) CourseStatisticsByMonth  " +
			" group by learnergroup_id,	name,	y,	m "
			+ " order by y desc, m desc, name ",
	nativeQuery=true)
    List<Object[]> getLearnerGroupCourseStatisticsByMonth(@Param("customerId") Long customerId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    
    
    
    @Query(value=
 			" select y,	m,	isnull(sum(second),0) second from ( " +
 			"  " +
 			" SELECT  YEAR(completionDate) AS y, MONTH(completionDate) AS m, sum(TOTALTIMEINSECONDS) as second " +
 			" FROM Learner l " +
 			" inner join vu360user u on u.id = l.vu360user_id " +
 			" inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id  " +
 			" inner join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id " +
 			" inner join course c on c.id=le.course_id   " +
 			" where u.username =:username and c.coursetype='Classroom Course' " +
 			" and lcs.completionDate>=:startDate and lcs.completionDate<=:endDate  " +
 			" GROUP BY YEAR(completionDate),MONTH(completionDate) " +
 			"  " +
 			" union all  " +
 			"  " +
 			" SELECT  YEAR(ls.starttime) AS y, MONTH(ls.starttime) AS m,  sum(DATEDIFF(second,starttime,endtime)) as second  " +
 			" FROM  Learner l  " +
 			" inner join vu360user u on u.id = l.vu360user_id " +
 			" inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id  " +
 			" inner join LEARNINGSESSION ls on ls.ENROLLMENT_id = le.id and le.LEARNER_ID = l.ID  " +
 			" inner join course crs on crs.id=le.course_id  " +
 			" where  ls.starttime >= :startDate and ls.starttime<=:endDate " +
 			" and crs.coursetype!='Classroom Course' " +
 			" and u.username =:username " +
 			" GROUP BY YEAR(ls.starttime), MONTH(ls.starttime) " +
 			"  " +
 			" ) CourseStatisticsByMonth  " +
 			" group by	y,	m  " +
 			" order by y desc, m desc ",
 	nativeQuery=true)
     List<Object[]> learnerTimespentByMonth(@Param("username") String username, @Param("startDate") String startDate, @Param("endDate") String endDate);

     
     @Query(value=
  			" SELECT YEAR(CREATEDDATE) AS Y, MONTH(CREATEDDATE) AS M, SUM(TIMESPENTINSECONDS) AS SECOND " +
  			" FROM LEARNERINFORMALACTIVITY " +
  			" WHERE CREATEDDATE>=:startDate AND CREATEDDATE<=:endDate " +
  			" and VU360Username=:username " +
  			" GROUP BY YEAR(CREATEDDATE),MONTH(CREATEDDATE) " +
  			" order by YEAR(CREATEDDATE) , MONTH(CREATEDDATE) " ,
  	nativeQuery=true)
      List<Object[]> learnerInformalLearningTimespentByMonth(@Param("username") String username, @Param("startDate") String startDate, @Param("endDate") String endDate);

      
    @Query(value=" select " +
    " isnull(lg.id, 0) as learnergroupid, lg.name as learnergroupname , vu.firstName as firstname, vu.lastName as lastname, vu.username as username, " +
    " (select isnull(sum(TOTALTIMEINSECONDS),0)  from LEARNERCOURSESTATISTICS lcs inner join LEARNERENROLLMENT le on le.id = lcs.LEARNERENROLLMENT_ID and le.learner_id=l.id) as timespent " +
    " from Learner l  " +
    " inner join VU360User vu on vu.id = l.vu360User_id " +
    " inner join Customer c on c.id = l.customer_id " +
    " left join Learner_Learnergroup lp on l.id = lp.learner_id  " +
    " left join LearnerGroup lg on lg.id = lp.learnerGroup_id  " +
    " where c.id = :customerId  " 
   ,nativeQuery=true)
    List<Object[]> getAggregateUsersTimespentByLearnerGroup(@Param("customerId") Long customerId);
    
    
    @Query(value=" select top 5 " +
    	    " cr.guid, cr.name,  count(le.course_id), cr.id " +
    	    " from Learner l " +
    	    " inner join learnerenrollment le on le.learner_id = l.id  " +
    	    " inner join learnercoursestatistics lcs on lcs.LEARNERENROLLMENT_ID =le.id  " +
    	    " inner join course cr on le.course_id = cr.id " +
    	    " inner join LEARNER_LEARNERGROUP lgm on  lgm.learner_id=l.id  " +
    	    " where lgm.LearnerGroup_id=:learnerGroupId and lcs.status!='notstarted' " +
    	    " group by  cr.guid, cr.id, cr.name  " +
    	    " order by count(le.course_id) desc  " 
    	   ,nativeQuery=true)
    List<Object[]> getPopularCoursesByLearnerGroup(@Param("learnerGroupId") Long learnerGroupId);
    
    
    @Query(value=" select top 5 " +
    	    " cr.guid, cr.name,  count(le.course_id), cr.id " +
    	    " from Learner l " +
    	    " inner join learnerenrollment le on le.learner_id = l.id  " +
    	    " inner join learnercoursestatistics lcs on lcs.LEARNERENROLLMENT_ID =le.id  " +
    	    " inner join course cr on le.course_id = cr.id " +
    	    " inner join Customer c on c.id = l.customer_id " +
    	    " where c.id=:customerId and lcs.status!='notstarted' " +
    	    " group by  cr.id, cr.guid, cr.name  " +
    	    " order by count(le.course_id) desc  " 
    	   ,nativeQuery=true)
    List<Object[]> getPopularCoursesByCustomer(@Param("customerId") Long customerId);
    
    
    
    @Query(value=" SELECT u.username, u.firstname, u.lastname, c.name as courseName, c.guid as courseGuid, isnull(sum(totalTimeInSeconds), 0) " +
    	    " FROM vu360user u " +
    	    " inner join Learner l on l.vu360user_id=u.id " +
    	    " inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id  " +
    	    " inner join learnercoursestatistics lcs on lcs.LEARNERENROLLMENT_ID =le.id " +
    	    " inner join course c on c.id=le.course_id  " +
    	    " inner join LEARNER_LEARNERGROUP lgm on  lgm.learner_id=l.id  " +
    	    " where lgm.LEARNERGROUP_id = :learnerGroupId  and c.id in (:courseIds)" +
    	    " group by  u.username, u.firstname, u.lastname, c.name , c.guid "
    	   ,nativeQuery=true)
    List<Object[]> getUsersTimespentPerCourseByLearnerGroup(@Param("learnerGroupId") Long learnerGroupId, @Param("courseIds") List<Long> courseIds);
    
    
    
    @Query(value=" SELECT u.username, u.firstname, u.lastname, c.name as courseName, c.guid as courseGuid, isnull(sum(totalTimeInSeconds), 0) " +
    	    " FROM vu360user u " +
    	    " inner join Learner l on l.vu360user_id=u.id " +
    	    " inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id  " +
    	    " inner join learnercoursestatistics lcs on lcs.LEARNERENROLLMENT_ID =le.id " +
    	    " inner join course c on c.id=le.course_id  " +
    	    " inner join Customer cus on cus.id = l.customer_id  " +
    	    " where cus.id = :customerId  and c.id in (:courseIds)" +
    	    " group by  u.username, u.firstname, u.lastname, c.name , c.guid "
    	   ,nativeQuery=true)
    List<Object[]> getUsersTimespentPerCourseByCustomer(@Param("customerId") Long customerId, @Param("courseIds") List<Long> courseIds);
    
    
    @Query(value=" SELECT isnull(sum(lcs.totalTimeInSeconds), 0) " +
    	    " FROM LEARNERENROLLMENT le " +
    	    " inner join learnercoursestatistics lcs on lcs.LEARNERENROLLMENT_ID =le.id " +
    	    " inner join course c on c.id=le.course_id  " +
    	    " where le.ENROLLMENTSTATUS = 'Active' and le.learner_id = :learnerId  and c.guid in (:guids) " 
    	   ,nativeQuery=true)
    Long getLearnerTimespentByGuids(@Param("learnerId") Long learnerId, @Param("guids") List<String> guids);

    
    @Query(value=" SELECT  c.guid, lcs.status, le.orderstatus " +
    	    " FROM vu360user u  " +
    	    " inner join Learner l on l.vu360user_id=u.id " +
    	    " inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id   " +
    	    " inner join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id   " +
    	    " inner join course c on c.id=le.course_id  " +
    	    " where le.ENROLLMENTSTATUS='Active' and u.username=:username " 
    	   ,nativeQuery=true)
    List<Object[]> getEnrolledCoursesInfoByUsername(@Param("username") String username);
    
    @EntityGraph(value = "LearnerCourseStatistics.LearnerEnrollments", type = EntityGraphType.LOAD)
    List<LearnerCourseStatistics> findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_Course_courseGuidAndLearnerEnrollment_enrollmentStatus(String userName, String guid, String enrollmentStatus);

    @Query(value="Select top 1 lcs.* from vu360user u " + 
    		"	Inner Join Learner l on l.vu360user_id=u.id " + 
    		"	Inner Join LEARNERENROLLMENT le on le.LEARNER_ID=l.id " + 
    		"	Inner Join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id " + 
    		"	Inner Join COURSE c on c.id=le.course_id " + 
    		"	Where u.username= :username AND c.THIRDPARTYGUID = :courseGuid order by lcs.id desc", nativeQuery = true)
    LearnerCourseStatistics getLearnerCourseStatisticsByUsernameAndEdxCourse(@Param("username") String username, @Param("courseGuid") String courseGuid);
    
    LearnerCourseStatistics findByLearnerEnrollment(LearnerEnrollment learnerEnrollmentId);
    
    @Query(value = "SELECT  c.name, c.guid, c.COURSETYPE,   c.BUSINESSUNIT_NAME, lcs.status, le.orderstatus , lcs.TOTALTIMEINSECONDS FROM vu360user u  \n" + 
    		"inner join Learner l on l.vu360user_id=u.id \n" + 
    		"inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id   \n" + 
    		"inner join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id  \n" + 
    		"inner join course c on c.id=le.course_id where  u.username LIKE  CONCAT('%',:username,'%') and le.ENROLLMENTSTATUS='Active'", nativeQuery = true)
    List<Object[]> getLearnerCourseStatisticsByUsername(@Param("username") String username);

    @Query(value = "SELECT  c.name, c.guid, c.COURSETYPE,   c.BUSINESSUNIT_NAME, lcs.status, le.orderstatus , lcs.TOTALTIMEINSECONDS FROM vu360user u  \n" + 
    		"inner join Learner l on l.vu360user_id=u.id \n" + 
    		"inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id   \n" + 
    		"inner join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id  \n" + 
    		"inner join course c on c.id=le.course_id where  u.username LIKE  CONCAT('%',:username,'%') and le.ENROLLMENTSTATUS='Active' AND lcs.status='completed' ", nativeQuery = true)
    List<Object[]> getLearnerCourseStatisticsByUsernameAndComplete(@Param("username") String username);
    
}
