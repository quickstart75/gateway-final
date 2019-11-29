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
import com.softech.ls360.lms.repository.entities.VU360User;
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

	@Modifying
	@Transactional
   	@Query(value="update LearnerCourseStatistics set STATUS=:status  where LEARNERENROLLMENT_ID in :ids", nativeQuery = true )
   	void updateLearnerCourseStatisticsStatus(@Param("status") String status, @Param("ids") List<Long> id);

	
	@Modifying
	@Transactional
   	@Query(value="update LEARNERENROLLMENT set MOC_STATUS=:status  where ID in :ids", nativeQuery = true )
   	void updateMocStatus(@Param("status") String status, @Param("ids") List<Long> ids);
	
	
	@Query(value=" select c.guid from course c   inner join learnerenrollment le on le.course_id = c.id inner join learner l on l.id = le.learner_id where customer_id=:customer_id", nativeQuery = true )
	List<Object[]> findByLearner_Customer_Id(@Param("customer_id") long customer_id );
	
	@Query(value=" select c.guid from course c   inner join learnerenrollment le on le.course_id = c.id inner join learner l on l.id = le.learner_id where le.ENROLLMENTSTATUS = 'Active' and le.learner_id=:learner_id", nativeQuery = true )
	List<Object[]> getCourseGuidByLearner(@Param("learner_id") long learner_id );
	
	@Query(value=" select c.id, c.guid from course c " +  
				 " inner join learnerenrollment le on le.course_id = c.id " + 
				 " inner join learner l on l.id = le.learner_id " + 
				 " where le.ENROLLMENTSTATUS = 'Active' and le.learner_id=:learner_id and le.course_id in (:courseId)", nativeQuery = true )
	List<Object[]> getCourseGuidByLearnerByCourse(@Param("learner_id") long learner_id, @Param("courseId") List<Long> courseId );
	
	
	@Query(value=" select count(le.id) from learnerenrollment le inner join course c " +
				 " on c.id=le.course_id inner join learner l "+
			     " on l.id = le.learner_id where customer_id=:customer_id " 
				 + "and c.guid in :courseguid  ", nativeQuery = true )
	Long countByCourseGuidByCustomerId(@Param("customer_id") long customer_id, @Param("courseguid") List<String> allGuid);
	
	@Query(value=" select LEARNERINSTRUCTIONS from learnerenrollment where id=:id ", nativeQuery = true )
	String getLearnerEnrollmentInstruction(@Param("id") long id);

	@Modifying
	@Transactional
   	@Query(value="update LEARNERENROLLMENT set LEARNERINSTRUCTIONS=:learnerinstructions  where ID = :id", nativeQuery = true )
   	void saveLearnerEnrollmentInstruction(@Param("id") Long id, @Param("learnerinstructions") String learnerinstructions);

	
	@Query(value=" select count(le.id) from learnerenrollment le   " +
			" inner join learner l on l.id = le.learner_id  " +
			" inner join vu360user u on u.id = l.vu360user_id  " +
			" inner join course c on c.id = le.course_id  " +
			" inner join subscription s on s.id=le.subscription_id  " +
			" where u.username=:username and  " +
		    " c.BUSINESSUNIT_NAME='MOC On Demand'  " + 
			" and (le.moc_status is NULL or le.moc_status!='Completed') " +
		    " and le.ENROLLMENTSTATUS!='Dropped' "+
			" and s.subscription_Code = :subscriptionCode ", nativeQuery = true )
	Long countMOCEnrollmentBySubscription(@Param("username") String username, @Param("subscriptionCode") Long subscriptionCode);
	
	//@Query(value="SELECT C.GUID, C.COURSETYPE, LCS.PERCENTCOMPLETE, LE.ID as LE_ID , CONVERT(varchar, SYCLASS.CLASSSTARTDATE, 110) as CLASSSTARTDATE,	CONVERT(varchar, SYCLASS.CLASSENDDATE, 110) as CLASSENDDATE "
	@Query(value="SELECT C.GUID, C.COURSETYPE, LCS.PERCENTCOMPLETE, LE.ID as LE_ID , SYCLASS.CLASSSTARTDATE as CLASSSTARTDATE,	SYCLASS.CLASSENDDATE as CLASSENDDATE "
			+" FROM VU360USER V  "
			+" INNER JOIN LEARNER L ON L.VU360USER_ID = V.ID  "
			+" INNER JOIN LEARNERENROLLMENT LE ON LE.LEARNER_ID = L.ID "
			+" INNER JOIN COURSE C ON C.ID = LE.COURSE_ID  "
			+" INNER JOIN LEARNERCOURSESTATISTICS LCS ON LCS.LEARNERENROLLMENT_ID =LE.ID  "
			+" LEFT OUTER JOIN SYNCHRONOUSCLASS SYCLASS ON SYCLASS.COURSE_ID = C.ID and SYNCHRONOUSCLASS_ID = SYCLASS.id "
			+" WHERE V.USERNAME = :userName AND C.GUID  IN (:courseGuid)", nativeQuery = true)
	public List<Object[]> getEnrollmentByUsersByCourse(@Param("userName") String userName, @Param("courseGuid") List courseGuid);
	
	@Query (value="Select u.USERGUID from vu360user u" + 
			"	Inner Join Learner l on l.vu360user_id=u.id" + 
			"	Inner Join LEARNERENROLLMENT le on le.LEARNER_ID=l.id" + 
			"	where le.id=:enrollmentId", nativeQuery = true)
	public String getVU360UserByEnrollmentId(@Param("enrollmentId") long enrollmentId);
}
