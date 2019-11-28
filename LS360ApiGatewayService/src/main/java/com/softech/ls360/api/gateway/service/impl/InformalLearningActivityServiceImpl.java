package com.softech.ls360.api.gateway.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.InformalLearningActivityService;
import com.softech.ls360.lms.repository.entities.InformalLearning;
import com.softech.ls360.lms.repository.entities.InformalLearningActivity;
import com.softech.ls360.lms.repository.repositories.InformalLearningActivityRepository;
import com.softech.ls360.lms.repository.repositories.InformalLearningRepository;


@Service
public class InformalLearningActivityServiceImpl implements InformalLearningActivityService{

	@Inject
	InformalLearningActivityRepository informalLearningActivityRepository;
	
	@Inject
	InformalLearningRepository informalLearningRepository;
	
	@Override
	public Map<String, Map<String, String>> getInformalLearningActivityByUser(String username) {
		Map<String, Map<String, String>> mapEnrollment = new  HashMap<String, Map<String, String>>();
		List<InformalLearning>  arrEnrollment = informalLearningRepository.findByUserNameAndTypeId(username, 2);
		Map<String, String> subMapEnrollment;
		for(InformalLearning subArr: arrEnrollment){
			if(subArr.getItemGuid()!=null && !subArr.getItemGuid().equals("")){
				subMapEnrollment = new HashMap<String,String>();
				if(subArr.getStatus()!=null)
					subMapEnrollment.put("status", subArr.getStatus().trim());
				else
					subMapEnrollment.put("status", "");
				
				mapEnrollment.put(subArr.getItemGuid(), subMapEnrollment);	
			}
		}
		
		return mapEnrollment;
	}
	
	public Map<String, Map<String, String>> getInformalLearningActivityCount(int storeId){
		Map<String, Map<String, String>> mapEnrollment = new  HashMap<String, Map<String, String>>();
		List<Object[]> findByEnrollmentIds = informalLearningRepository.getInformalLearningActivityCount(storeId);
		Map<String, String> subMapEnrollment;
		
		for (Object[] record : findByEnrollmentIds) {
			subMapEnrollment = new HashMap<String,String>();
			subMapEnrollment.put("count", record[0].toString());
			mapEnrollment.put(record[1].toString(), subMapEnrollment);
		}
		return mapEnrollment;
	}

}
