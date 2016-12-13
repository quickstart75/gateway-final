package com.softech.ls360.lms.api.service.impl.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.api.service.user.LmsApiRegisterUserService;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.RegisterOrganizationalGroup;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.RegisterOrganizationalGroups;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUser;

@Service
public class LmsApiRegisterUserServiceImpl implements LmsApiRegisterUserService {

	@Override
	public boolean isSuccessfulRegisterUser(RegisterUser registerUser) {
		
		boolean successful = false;
		
		if (registerUser == null) {
			return successful;
		}
		
		String userErrorCode = registerUser.getErrorCode();
		if(userErrorCode.equals("0")) {
			successful = true;
		}
		
		return successful;
	}

	@Override
	public List<RegisterOrganizationalGroup> getRegisterUserFailedOrganizationalGroupsList(RegisterUser registerUser) {
		
		if (registerUser == null) {
			return null;
		}
		
		RegisterOrganizationalGroups registerOrganizationalGroups = registerUser.getRegisterOrganizationalGroups();
		if (registerOrganizationalGroups == null) {
			return null;
		}
		
		List<RegisterOrganizationalGroup> failedOrganizationalGroupsList = null;
		List<RegisterOrganizationalGroup> registeredOrganizationalGroupList = registerOrganizationalGroups.getRegisterOrganizationalGroup();
		if (!CollectionUtils.isEmpty(registeredOrganizationalGroupList)) {
			failedOrganizationalGroupsList = registeredOrganizationalGroupList.stream()
			.filter(registeredOrganizationalGroup -> !(registeredOrganizationalGroup.getErrorCode().equals("0")))
			.collect(Collectors.toList());
			
		}
		return failedOrganizationalGroupsList;
	}

}
