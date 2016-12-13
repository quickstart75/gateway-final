package com.softech.ls360.api.gateway.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.LmsRoleLmsFeatureService;
import com.softech.ls360.lms.repository.entities.LmsRole;
import com.softech.ls360.lms.repository.entities.LmsRoleLmsFeature;
import com.softech.ls360.lms.repository.repositories.LmsRoleLmsFeatureRepository;

@Service
public class LmsRoleLmsFeatureServiceImpl implements LmsRoleLmsFeatureService{
	
	@Inject
	private LmsRoleLmsFeatureRepository lmsRoleLmsFeatureRepository; 

	@Override
	public Boolean hasFeature(String featureName, LmsRole role) {

		LmsRoleLmsFeature feature = null;
		Long id = role.getId();
		feature = lmsRoleLmsFeatureRepository.findByLmsRoleIdAndLmsFeature_FeatureName(id, featureName);
		
		if(feature != null)
			if(feature.getEnabledTf())
				return true;
		
		return false;
	}

}
