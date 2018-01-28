package com.softech.ls360.lms.api.service.user;

import java.util.Map;

import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.UpdateableUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

public interface LmsApiUserService {

	 AddUserResponse createUser(User user, Long customerId, String customerCode, String apiKey) throws Exception;
	 UpdateUserResponse updateUser(UpdateableUser updateableUser, Long customerId, String customerCode, String apiKey) throws Exception;
	 Map<String, String> createUser(User user, Long customerId,String token) throws Exception;
	
}
