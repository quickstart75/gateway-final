package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.OrganizationalGroup;

public interface OrganizationalGroupRepository extends CrudRepository<OrganizationalGroup, Long> {

	List<OrganizationalGroup> findByCustomerId(Long customerId);
	
	@Query("select og from OrganizationalGroup og JOIN Customer c on og.customer.id = c.id WHERE c.id=:customerId AND og.rootOrgGroup IS NULL")
	public List<OrganizationalGroup>  findByCustomerIdAndRootOrgGroupIsNull(@Param("customerId") Long customerId);
}
