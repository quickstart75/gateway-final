package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.LockedCourse;

public interface LockedCourseRepository extends CrudRepository<LockedCourse, Long> {
	
	@Query(value="EXEC UDP_SELECT_LOCKEDCOURSES :enrollmentId", nativeQuery = true)
	List<LockedCourse> getLockedCourses(@Param("enrollmentId") String enrollmentId);

}
