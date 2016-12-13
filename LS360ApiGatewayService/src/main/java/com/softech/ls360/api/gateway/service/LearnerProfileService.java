package com.softech.ls360.api.gateway.service;

import com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfile;

public interface LearnerProfileService {
	
	LearnerProfile getLearnerProfile(String userName);

}
