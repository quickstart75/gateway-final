package com.softech.ls360.api.gateway.service;

import java.util.List;

import com.softech.ls360.lms.repository.entities.GroupProductEnrollment;

public interface GroupProductService {
	
	/*public GroupProductEntitlement saveGroupProductEntitlement(GroupProductEntitlement objGP);
    public GroupProductEnrollment saveGroupProductEnrollment(GroupProductEnrollment objGPE);
    public GroupProductEntitlementCourse saveGroupProductEntitlementCourse(GroupProductEntitlementCourse objGPEC);
    public GroupProductEntitlement getGroupProductEnrollment(Long id);
	*/    
    
	public List<GroupProductEnrollment> searchGroupProductEnrollmentByUsrename(String usrename);
	
	
}
