package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.softech.ls360.api.gateway.service.InformalLearningService;
import com.softech.ls360.lms.repository.entities.InformalLearning;
import com.softech.ls360.lms.repository.repositories.InformalLearningRepository;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;

@Service
public class InformalLearningServiceImpl implements InformalLearningService {

	@Inject
	private InformalLearningRepository informalLearningRepository;
	
	@Inject
	private LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
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
		
		Integer totalTimeSpentOfUserCourse = learnerCourseStatisticsRepository.totalTimeSpentOfUserCourse(userName, "Active");
		List<Object[]> activityTimeSpent = informalLearningRepository.getActivityTimeSpent(userName);
		
		Long  totalActivityTimeSpent = getTotalActivityTimeSpent(activityTimeSpent);
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
}
