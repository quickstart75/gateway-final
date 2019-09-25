package com.softech.ls360.api.gateway.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hibernate.annotations.common.util.impl.Log;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.GroupProductService;
import com.softech.ls360.lms.repository.entities.GroupProductEnrollment;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlement;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlementCourse;
import com.softech.ls360.lms.repository.repositories.GroupProductEnrollmentRepository;
import com.softech.ls360.lms.repository.repositories.GroupProductEntitlementCourseRepository;

@Service
public class GroupProductServiceImpl implements GroupProductService{
	final String COURSE_STATUS_NOTSTARTED = "notstarted";
	final String COURSE_STATUS_INPROGRESS = "inprogress";
	final String COURSE_STATUS_CPMPLETED = "completed";
	
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
		return groupProductEntitlementCourseRepository.findByGroupProductEntitlementOrderBySequenceAsc(groupProductEntitlement);
	}
	
	public Map<Long, String> getEnrollmentStatusByGroupProductEnrollments(List<Long> enrollmentIds){
		Map<Long, String> objMap = new HashMap<Long, String>();
		try{
		List<Object[]> enrollments = groupProductEnrollmentRepository.getEnrollmentStatusByGroupProductEnrollments(enrollmentIds);
		//long counter = -1;
		
		for (Object[] record : enrollments) {
			Long index = Long.parseLong(record[0].toString());
			String dbStatus="";
			
			if(record[2]==null){
				dbStatus = COURSE_STATUS_NOTSTARTED;
			}else{
				dbStatus = record[2].toString();
			}
			
			if(objMap.get(index)==null){
				objMap.put(index, dbStatus);
			}else{
				String existingStatus = objMap.get(index);
				
				if(existingStatus.equalsIgnoreCase(COURSE_STATUS_NOTSTARTED) && dbStatus.equalsIgnoreCase(COURSE_STATUS_INPROGRESS)){
					objMap.put(index, dbStatus);
				}else if(existingStatus.equalsIgnoreCase(COURSE_STATUS_INPROGRESS) && dbStatus.equalsIgnoreCase(COURSE_STATUS_CPMPLETED)){
					objMap.put(index, dbStatus);
				}
				
			}
			
		}
		}catch(Exception ex){
				
		}
		return objMap;
	}
}
