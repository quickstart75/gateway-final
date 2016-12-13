package com.softech.ls360.lms.api.service.user;

import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.UpdateableUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

public interface LmsApiUpdateUserService {

	UpdateUserRequest getUpdateUserRequest(UpdateableUser updateableUser, String customerCode, String apiKey) throws Exception;
	UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) throws Exception;
	RegisterUser getRegisterUser(UpdateUserResponse updateUserResponse) throws Exception;
	UpdateableUser getUpdateableUser(User user) throws Exception;
	
}
