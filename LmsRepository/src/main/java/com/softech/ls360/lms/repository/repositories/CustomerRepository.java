package com.softech.ls360.lms.repository.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.projection.customer.CustomerCustomFields;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Customer findByCustomerGuid(String customerGuid);
	Customer findById(Long id);
	Optional<List<CustomerCustomFields>> findCustomFieldsById(Long customerId);
	
	@Query(value = " select c.* from VU360USER u " +
			" inner join Learner r on u.ID = r.VU360USER_ID " +
			" inner join CUSTOMER c on r.CUSTOMER_ID = c.ID " +
			" where u.USERNAME = ?1 ", nativeQuery = true)
	    List<Customer> findByUsername(String username);
	
}
