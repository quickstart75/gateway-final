package com.softech.ls360.api.gateway.service.impl;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.Distributor;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;


@Service
public class LearnerServiceImpl implements LearnerService {
	private static final Logger logger = LogManager.getLogger();

	@Inject
	private LearnerRepository learnerRepository;
	
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

}
