package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.VILTAttendance;

public interface VILTAttendanceRepository extends CrudRepository<VILTAttendance, Long>{
	
	@Query(value = "select c.ENROLLMENT_ID, c.ATTENDANCE_DATE from VILT_Attendance c where c.ENROLLMENT_ID = :ids  order by c.ENROLLMENT_ID ", nativeQuery = true)
	List<Object[]> findByEnrollmentIds( @Param("ids") Long ids);
}
