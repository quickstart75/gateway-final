package com.softech.ls360.api.gateway.service;

import java.util.List;

import com.softech.ls360.api.gateway.service.model.response.EngagementTeamByMonthResponse;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithCourseUserRest;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithUserRest;

public interface LearnerCourseStatisticsService {
	EngagementTeamByMonthResponse LearnerGroupCourseStatisticsByMonth(Long customerId, String startDate, String endDate);
	List<UserGroupwithUserRest> getUsersTimespentByLearnerGroup(Long customerId);
	List<UserGroupwithCourseUserRest> getCourseEngagementByTeam(Long customerId);
}
