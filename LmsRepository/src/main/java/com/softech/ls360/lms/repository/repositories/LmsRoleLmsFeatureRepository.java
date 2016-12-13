package com.softech.ls360.lms.repository.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.LmsRoleLmsFeature;

public interface LmsRoleLmsFeatureRepository extends CrudRepository<LmsRoleLmsFeature, Long> {

	List<LmsRoleLmsFeature> findByLmsRoleIdIn(Collection<Long> ids);
	LmsRoleLmsFeature findByLmsRoleIdAndLmsFeature_FeatureName(Long id, String featureName);
}
