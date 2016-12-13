package com.softech.ls360.lms.api.service.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.softech.ls360.lms.api.service.user.LmsApiUpdateUserService;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUsers;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.UpdateableUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.UpdateableUsers;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

@Service
public class LmsApiUpdateUserServiceImpl implements LmsApiUpdateUserService {

	@Inject
    protected WebServiceTemplate lmsApiWebServiceTemplate;
	
	@Override
	public UpdateUserRequest getUpdateUserRequest( UpdateableUser updateableUser, String customerCode, String apiKey) throws Exception {
		
		List<UpdateableUser> updateableUserList = new ArrayList<>();
		updateableUserList.add(updateableUser);
		
		UpdateableUsers updateableUsers = new UpdateableUsers();
		updateableUsers.setUpdateableUser(updateableUserList);
		
		UpdateUserRequest updateUserRequest = new UpdateUserRequest();
		updateUserRequest.setCustomerCode(customerCode);
		updateUserRequest.setKey(apiKey);
		updateUserRequest.setUsers(updateableUsers);
		
		return updateUserRequest;
		
	}

	@Override
	public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) throws Exception {
		
		UpdateUserResponse updateUserResponse = null;
		Object response = lmsApiWebServiceTemplate.marshalSendAndReceive(updateUserRequest);
		if (response instanceof UpdateUserResponse) {
			updateUserResponse = (UpdateUserResponse)response;
		}
		return updateUserResponse;
	}
	
	@Override
	public RegisterUser getRegisterUser(UpdateUserResponse updateUserResponse) throws Exception {
		
		RegisterUsers registerUsers = updateUserResponse.getRegisterUsers();
		if (registerUsers == null) {
			return null;
		}
		
		RegisterUser user = null;
		List<RegisterUser> registerUserList = registerUsers.getRegisterUser();
		if (!CollectionUtils.isEmpty(registerUserList)) {
			user = registerUserList.get(0);
		}
		return user;
	}
	
	@Override
	public UpdateableUser getUpdateableUser(User user) throws Exception {

		if (user == null) {
			throw new Exception("User is null. Can not get UpdateableUser from User.");
		}
		
		UpdateableUser updateableUser = new UpdateableUser();
		updateableUser.setFirstName(user.getFirstName());
		updateableUser.setMiddleName(user.getMiddleName());
		updateableUser.setLastName(user.getLastName());
		updateableUser.setEmailAddress(user.getEmailAddress());
		updateableUser.setPhone(user.getPhone());
		updateableUser.setMobilePhone(user.getMobilePhone());
		updateableUser.setExtension(user.getExtension());
		updateableUser.setAddress(user.getAddress());
		updateableUser.setAlternateAddress(user.getAlternateAddress());
		updateableUser.setUserName(user.getUserName());
		updateableUser.setPassword(user.getPassword());
		updateableUser.setOrganizationalGroups(user.getOrganizationalGroups());
		updateableUser.setAccountLocked(user.isAccountLocked());
		updateableUser.setAccountExpired(user.isAccountExpired());
		updateableUser.setAccountDisabled(user.isAccountDisabled());
		updateableUser.setVisibleOnReport(user.isVisibleOnReport());
		updateableUser.setExpirationDate(user.getExpirationDate());
		updateableUser.setChangePasswordOnNextLogin(user.isChangePasswordOnNextLogin());
		
		return updateableUser;
	}
	
}
