package com.softech.ls360.api.gateway.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.softech.ls360.api.gateway.service.model.response.EngagementTeamByMonthResponse;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithCourseUserRest;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithUserRest;

public interface LearnerCourseStatisticsService {
	EngagementTeamByMonthResponse LearnerGroupCourseStatisticsByMonth(Long customerId, String startDate, String endDate);
	List<UserGroupwithUserRest> getUsersTimespentByLearnerGroup(Long customerId);
	List<UserGroupwithCourseUserRest> getCourseEngagementByTeam(Long customerId);
	List<Object[]> getLearnerCourseStatisticsByUsername(String username);
	List<Object[]> getLearnerCourseStatisticsByUsernameAndComplete(String username);
	
	public List learnerTimespentByMonth(String username, String startDate, String endDate);
	public List learnerTimespentByDay(String username, String startDate, String endDate);
}
