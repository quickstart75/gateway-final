package com.softech.ls360.api.gateway.service;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import java.util.HashSet;
import java.util.Set;
import org.mockito.runners.MockitoJUnitRunner;

import com.softech.ls360.api.gateway.service.impl.LmsUserDetailsServiceImpl;
import com.softech.ls360.lms.repository.entities.LmsRole;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//@RunWith(MockitoJUnitRunner.class)
public class UserDetailServiceMockTest {
	/*
	@Mock
	private VU360UserRepository vu360UserRepository;
	 
	@InjectMocks
	private LmsUserDetailsServiceImpl lmsUserDetailService = new LmsUserDetailsServiceImpl();
	
	@Before
    public void init() throws Exception {		
        MockitoAnnotations.initMocks(this);  
    }
	
	 //@Test
	 public void testLoadUserByUsername() throws Exception {
		 System.out.println("testLoadUserByUsername");	
		 
		 // creating mock user object
		 VU360User  user= new VU360User();
		 user.setFirstName("dashboard");
		 user.setLastName("udp");
		 user.setEmailAddress("dashboard@360training.com");	
		 
		 Set<LmsRole> lmsRole=new HashSet<LmsRole>();		 
		 LmsRole roleLearner = new LmsRole();
		 roleLearner.setRoleName("ROLE_LEARNER");
		 roleLearner.setRoleType("LEARNER");
		 roleLearner.setSystemCreatedTf(true);
		 roleLearner.setId(1l);
		 lmsRole.add(roleLearner);		 
		 user.setLmsRoles(lmsRole);
		 
		// set up mocking
		 String userName="dashboard"; 
		 //when (vu360UserRepository.findUserAndRolesByUsername(userName)).thenReturn(user);	
		 when (vu360UserRepository.findUserAndRolesByUsername( anyString())).thenReturn(user);
		 
		 VU360User  mockedUser= lmsUserDetailService.getUserByUsername(userName);
		 
		 // Validate Output
	     assertTrue( mockedUser != null );
	     assertTrue( mockedUser.getFirstName().equals("dashboard"));
	     assertTrue( mockedUser.getLastName().equals("udp")); 
	     assertTrue( mockedUser.getEmailAddress().equals("dashboard@360training.com"));
	     assertTrue( mockedUser.getLmsRoles() != null );
		 
	     //todo remove just for testing
	     System.out.println( mockedUser.getFirstName());
	     System.out.println( mockedUser.getLastName());
	     System.out.println( mockedUser.getEmailAddress());
			     
		 
	 }
*/
}
