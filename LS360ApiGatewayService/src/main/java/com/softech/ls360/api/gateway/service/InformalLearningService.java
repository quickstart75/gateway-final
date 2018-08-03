package com.softech.ls360.api.gateway.service;



import java.util.HashMap;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.InformalLearning;

public interface InformalLearningService {
	public void logInformalLearning(InformalLearning informalLearning);
	public List<HashMap<String,Double>> getActivityTimeSpent(String username);
	public List<Object[]> getActivityTimeSpentByTopic(String userName);
}
