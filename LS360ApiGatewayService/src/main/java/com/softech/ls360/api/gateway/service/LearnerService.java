package com.softech.ls360.api.gateway.service;

import java.util.List;

import com.softech.ls360.lms.repository.entities.LearnerGroup;
import com.softech.ls360.lms.repository.projection.VU360UserProjection;

public interface LearnerService {
	
	int getStoreId(String userName);
	List<VU360UserProjection> findByLearnerGroupId(Long learnerGroupId);
	Long countByCustomerId(Long customerId);
	List<VU360UserProjection> findByCustomer(Long learnerGroupId);
	List<Object[]> findUserCourseAnalyticsByUserName(String username);
	String findLearnerGroupByUsername(String username);
	List<Object[]> findSubscriptionNameByUsername(String username);
}
