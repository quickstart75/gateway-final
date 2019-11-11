package com.softech.ls360.api.gateway.service;

import java.util.List;

import com.softech.ls360.lms.repository.entities.AssessmentUser;

public interface AssessmentUserService {
	
	public AssessmentUser addAssAssessmentUser(AssessmentUser user);
	public List<AssessmentUser> getAllAssessmentUser();
	public AssessmentUser getAssessmentByUserId(long vu360UserId);
	
}
