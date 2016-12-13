package com.softech.ls360.api.gateway.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.lms.repository.entities.CustomField;
import com.softech.ls360.lms.repository.projection.customer.CustomerCustomFields;
import com.softech.ls360.lms.repository.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Inject
	private CustomerRepository customerRepository;
	
	@Override
	public Set<CustomField> findCustomerCustomFields(Long customerId) {
		Set<CustomField> customFields = null;
		Optional<List<CustomerCustomFields>> optionalCustomerCustomFields = customerRepository.findCustomFieldsById(customerId);
		if (optionalCustomerCustomFields.isPresent()) {
			List<CustomerCustomFields> customerCustomFields = optionalCustomerCustomFields.get();
			if (!CollectionUtils.isEmpty(customerCustomFields)) {
				customFields = customerCustomFields
						.stream()
						.map(CustomerCustomFields::getCustomFields)
						.collect(Collectors.toSet());
			}
		}
		return customFields;
	}

}
