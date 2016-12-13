package com.softech.ls360.api.gateway.test;

import java.util.Arrays;
import java.util.List;

import com.softech.vu360.lms.webservice.message.lmsapi.types.address.Address;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.OrganizationalGroups;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

public class LmsApiUser {
	
	public static User getUser(String firstName, String middleName, String lastName, String emailAddress, String phone, 
			String mobilePhone, String extension, Address address, Address alternateAddress, String userName,
			String password, OrganizationalGroups organizationalGroup) {
		
		User user = new User();
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setEmailAddress(emailAddress);
		user.setPhone(phone);
		user.setMobilePhone(mobilePhone);
		user.setExtension(extension);
		user.setAddress(address);
		user.setAlternateAddress(alternateAddress);
		user.setUserName(userName);
		user.setPassword(password);
		user.setOrganizationalGroups(organizationalGroup);
		
		return user;
		
	}
	
	public static User getMinimumUser(String firstName, String lastName, String emailAddress, String userName,
			String password) {
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmailAddress(emailAddress);
		user.setUserName(userName);
		user.setPassword(password);
		return user;
		
	}
	
	public static Address getUserAddress(String streetAddress1, String streetAddress2, String city, String state, String zipCode, String country) {
		
		Address userAddress = new Address();
		userAddress.setStreetAddress1(streetAddress1);
		userAddress.setStreetAddress2(streetAddress2);
		userAddress.setCity(city);
		userAddress.setState(state);
		userAddress.setZipCode(zipCode);
		userAddress.setCountry(country);
		
		return userAddress;
		
	}
	
	public static OrganizationalGroups getOrganizationalGroup(String[] orgGroupHierarchieArray) {
		
		List<String> orgGroupHierarchies = Arrays.asList(orgGroupHierarchieArray);
		
		OrganizationalGroups organizationalGroup = new OrganizationalGroups();
		organizationalGroup.setOrgGroupHierarchy(orgGroupHierarchies);
		
		return organizationalGroup;
		
	}
	
}
