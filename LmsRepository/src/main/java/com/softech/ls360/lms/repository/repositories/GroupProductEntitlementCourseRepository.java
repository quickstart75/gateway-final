package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.GroupProductEntitlement;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlementCourse;

public interface GroupProductEntitlementCourseRepository extends CrudRepository<GroupProductEntitlementCourse,Long>{
	
//	@Query(value = "select from GroupProductEntitlementCourse "
//			+ "where groupProductEntitlement : entitlementId", nativeQuery = true)
	public List<GroupProductEntitlementCourse> findByGroupProductEntitlement(GroupProductEntitlement entitlement);
	
}
