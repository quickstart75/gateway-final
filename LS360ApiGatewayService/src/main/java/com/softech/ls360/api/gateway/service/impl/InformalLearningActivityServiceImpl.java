package com.softech.ls360.api.gateway.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.InformalLearningActivityService;
import com.softech.ls360.lms.repository.entities.InformalLearningActivity;
import com.softech.ls360.lms.repository.repositories.InformalLearningActivityRepository;


@Service
public class InformalLearningActivityServiceImpl implements InformalLearningActivityService{

	@Inject
	InformalLearningActivityRepository informalLearningActivityRepository;
	
	@Override
	public Map<String, Map<String, String>> getInformalLearningActivityByUser(Long userId) {
		Map<String, Map<String, String>> mapEnrollment = new  HashMap<String, Map<String, String>>();
		List<InformalLearningActivity>  arrEnrollment = informalLearningActivityRepository.findByVu360userId(userId);
		Map<String, String> subMapEnrollment;
		for(InformalLearningActivity subArr: arrEnrollment){
			subMapEnrollment = new HashMap<String,String>();
			subMapEnrollment.put("status", subArr.getStatus().trim());
			
			mapEnrollment.put(subArr.getItemGuid(), subMapEnrollment);	
		}
		
		return mapEnrollment;
	}
	
	public Map<String, Map<String, String>> getInformalLearningActivityCount(int storeId){
		Map<String, Map<String, String>> mapEnrollment = new  HashMap<String, Map<String, String>>();
		List<Object[]> findByEnrollmentIds = informalLearningActivityRepository.getInformalLearningActivityCount(storeId);
		Map<String, String> subMapEnrollment;
		
		for (Object[] record : findByEnrollmentIds) {
			subMapEnrollment = new HashMap<String,String>();
			subMapEnrollment.put("count", record[0].toString());
			mapEnrollment.put(record[1].toString(), subMapEnrollment);
		}
		return mapEnrollment;
	}

}
