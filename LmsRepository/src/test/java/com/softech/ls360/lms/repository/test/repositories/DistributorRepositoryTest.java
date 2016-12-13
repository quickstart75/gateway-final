package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.repository.entities.CustomField;
import com.softech.ls360.lms.repository.entities.Distributor;
import com.softech.ls360.lms.repository.projection.distributor.DistributorCustomFields;
import com.softech.ls360.lms.repository.repositories.DistributorRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class DistributorRepositoryTest extends LmsRepositoryAbstractTest  {
	
	@Inject
	private DistributorRepository distributorRepository;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void findByDistributorCode() {
		String distributorCode = "10302";
		Distributor distributor = distributorRepository.findByDistributorCode(distributorCode);
		System.out.println(distributor);
	}
	
	@Test
	public void findDistributorCustomFields() {
		
		Long distributorId = 52L;
		Optional<List<DistributorCustomFields>> optionalDistributorCustomFields = distributorRepository.findCustomFieldsById(distributorId);
		if (optionalDistributorCustomFields.isPresent()) {
			List<DistributorCustomFields> distributorCustomFields = optionalDistributorCustomFields.get();
			if (!CollectionUtils.isEmpty(distributorCustomFields)) {
				Set<CustomField> customFields = distributorCustomFields
						.stream()
						.map(DistributorCustomFields::getCustomFields)
						.collect(Collectors.toSet());
			}
		}
		
		
	}
	
}
