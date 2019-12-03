package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.InformalLearningService;
import com.softech.ls360.api.gateway.service.UserService;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningActivityRequest;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningActivityUserResponse;
import com.softech.ls360.lms.repository.entities.InformalLearning;
import com.softech.ls360.lms.repository.entities.InformalLearningActivity;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.InformalLearningActivityRepository;
import com.softech.ls360.lms.repository.repositories.InformalLearningRepository;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;

@Service
public class InformalLearningServiceImpl implements InformalLearningService {

	@Inject
	private InformalLearningRepository informalLearningRepository;
	
	@Inject
	private InformalLearningActivityRepository informalLearningActivityRepository;
	
	@Inject
	private LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
	@Value( "${api.humhub.baseURL}" )
    private String humhubBaseURL;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void logInformalLearning(InformalLearning informalLearning) {
		informalLearningRepository.save(informalLearning);
	}
	
	@Override
	public List<Object[]> getActivityTimeSpentByTopic(String userName){
		
		List<Object[]> activityTimeSpent = informalLearningRepository.getActivityTimeSpentByTopic(userName);
		return activityTimeSpent;
	}
	
	@Override
	public List<HashMap<String,Double>> getActivityTimeSpent(String userName){
		Long totalTimeSpentOfUserCourse = 0L;
		
		List<Object[]> lsttotalTimeSpentOfUserCourse = learnerCourseStatisticsRepository.totalTimeSpentOfUserCourse(userName, "Active");
		List<Object[]> activityTimeSpent = informalLearningRepository.getActivityTimeSpent(userName);
		
		Long  totalActivityTimeSpent = getTotalActivityTimeSpent(activityTimeSpent);
		
		if(lsttotalTimeSpentOfUserCourse!=null && lsttotalTimeSpentOfUserCourse.size()>0){
			try{
				Object[] record = lsttotalTimeSpentOfUserCourse.get(0);
				totalTimeSpentOfUserCourse =  Long.parseLong(record[0].toString());
			}catch(Exception ex ){}
		}
		
		Long totalTimeSpent = totalTimeSpentOfUserCourse + totalActivityTimeSpent;
		
		List<HashMap<String,Double>> mapActivityTimeSpent = new ArrayList<HashMap<String,Double>>();
		
		if (totalTimeSpent>0){
			
			if(totalTimeSpentOfUserCourse>0){
			HashMap<String,Double> activity = new HashMap<String,Double>();
			activity.put("activityId", 0D);
			double timeSpent =  (( Double.parseDouble (totalTimeSpentOfUserCourse.toString()) * 100 ) / Double.parseDouble (totalTimeSpent.toString()));
			activity.put("percentage", Double.parseDouble (String.format ("%.1f",timeSpent)));
			mapActivityTimeSpent.add(activity);
			}
			
			for (Object[] record : activityTimeSpent) {
				mapActivityTimeSpent.add(parseActivityAndTimeSpent(record,totalTimeSpent));
			}
		}
		return mapActivityTimeSpent;
	}
	
	private Long getTotalActivityTimeSpent(List<Object[]> records){
		
		Long totalActivityTimeSpent = 0L;
		for (Object[] record : records) {
			totalActivityTimeSpent += Long.parseLong(record[1].toString());
		}
		return totalActivityTimeSpent;
	}
	
	private HashMap<String,Double> parseActivityAndTimeSpent(Object[] record,Long totalTimeSpent){
		HashMap<String,Double> activity = new HashMap<String,Double>();
		activity.put("activityId", Double.parseDouble(record[0].toString()));
		Long activityTimeSpent= Long.parseLong(record[1].toString());
	//	double timeSpent = ((activityTimeSpent * 100) /totalTimeSpent);
		double timeSpent =  (( Double.parseDouble (activityTimeSpent.toString()) * 100 ) / Double.parseDouble (totalTimeSpent.toString()));
		activity.put("percentage", Double.parseDouble (String.format ("%.1f",timeSpent)));
		//activity.put("percentage", Double.parseDouble (String.format ("%.1f",((activityTimeSpent/totalTimeSpent) * 100))));
		return activity;
	}


	public void logInformalLearningActivity(InformalLearningActivity informalLearningActivity){
		informalLearningActivityRepository.save(informalLearningActivity);
	}
	/*
	public InformalLearningActivity getInformalLearningActivity(com.softech.ls360.api.gateway.service.model.request.InformalLearningActivityRequest infLearRequest){
		return informalLearningActivityRepository.findByItemGuidAndVu360userIdAndStoreId(infLearRequest.getItemGuid(), infLearRequest.getVu360userId(), infLearRequest.getStoreId());
	}
	*/
	//TODO add null check
	public InformalLearning getLearnerInformalActivity(com.softech.ls360.api.gateway.service.model.request.InformalLearningActivityRequest infLearRequest){
		return informalLearningRepository.findTopByItemGuidAndUserNameAndStoreId(infLearRequest.getItemGuid(), infLearRequest.getUsername(), infLearRequest.getStoreId());
	}
	
	public InformalLearningActivity findById(long id){
		return informalLearningActivityRepository.findOne(id);
	}
	
	public boolean deleteInformalLearningActivity(long id){
		InformalLearningActivity obj = informalLearningActivityRepository.findOne(id);
		informalLearningActivityRepository.delete(obj);
		return true;
	}
	
