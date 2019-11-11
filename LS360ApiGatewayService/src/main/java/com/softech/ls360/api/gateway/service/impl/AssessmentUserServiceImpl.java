package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.AssessmentUserService;
import com.softech.ls360.lms.repository.entities.AssessmentUser;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.AssessmentUserRepository;

@Service
public class AssessmentUserServiceImpl implements AssessmentUserService {

	@Inject
	AssessmentUserRepository assessmentUserRepository;
	
	@Override
	public AssessmentUser addAssAssessmentUser(AssessmentUser user) {
		if(user != null) {
			return assessmentUserRepository.save(user);
		}
		return null;
	}

	@Override
	public List<AssessmentUser> getAllAssessmentUser() {
		return new ArrayList<AssessmentUser>();
	}

	@Override
	public AssessmentUser getAssessmentByUserId(long vu360UserId) {		
		VU360User user=new VU360User();
		user.setId(vu360UserId);
		
		return assessmentUserRepository.findByUser(user);
		
	}

}
