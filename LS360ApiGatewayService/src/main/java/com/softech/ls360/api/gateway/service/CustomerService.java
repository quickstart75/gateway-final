package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Set;

import com.softech.ls360.lms.repository.entities.CustomField;
import com.softech.ls360.lms.repository.entities.Customer;

public interface CustomerService {
	Set<CustomField> findCustomerCustomFields(Long customerId);
	Customer findByUsername(String username);
	List<Object[]> findEntitlementByCustomer(Long customerId);
}
