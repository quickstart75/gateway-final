package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Map;

import com.softech.ls360.lms.repository.entities.GroupProductEnrollment;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlement;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlementCourse;

public interface GroupProductService {
	
	/*public GroupProductEntitlement saveGroupProductEntitlement(GroupProductEntitlement objGP);
    public GroupProductEnrollment saveGroupProductEnrollment(GroupProductEnrollment objGPE);
    public GroupProductEntitlementCourse saveGroupProductEntitlementCourse(GroupProductEntitlementCourse objGPEC);
    public GroupProductEntitlement getGroupProductEnrollment(Long id);
	*/    
    
	public List<GroupProductEnrollment> searchGroupProductEnrollmentByUsrename(String usrename);
	
	public GroupProductEnrollment searchGroupProductEnrollmentById(long id);
	
	public List<GroupProductEntitlementCourse> searchCourseByGroupEntitlement(GroupProductEntitlement groupProductEntitlement);
	
	public Map getEnrollmentStatusByGroupProductEnrollments(List<Long> enrollmentIds);
}
