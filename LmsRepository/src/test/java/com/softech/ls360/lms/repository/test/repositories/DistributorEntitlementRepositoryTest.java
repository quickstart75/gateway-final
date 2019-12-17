package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import com.softech.ls360.lms.repository.entities.DistributorEntitlement;
import com.softech.ls360.lms.repository.repositories.DistributorEntitlementRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class DistributorEntitlementRepositoryTest extends LmsRepositoryAbstractTest {
	
	@Inject
	private DistributorEntitlementRepository distributorEntitlementRepository;

	//@Test
	//public void test1() {
		
	//}
	
	//@Test
	public void findById() {
			
		Long id = 1L; 
		try {
			DistributorEntitlement distributorEntitlement = distributorEntitlementRepository.findOne(id);
			System.out.println(distributorEntitlement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void findByDistributorId() {
		
		Long distributorId = 1L;  
		try {
			List<DistributorEntitlement> distributorEntitlementList = distributorEntitlementRepository.findByDistributorId(distributorId);
			int length = distributorEntitlementList.size();
			System.out.println(distributorEntitlementList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
