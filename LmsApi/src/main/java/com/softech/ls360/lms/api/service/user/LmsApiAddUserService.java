package com.softech.ls360.lms.api.service.user;

import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

public interface LmsApiAddUserService {

	AddUserRequest getAddUserRequest(User user, String customerCode, String apiKey) throws Exception;
	AddUserResponse createUser(AddUserRequest addUserRequest) throws Exception;
	RegisterUser getRegisterUser(AddUserResponse addUserResponse) throws Exception;
	
	
}
