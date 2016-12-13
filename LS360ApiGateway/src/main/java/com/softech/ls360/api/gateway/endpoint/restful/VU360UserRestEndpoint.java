package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.model.LmsUserPrincipal;
import com.softech.ls360.lms.api.model.request.user.UpdateUserRequest;
import com.softech.ls360.lms.api.model.request.user.UserRequest;
import com.softech.ls360.lms.api.service.user.LmsApiUserService;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.transactionresult.TransactionResultType;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.UpdateableUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

@RestEndpoint
@RequestMapping(value="/lms/customer/")
public class VU360UserRestEndpoint {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LmsApiUserService lmsApiUserService;
	
	@RequestMapping(value = "user/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<AddUserResponse> addUser(@RequestBody @Valid UserRequest user,
			@AuthenticationPrincipal OAuth2Authentication authentication) throws Exception {
	
		logger.info("Request received at " + getClass().getName() + " for add user");
	
		Authentication userAuthentication = authentication.getUserAuthentication();
		if (userAuthentication != null) {
			Collection<? extends GrantedAuthority> lmsUserGrantedAuthorities = userAuthentication.getAuthorities();
			String lmsUserName = userAuthentication.getName();
			logger.info("");
		}
		
		OAuth2Request oauth2Request = authentication.getOAuth2Request();
		
		if (oauth2Request != null) {
			String clientId = oauth2Request.getClientId();
			String grantType = oauth2Request.getGrantType();
			String redirectUri = oauth2Request.getRedirectUri();
			Set<String> resourceIds = oauth2Request.getResourceIds();
			Set<String> responseTypes = oauth2Request.getResponseTypes();
			Set<String> scope = oauth2Request.getScope();
			Map<String, String> requestParameters = oauth2Request.getRequestParameters();
			Collection<? extends GrantedAuthority> grantedAuthorities = oauth2Request.getAuthorities();
			
			logger.info("");
		}
		
		//Long customerId = principal.getCustomerId();
		//String customerCode = principal.getCustomerCode();
		Long customerId = 1L;
		String customerCode = "VUCUS-1";
		User lmsApiUser = user.getUser();
		AddUserResponse addUserResponse = new AddUserResponse();
		addUserResponse.setTransactionResult(TransactionResultType.SUCCESS);
		addUserResponse.setTransactionResultMessage("");
		addUserResponse.setRegisterUsers(null);
		//addUserResponse = lmsApiUserService.createUser(lmsApiUser, customerId, customerCode, clientApiKey);
		return new ResponseEntity<AddUserResponse>(addUserResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "user/update", method = RequestMethod.PUT)
	@ResponseBody
	public UpdateUserResponse updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest,
			@AuthenticationPrincipal LmsUserPrincipal principal) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for update user");
		
		Long customerId = 1L;
		String customerCode = "VUCUS-1";
		UpdateableUser lmsApiUser = updateUserRequest.getUpdateableUser();
		UpdateUserResponse updateUserResponse = null;
		//updateUserResponse = lmsApiUserService.updateUser(lmsApiUser, customerId, customerCode, clientApiKey);
		return updateUserResponse;
	
	}
	
}
