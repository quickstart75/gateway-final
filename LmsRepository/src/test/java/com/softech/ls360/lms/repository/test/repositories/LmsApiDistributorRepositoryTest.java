package com.softech.ls360.lms.repository.test.repositories;

import javax.inject.Inject;

import org.junit.Test;

import com.softech.ls360.lms.repository.entities.LmsApiDistributor;
import com.softech.ls360.lms.repository.repositories.LmsApiDistributorRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class LmsApiDistributorRepositoryTest extends LmsRepositoryAbstractTest {

	@Inject
	private LmsApiDistributorRepository lmsApiDistributorRepository;
	
	@Test
	public void test1() {
		
	}
	
	//@Test
	public void findLmsApiDistributor() {
		
		String userName = "LinuxFoundation";
		String password = "576wJkHsajRXT5C";
		try {
			LmsApiDistributor lmsApiDistributor = lmsApiDistributorRepository.findByUserNameAndPassword(userName, password);;
			System.out.println(lmsApiDistributor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
