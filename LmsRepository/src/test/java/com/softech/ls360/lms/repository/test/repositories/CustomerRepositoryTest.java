package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.repository.entities.CustomField;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.projection.customer.CustomerCustomFields;
import com.softech.ls360.lms.repository.repositories.CustomerRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class CustomerRepositoryTest extends LmsRepositoryAbstractTest {
	
	@Inject
	private CustomerRepository customerRepository;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	//@Transactional
	public void findById(){
		try{
			Customer customer = customerRepository.findById(3177300L);
			System.out.println(customer.getName());
			System.out.println(customer.getDistributor().getDistributorCode());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//@Test
	public void findByKey() {
		String apiKey = "dcda5167-8af6-48d3-99cf-4337d19d378a";   // apiKey here is actually customerGuid (QA2)
		
		try {
			Customer customer = customerRepository.findByCustomerGuid(apiKey);
			System.out.println(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void findCustomerCustomFields() {
		Long customerId = 1452L;
		Optional<List<CustomerCustomFields>> optionalCustomerCustomFields = customerRepository.findCustomFieldsById(customerId);
		if (optionalCustomerCustomFields.isPresent()) {
			List<CustomerCustomFields> customerCustomFields = optionalCustomerCustomFields.get();
			if (!CollectionUtils.isEmpty(customerCustomFields)) {
				Set<CustomField> customFields = customerCustomFields
						.stream()
						.map(CustomerCustomFields::getCustomFields)
						.collect(Collectors.toSet());
			}
		}	
	}
	
}
