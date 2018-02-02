package com.softech.ls360.api.gateway.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.Distributor;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.LearnerGroupMember;
import com.softech.ls360.lms.repository.projection.UserCourseAnalytics;
import com.softech.ls360.lms.repository.projection.VU360UserProjection;
import com.softech.ls360.lms.repository.repositories.LearnerGroupMemberRepository;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;


@Service
public class LearnerServiceImpl implements LearnerService {
	private static final Logger logger = LogManager.getLogger();

	@Inject
	private LearnerRepository learnerRepository;
	
	@Inject
	private LearnerGroupMemberRepository learnerGroupMemberRepository;
	
	@Override
	@Transactional
	public int getStoreId(String userName) {
		int storeID = 0;
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		
		if (learner == null){
			logger.debug("Invalid user. Learner is NULL");
			return 0;
		}		 
		
		Customer cust = learner.getCustomer();
		if (cust == null){
			logger.debug("Invalid user. Customer is NULL");
			return storeID ;
		}
		Distributor dist = cust.getDistributor();
		if (dist == null){
			logger.debug("Invalid user. Distributor is NULL");
			return storeID;
		}
		String distCode = dist.getDistributorCode();
		
		try{
			storeID = Integer.parseInt(distCode);
		}catch(NumberFormatException e){
			logger.debug("Invalid Store ID  :: NumberFormatException");			
		}		
		return storeID;		
	}

	public List<VU360UserProjection> findByLearnerGroupId(Long learnerGroupId){
		List<VU360UserProjection> lrnGroupMemberList = learnerGroupMemberRepository.findByLearnerGroupId(learnerGroupId);
		return lrnGroupMemberList;
	}
	
	public List<VU360UserProjection> findByCustomer(Long learnerGroupId){
		List<VU360UserProjection> lrnGroupMemberList = learnerGroupMemberRepository.findByCustomer(learnerGroupId);
		return lrnGroupMemberList;
	}
	
	public List<LearnerGroupMember> findLearnerGroupByUsername(String username){
		return learnerGroupMemberRepository.findByLearner_Vu360User_Username(username);
	}
	
	public List<Object[]> findUserCourseAnalyticsByUserName(String username){
		List<Object[]> courseAnalytics = learnerRepository.findUserCourseAnalyticsByUserName(username);
		return courseAnalytics;
	}
	public Long countByCustomerId(Long customerId){
		Long count = learnerRepository.countByCustomerId(customerId);
		if(count==null)
			return (long) 0;
		
		return count;
	}
}
