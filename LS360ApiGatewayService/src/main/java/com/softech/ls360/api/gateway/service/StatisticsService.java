package com.softech.ls360.api.gateway.service;

import java.util.List;

import com.softech.ls360.api.gateway.service.model.response.LearnerCourseStatisticsResponse;

public interface StatisticsService {
	
	List<LearnerCourseStatisticsResponse> getLearnerCourseStatistics(List<Long> learnerEnrollmentIdList);
	Long getAverageViewTimeByWeekByUserName(String username);
}
