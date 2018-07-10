package com.softech.ls360.api.gateway.service;

import com.softech.ls360.api.gateway.service.model.response.EngagementTeamByMonthResponse;

public interface LearnerCourseStatisticsService {
	EngagementTeamByMonthResponse LearnerGroupCourseStatisticsByMonth(Long customerId, String startDate, String endDate);
}
