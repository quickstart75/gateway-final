package com.softech.ls360.api.gateway.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.UserGroupService;
import com.softech.ls360.lms.repository.entities.LearnerGroup;
import com.softech.ls360.lms.repository.entities.LearnerGroupMember;
import com.softech.ls360.lms.repository.repositories.LearnerGroupMemberRepository;
import com.softech.ls360.lms.repository.repositories.LearnerGroupRepository;

@Service
public class UserGroupServiceImpl implements UserGroupService{

	@Inject
	LearnerGroupRepository learnerGroupRepository;
	
	@Inject
	LearnerGroupMemberRepository learnerGroupMemberRepository;
	
	@Override
	public List<LearnerGroup> findByCustomer(Long customerId) {
		return learnerGroupRepository.findByCustomerId(customerId);
	}
	
	public void deleteLearnersFromLearnerGroup(Long learnerIdArray[],Long learnerGroupId ){
		List<LearnerGroupMember> objlg = learnerGroupMemberRepository.findByLearnerGroupIdAndLearnerIdIn(learnerGroupId, learnerIdArray);
		for (LearnerGroupMember lg : objlg) 
			learnerGroupMemberRepository.delete(lg);
	}
}
