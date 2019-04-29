package com.softech.ls360.lms.repository.repositories;

import com.softech.ls360.lms.repository.entities.ClassroomSchedule;
import com.softech.ls360.lms.repository.entities.SynchronousClass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SynchronousClassRepository extends CrudRepository<SynchronousClass, Long> {

    @Query("SELECT new com.softech.ls360.lms.repository.entities.ClassroomSchedule(SC.id, SC.className, MIN(SS.startDateTime) , L.locationName) FROM SynchronousClass SC\n" +
            " join SynchronousSession SS ON SC.id = SS.synchronousClass.id \n" +
            " join Location L ON L.id = SC.location.id\n" +
            " join Course C ON C.id = SC.course.id\n" +
            " WHERE C.courseGuid=?1\n" +
            " GROUP BY SC.id, L.locationName, SC.className \n" +
            " ORDER BY SC.id ASC")
    List<ClassroomSchedule> findScheduleData(String courseGuid);
    
    
    @Query(value =  " SELECT C.GUID FROM COURSE C " +
					" INNER JOIN SYNCHRONOUSCLASS SC ON SC.COURSE_ID=C.ID  " +
					" INNER JOIN SYNCHRONOUSSESSION SS ON SS.SYNCHRONOUSCLASS_ID=SC.ID " +
					" WHERE C.GUID IN (:guids) " +
					" AND ((SS.STARTDATETIME BETWEEN :sDate AND :eDate )" +
					"        or (SS.ENDDATETIME BETWEEN :sDate AND :eDate)) ", nativeQuery = true)
	List<String> getSubscriptionsGuidsByClassDates(@Param("sDate") String sDate, @Param("eDate") String eDate, @Param("guids") List<String> guids);
	
    
    @Query(value =  " SELECT C.GUID FROM VU360USER U " +
					" INNER JOIN LEARNER L ON L.VU360USER_ID = U.ID  " +
					" INNER JOIN LEARNERENROLLMENT LE ON LE.LEARNER_ID = L.ID " +
					" INNER JOIN SYNCHRONOUSCLASS SC ON SC.COURSE_ID=LE.COURSE_ID  " +
					" INNER JOIN COURSE C ON C.ID = LE.COURSE_ID AND SC.COURSE_ID=C.ID " +
					" INNER JOIN SYNCHRONOUSSESSION SS ON SS.SYNCHRONOUSCLASS_ID=SC.ID  " +
					" WHERE C.GUID IN (:guids) " +
					" AND ((SS.STARTDATETIME BETWEEN :sDate AND :eDate) " +
					" OR (SS.ENDDATETIME BETWEEN :sDate AND :eDate))" +
					" AND ENROLLMENTSTATUS='ACTIVE' " +
					" AND SC.STATUS in ('A','U') " +
					" AND U.USERNAME = :username ", nativeQuery = true)
    List<String> getEnrollmentCourseGuidsByClassDates(@Param("sDate") String sDate, @Param("eDate") String eDate, @Param("guids") List<String> guids, @Param("username") String username);

}