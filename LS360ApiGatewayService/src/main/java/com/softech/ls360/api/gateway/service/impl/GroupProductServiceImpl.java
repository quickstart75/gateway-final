package com.softech.ls360.api.gateway.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.GroupProductService;
import com.softech.ls360.lms.repository.entities.GroupProductEnrollment;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlement;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlementCourse;
import com.softech.ls360.lms.repository.repositories.GroupProductEnrollmentRepository;
import com.softech.ls360.lms.repository.repositories.GroupProductEntitlementCourseRepository;
import com.softech.ls360.lms.repository.repositories.GroupProductEntitlementRepository;

@Service
public class GroupProductServiceImpl implements GroupProductService{
	
	/*@Inject
	GroupProductEnrollmentRepository groupProductEnrollmentRepository;*/
	
	@Inject
	GroupProductEntitlementCourseRepository groupProductEntitlementCourseRepository;
	
//	@Inject
//	private GroupProductEntitlementRepository groupProductEntitlementRepository;
	
	@Inject
	private GroupProductEnrollmentRepository groupProductEnrollmentRepository;
	
	public List<GroupProductEnrollment> searchGroupProductEnrollmentByUsrename(String usrename){
		return groupProductEnrollmentRepository.findByVu360User_username(usrename);
	}

	
	public GroupProductEnrollment searchGroupProductEnrollmentById(long id) {
		return groupProductEnrollmentRepository.findOne(id);
	}


	@Override
	public List<GroupProductEntitlementCourse> searchCourseByGroupEntitlement(GroupProductEntitlement groupProductEntitlement) {
		return groupProductEntitlementCourseRepository.findByGroupProductEntitlement(groupProductEntitlement);
	}
	
	
}
