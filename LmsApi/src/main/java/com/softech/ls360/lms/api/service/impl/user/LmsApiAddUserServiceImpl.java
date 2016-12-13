package com.softech.ls360.lms.api.service.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.softech.ls360.lms.api.service.user.LmsApiAddUserService;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUsers;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.Users;

@Service
public class LmsApiAddUserServiceImpl implements LmsApiAddUserService {

	@Inject
    protected WebServiceTemplate lmsApiWebServiceTemplate;
	
	@Override
	public AddUserRequest getAddUserRequest(User user, String customerCode, String apiKey) throws Exception {
		
		List<User> userList = new ArrayList<>();
		userList.add(user);
		Users users = new Users();
		users.setUser(userList);
		
		AddUserRequest addUserRequest = new AddUserRequest();
		addUserRequest.setCustomerCode(customerCode);
		addUserRequest.setKey(apiKey);
		addUserRequest.setUsers(users);
		
		return addUserRequest;
	}

	@Override
	public AddUserResponse createUser(AddUserRequest addUserRequest) throws Exception {
		AddUserResponse addUserResponse = null;
		Object response = lmsApiWebServiceTemplate.marshalSendAndReceive(addUserRequest);
		if (response instanceof AddUserResponse) {
			addUserResponse = (AddUserResponse)response;
		}
		return addUserResponse;
	}
	
	@Override
	public RegisterUser getRegisterUser(AddUserResponse addUserResponse) throws Exception {
		
		RegisterUser user = null;
		
		RegisterUsers registerUsers = addUserResponse.getRegisterUsers();
		if (registerUsers == null) {
			return null;
		}
		
		List<RegisterUser> registerUserList = registerUsers.getRegisterUser();
		
		if (!CollectionUtils.isEmpty(registerUserList)) {
			user = registerUserList.get(0);
		}
		return user;
	}
	
}
