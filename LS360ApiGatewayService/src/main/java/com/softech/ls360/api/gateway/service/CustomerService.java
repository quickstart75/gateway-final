package com.softech.ls360.api.gateway.service;

import java.util.Set;

import com.softech.ls360.lms.repository.entities.CustomField;

public interface CustomerService {

	Set<CustomField> findCustomerCustomFields(Long customerId);
	
}
