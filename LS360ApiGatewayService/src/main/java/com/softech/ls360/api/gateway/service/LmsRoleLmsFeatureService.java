package com.softech.ls360.api.gateway.service;

import com.softech.ls360.lms.repository.entities.LmsRole;
import com.softech.ls360.lms.repository.entities.LmsRoleLmsFeature;

public interface LmsRoleLmsFeatureService {

	Boolean hasFeature(String feature, LmsRole role);
	
}
