package com.softech.ls360.api.gateway.service;

import java.util.List;

import com.softech.ls360.api.gateway.service.model.request.LearnerCourseStatisticsRequest;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseStatisticsResponse;

public interface StatisticsService {
	
	List<LearnerCourseStatisticsResponse> getLearnerCourseStatistics(List<Long> learnerEnrollmentIdList);
	Long getAverageViewTimeByWeekByUserName(String username);
	Boolean updateLearnerCourseStatistics(
			LearnerCourseStatisticsRequest learnerCourseStatisticsRequest);
	public Boolean updateMocStatistics(List<Long> enrollmentIds , String status);
	public Boolean updateCertVoucherStatistics(List<Long> enrollmentIds , String status);
}
