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
	
	@Query(value = "select isNull(c.ceus, 0) from Course c where c.Guid in (:guids)", nativeQuery = true)
	List<Object> findByCourseGuid(@Param("guids") List<String> guids);
	
	
	@Query(value = "SELECT ID, NAME, DESCRIPTION, (Select top 1 scene_id from CONTENTOBJECT_SCENE where contentobject_id=co.id) as sceneId, CONTENTOBJECT_GUID " +
				   " FROM CONTENTOBJECT co WHERE CONTENTOBJECT_GUID IN (:guids)", nativeQuery = true)
	List<Object[]> findLessonByGuids(@Param("guids") List<String> guids);
	
	@Query(value = "SELECT co.ID, co.CONTENTOBJECT_GUID, (Select top 1 scene_id from CONTENTOBJECT_SCENE where contentobject_id=co.id) as sceneId, cdo.displayorder " +
			   " FROM CONTENTOBJECT co " +
			   " inner join coursedisplayorder cdo on cdo.item_id=co.id and cdo.course_id=co.course_id " + 
			   " WHERE CONTENTOBJECT_GUID IN (:guids)  " + 
			   " order by cdo.displayorder ", nativeQuery = true)
	List<Object[]> findLessonWithFirstSlideIdByGuids(@Param("guids") List<String> guids);

	@Query(value = " select ID, NAME, DESCRIPTION, SCENE_GUID  from scene where SCENE_GUID IN (:guids) ", nativeQuery = true)
	List<Object[]> findSlideByGuids(@Param("guids") List<String> guids);
	
	@Query(value = " select ID from course where GUID =:guid ", nativeQuery = true)
	Long findIdByGuid(@Param("guid") String guid);
	
	@Query(value = " SELECT LEARNINGOBJECTIVES, SUPPLEMENT_COURSE_ID FROM COURSE WHERE GUID =:guid " +
			   	   " AND LEARNINGOBJECTIVES LIKE CONCAT('%', :searchText, '%') ", nativeQuery = true)
	Object[] getCourseMaterialByGuid(@Param("guid") String guid, @Param("searchText") String searchText);
}
