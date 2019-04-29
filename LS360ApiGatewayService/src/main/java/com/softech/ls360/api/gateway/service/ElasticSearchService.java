package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.softech.ls360.api.gateway.service.model.request.ElasticSearchCourseRequest;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningRequest;

public interface ElasticSearchService {
	List getMagentoSubscriptionIdByUsername(Map request);
	void setMainElasticCourseSearchParam(InformalLearningRequest request, ElasticSearchCourseRequest onjESearch);
	List getSubscriptionViltCourses(ElasticSearchCourseRequest onjESearch, Integer storeId);
	
	List<String> getSubscriptionsGuidsByClassDates(String startDate, String endDate, List guids);
	List<String> getEnrollmentCourseGuidsByClassDates(String startDate, String endDate, List guids, String username);
}
