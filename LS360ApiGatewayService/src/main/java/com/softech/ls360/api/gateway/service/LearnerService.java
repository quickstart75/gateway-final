package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.softech.ls360.api.gateway.service.model.vo.EnrollmentVO;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.projection.VU360UserDetailProjection;

public interface LearnerService {
	Learner findByVu360UserUsername(String userName);
	int getStoreId(String userName);
	List<VU360UserDetailProjection> findByLearnerGroupId(Long learnerGroupId);
	Long countByCustomerId(Long customerId);
	List<VU360UserDetailProjection> findByCustomer(Long learnerGroupId);
	List<Object[]> findUserCourseAnalyticsByUserName(String username);
	List<Object[]> findUserCourseAnalyticsByUserNameByCourseGUIDs(String username, List<String> guids);
	String findLearnerGroupByUsername(String username);
	List<Object[]> findSubscriptionNameByUsername(String username);
	void deleteLearnerFromLearnerGroup(String username);
	
	Map<String, String> getAuthorByName(String authorName);
	List<EnrollmentVO> getEnrollmentsByDates(String sDate, String eDate);
}
