package com.softech.ls360.api.gateway.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.api.gateway.service.DistributorService;
import com.softech.ls360.lms.repository.entities.CustomField;
import com.softech.ls360.lms.repository.projection.distributor.DistributorCustomFields;
import com.softech.ls360.lms.repository.repositories.DistributorRepository;

@Service
public class DistributorServiceImpl implements DistributorService {

	@Inject
	private DistributorRepository distributorRepository;
	
	@Override
	public Set<CustomField> findDistributorCustomFields(Long distributorId) {
		Set<CustomField> customFields = null;
		Optional<List<DistributorCustomFields>> optionalDistributorCustomFields = distributorRepository.findCustomFieldsById(distributorId);
		if (optionalDistributorCustomFields.isPresent()) {
			List<DistributorCustomFields> distributorCustomFields = optionalDistributorCustomFields.get();
			if (!CollectionUtils.isEmpty(distributorCustomFields)) {
				customFields = distributorCustomFields
						.stream()
						.map(DistributorCustomFields::getCustomFields)
						.collect(Collectors.toSet());
			}
		}
		return customFields;
	}

}
