package com.softech.ls360.lms.repository.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.SynchronousSession;

public interface SynchronousSessionRepository extends CrudRepository<SynchronousSession, Long>{
	
	@Query(value = "SELECT ss.STARTDATETIME,ss.ENDDATETIME,ss.SESSION_KEY,c.GUID FROM  COURSE c " + 
			"INNER JOIN SYNCHRONOUSCLASS sc ON c.ID = sc.COURSE_ID " + 
			"INNER JOIN SYNCHRONOUSSESSION ss ON sc.ID = ss.SYNCHRONOUSCLASS_ID "
			+ "where sc.PROBABILITY > = 50 AND sc.ISEVENT = 0 AND sc.CLASSSTARTDATE > GETDATE() AND sc.TIMEZONE_ID=:timeZone AND "
			+ "c.GUID in (:courseGuid) AND ss.status NOT IN ('C','D')", nativeQuery = true)
	List<Object[]> findSynchronousSessionByCourses(@Param("courseGuid") List<String> courseGuid, @Param("timeZone") Integer timeZone);
}
