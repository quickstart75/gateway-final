package com.softech.ls360.api.gateway.exception.restful;

import org.springframework.security.core.AuthenticationException;

public class RestAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMessage;
	
	public RestAuthenticationException(String errorCode, String errorMessage) {
		super(errorCode);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
