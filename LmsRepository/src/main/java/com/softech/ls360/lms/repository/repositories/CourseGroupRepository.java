package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.CourseGroup;

public interface CourseGroupRepository extends CrudRepository<CourseGroup, Long> {

	List<CourseGroup> findByCourseGroupCustomerEntitlementCustomerEntitlementId(Long customerEntitlementId);
	List<CourseGroup> findByDistributorEntitlementId(Long distributorEntitlementId);
	CourseGroup findByCourseGroupGuid(String courseGroupGuid);
	
	@Query("select cg from #{#entityName} cg "
    		+ "join fetch cg.parentCourseGroup cg1 "
    		+ "where cg.courseGroupGuid=:courseGroupGuid")
	List<CourseGroup> findImmediateChildCourseGruopsByCourseGroupGuid(@Param("courseGroupGuid") String courseGroupGuid);
	
    List<CourseGroup> findByIdAndCourseCourseGroup_Course_CourseStatusIgnoreCaseAndCourseCourseGroup_CourseRetiredTfFalse(Long id, String status);
    
    CourseGroup findByCourseGroupGuidAndCourseGroupCustomerEntitlementCustomerEntitlementId(String courseGroupGuid, Long customerEntitlementId);
    
    @Query(value="select top (1) cg.* from COURSEGROUP cg "
    		+ " inner join COURSEGROUP_CUSTOMERENTITLEMENT cg1 on cg1.courseGroup_id = cg.id "
    		+ " inner join COURSE_COURSEGROUP cg2 on cg2.courseGroup_id = cg1.courseGroup_id "
    		+ " where cg1.CUSTOMERENTITLEMENT_id = :customerEntitlementId "
    		+ " and cg2.course_id = :courseId "
    		+ " UNION ALL "
    		+ " select top (1) cg.* from COURSEGROUP cg "
    		+ " inner join COURSE_CUSTOMERENTITLEMENT cg1 on cg1.courseGroup_id = cg.id "
    		+ " where cg1.CUSTOMERENTITLEMENT_id = :customerEntitlementId "
    		+ " and cg1.course_id = :courseId ", nativeQuery = true)
    CourseGroup getCourseGroupByCourseGroupCustomerEntitlement(@Param("customerEntitlementId") Long customerEntitlementId, @Param("courseId") Long courseId);
	
    @Query(value="select top (1) cg.* "
			+" from CourseGroup cg "
			+" inner join SUBSCRIPTION_COURSE sc on sc.courseGroup_id = cg.id "
			+" where sc.subscription_id = :subscriptionId "
			+" and sc.course_id = :courseId" , nativeQuery = true)
    CourseGroup getCourseGroupBySubscription(@Param("subscriptionId") Long subscriptionId, @Param("courseId") Long courseId);

}
