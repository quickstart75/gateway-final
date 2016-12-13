package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.CustomerEntitlement;

public interface CustomerEntitlementRepository extends CrudRepository<CustomerEntitlement, Long> {

	@Query("select ce from #{#entityName} ce "
    		+ "join ce.customer c "
    		+ "where c.customerGuid=:customerGuid")
	List<CustomerEntitlement> findByCustomerCustomerGuid(@Param("customerGuid") String customerGuid);
	List<CustomerEntitlement> findByCustomerCustomerCode(String customerCode);
	
}
