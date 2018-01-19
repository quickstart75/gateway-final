package com.softech.ls360.api.gateway.service.impl;

import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.UserGroupService;
import com.softech.ls360.lms.repository.entities.LearnerGroup;
import com.softech.ls360.lms.repository.repositories.LearnerGroupRepository;

@Service
public class UserGroupServiceImpl implements UserGroupService{

	@Inject
	LearnerGroupRepository learnerGroupRepository;
	
	@Override
	public List<LearnerGroup> findByCustomer(Long customerId) {
		return learnerGroupRepository.findByCustomerId(customerId);
	}
	
}
