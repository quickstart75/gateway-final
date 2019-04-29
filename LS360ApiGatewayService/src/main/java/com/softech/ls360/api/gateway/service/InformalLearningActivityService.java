package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Map;

public interface InformalLearningActivityService {
	Map<String, Map<String, String>> getInformalLearningActivityByUser(Long userId);
	
	Map<String, Map<String, String>> getInformalLearningActivityCount(int storeId);
}
