package com.softech.ls360.api.gateway.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.UserGroupService;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.Distributor;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.LearnerGroupMember;
import com.softech.ls360.lms.repository.projection.VU360UserProjection;
import com.softech.ls360.lms.repository.projection.VU360UserDetailProjection;
import com.softech.ls360.lms.repository.repositories.LearnerGroupMemberRepository;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;


@Service
public class LearnerServiceImpl implements LearnerService {
	private static final Logger logger = LogManager.getLogger();

	@Inject
	private LearnerRepository learnerRepository;
	
	@Inject
	private LearnerGroupMemberRepository learnerGroupMemberRepository;
	
	@Inject
	private SubscriptionRepository subscriptionRepository;
	
	@Inject
	private UserGroupService userGroupService;
	
	
	@Override
	public Learner findByVu360UserUsername(String userName){
		return learnerRepository.findByVu360UserUsername(userName);
	}
	
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

	public List<VU360UserDetailProjection> findByLearnerGroupId(Long learnerGroupId){
		List<VU360UserDetailProjection> lrnGroupMemberList = learnerGroupMemberRepository.findByLearnerGroupId(learnerGroupId);
		return lrnGroupMemberList;
	}
	
	public List<VU360UserDetailProjection> findByCustomer(Long learnerGroupId){
		List<VU360UserDetailProjection> lrnGroupMemberList = learnerGroupMemberRepository.findByCustomer(learnerGroupId);
		return lrnGroupMemberList;
	}
	
	@Transactional
	public String findLearnerGroupByUsername(String username){
		LearnerGroupMember lstLGM = learnerGroupMemberRepository.findFirstByLearner_Vu360User_Username(username);
		if(lstLGM!=null)
			return lstLGM.getLearnerGroup().getName();
		else
			return null;
	}
	
	@Transactional
	public void deleteLearnerFromLearnerGroup(String username){
		LearnerGroupMember lstLGM = learnerGroupMemberRepository.findFirstByLearner_Vu360User_Username(username);
		//for (LearnerGroupMember lg : lstLGM)
		if(lstLGM!=null)
			learnerGroupMemberRepository.delete(lstLGM);
	}
	
	public List<Object[]> findUserCourseAnalyticsByUserName(String username){
		List<Object[]> courseAnalytics = learnerRepository.findUserCourseAnalyticsByUserName(username);
		return courseAnalytics;
	}
	
	public List<Object[]> findSubscriptionNameByUsername(String username){
		List<Object[]> subscriptioname = subscriptionRepository.findSubscriptionNameByUsername(username);
		return subscriptioname;
	}
	
	public Long countByCustomerId(Long customerId){
		Long count = learnerRepository.countByCustomerId(customerId);
		if(count==null)
			return (long) 0;
		
		return count;
	}
}
