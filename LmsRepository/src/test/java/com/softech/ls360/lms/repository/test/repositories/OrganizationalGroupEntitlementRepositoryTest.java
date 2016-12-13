package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import com.softech.ls360.lms.repository.entities.OrganizationalGroupEntitlement;
import com.softech.ls360.lms.repository.repositories.OrganizationalGroupEntitlementRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class OrganizationalGroupEntitlementRepositoryTest extends LmsRepositoryAbstractTest {
	
	@Inject
	private OrganizationalGroupEntitlementRepository  organizationalGroupEntitlementRepository;
	
	@Test
	public void test1() {
		
	}
	
	//@Test
	public void findOrganizationalGroupByCustomerEntitlementId() {
		
		Long customerEntitlementId = 208205L;  
		try {
			List<OrganizationalGroupEntitlement> organizationalGroupEntitlementList = organizationalGroupEntitlementRepository.findByCustomerEntitlementId(customerEntitlementId);
			System.out.println(organizationalGroupEntitlementList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
