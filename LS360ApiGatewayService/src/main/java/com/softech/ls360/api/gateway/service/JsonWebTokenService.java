package com.softech.ls360.api.gateway.service;

public interface JsonWebTokenService {

	String getToken(String oauth2TokenEndPoint, String oauth2ClientUsername, String oauth2ClientPassword, String userName, String password, String grantType) throws Exception;
	
}