	public boolean deleteLearnerInformalActivity(long id){
		InformalLearning obj = informalLearningRepository.findOne(id);
		if(obj!=null)
			informalLearningRepository.delete(obj);
		return true;
	}
	
	/*
	public List<InformalLearningActivityUserResponse> getInformalActivityListByItemGuid(InformalLearningActivityRequest request){
		List<InformalLearningActivity> lstInfo = informalLearningActivityRepository.findByItemGuidAndStoreId(request.getItemGuid(), request.getStoreId());
		
		List<InformalLearningActivityUserResponse> LstInformalResp = new ArrayList<InformalLearningActivityUserResponse>();
		InformalLearningActivityUserResponse objInforResponse;
		for(InformalLearningActivity  objInformal : lstInfo){
			objInforResponse = new InformalLearningActivityUserResponse();
			objInforResponse.setComments(objInformal.getComments());
			
			if(objInformal.getVu360userId() > 0){
				VU360User objUser = userService.findById(objInformal.getVu360userId()); 
				objInforResponse.setFirstName(objUser.getFirstName());
				objInforResponse.setLastName(objUser.getLastName());
				objInforResponse.setUrl(humhubBaseURL + "u/" + objUser.getEmailAddress());
			}else{
				objInforResponse.setUrl("");
			}
			
			objInforResponse.setProfileImg("");
			LstInformalResp.add(objInforResponse);
		}
		
		return LstInformalResp;
	}*/
/*
	@Override
	public Integer getGetTimeInSecondsByUserId(long userId) {
		return informalLearningActivityRepository.getGetTimeInSecondsByUserId(userId);
	}

	@Override
	public Integer getGetTimeInSecondsByUsername(String username) {
		// TODO Auto-generated method stub
		return informalLearningActivityRepository.getGetTimeInSecondsByUsername(username);
	}*/
	
	
	public InformalLearning findLearnerInformalActivityById(long id){
		return informalLearningRepository.findOne(id);
	}
	
	
	public List<InformalLearningActivityUserResponse> getLearnerInformalActivityListByItemGuid(InformalLearningActivityRequest request){
		List<InformalLearning> lstInfo = informalLearningRepository.findByItemGuidAndStoreId(request.getItemGuid(), request.getStoreId());
		
		List<InformalLearningActivityUserResponse> LstInformalResp = new ArrayList<InformalLearningActivityUserResponse>();
		InformalLearningActivityUserResponse objInforResponse;
		for(InformalLearning  objInformal : lstInfo){
			objInforResponse = new InformalLearningActivityUserResponse();
			objInforResponse.setComments(objInformal.getNotes());
			
			if(objInformal.getUserName() !=null && !objInformal.getUserName().equals("")){
				VU360User objUser = userService.findByUsername(objInformal.getUserName()); 
				objInforResponse.setFirstName(objUser.getFirstName());
				objInforResponse.setLastName(objUser.getLastName());
				objInforResponse.setUrl(humhubBaseURL + "u/" + objUser.getEmailAddress());
			}else{
				objInforResponse.setUrl("");
			}
			
			objInforResponse.setProfileImg("");
			LstInformalResp.add(objInforResponse);
		}
		
		return LstInformalResp;
	}

	@Override
	public Integer getGetTimeInSecondsByUsername(String username) {
		return informalLearningRepository.getGetTimeInSecondsByUsername(username);
	}

	@Override
	public List<Map<String, Double>> getLearnerActivityStatus(String userName) {
		Long totalTimeSpentOfUserCourse = 0L;
		Double count=0d; 
		
		List<Object[]> lsttotalTimeSpentOfUserCourse = learnerCourseStatisticsRepository.totalTimeSpentOfUserCourse(userName, "Active");
		List<Object[]> activityTimeSpent = informalLearningRepository.getActivityTimeSpent(userName);
		
		Long  totalActivityTimeSpent = getTotalActivityTimeSpent(activityTimeSpent);
		if(lsttotalTimeSpentOfUserCourse!=null && lsttotalTimeSpentOfUserCourse.size()>0){
			try{
				Object[] record = lsttotalTimeSpentOfUserCourse.get(0);
				totalTimeSpentOfUserCourse =  Long.parseLong(record[0].toString());
				count=Double.parseDouble(record[1].toString());
			}catch(Exception ex ){}
		}
		
		Long totalTimeSpent = totalTimeSpentOfUserCourse + totalActivityTimeSpent;
		
		List<Map<String,Double>> mapActivityTimeSpent = new ArrayList<>();
		
		if (totalTimeSpent>0){
			
			if(totalTimeSpentOfUserCourse>0){
			HashMap<String,Double> activity = new HashMap<String,Double>();
			activity.put("activityId", 0D);
			double timeSpent =  (( Double.parseDouble (totalTimeSpentOfUserCourse.toString()) * 100 ) / Double.parseDouble (totalTimeSpent.toString()));
			activity.put("percentage", Double.parseDouble (String.format ("%.1f",timeSpent)));
			activity.put("count", count);
			mapActivityTimeSpent.add(activity);
			}
			
			for (Object[] record : activityTimeSpent) {
				Map<String, Double> data=parseActivityAndTimeSpent(record,totalTimeSpent);
				data.put("count", Double.parseDouble(record[2].toString()));
				mapActivityTimeSpent.add(data);
			}
		}
		
		return mapActivityTimeSpent;
	}
}
