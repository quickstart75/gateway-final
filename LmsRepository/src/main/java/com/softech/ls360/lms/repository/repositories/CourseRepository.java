package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

	Course findByCourseGuid(String courseGuid);
	List<Course> findByCourseCustomerEntitlementCustomerEntitlementId(Long customerEntitlementId);
	List<Course> findByCourseCourseGroupCourseGroupCourseGroupGuid(String courseGroupGuid);
	
	List<Course> findByCourseCustomerEntitlementCustomerEntitlementIdAndCourseCustomerEntitlementCourseGroupCourseGroupGuid(Long customerEntitlementId, String courseGroupGuid);
	
	@Query(value = "select c.courseGuid, c.topicsCovered from com.softech.ls360.lms.repository.entities.Course c where c.courseGuid in ( :guids )")
	List<Object[]> findCourseOutlineByGuids( @Param("guids") List<String> guids);
}
