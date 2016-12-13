package com.softech.ls360.api.gateway.exception.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.softech.ls360.api.gateway.config.spring.annotation.WebControllerAdvice;

@WebControllerAdvice
public class WebControllerExceptionHandler {

	private static final Logger logger = LogManager.getLogger();
	
	@ExceptionHandler(OAuth2Exception.class)
	public ResponseEntity<ErrorResponse> handleDashboardException(OAuth2Exception e) {
		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem();
		String errorCode = "error.invalid.client";
		String errorMessage = e.getMessage();
		error.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		error.setCode(errorCode);
		error.setMessage(errorMessage);
		errors.addError(error);

		logger.error("InvalidClientException occured: " + errorMessage);
		return new ResponseEntity<ErrorResponse>(errors, HttpStatus.UNAUTHORIZED);
	}
	
	@XmlRootElement(name = "error")
	public static class ErrorItem {
		
		private int status;
		private String code;
		private String message;

		@XmlAttribute
		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		@XmlAttribute
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		@XmlValue
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	@XmlRootElement(name = "errors")
	public static class ErrorResponse {
		
		private List<ErrorItem> errors = new ArrayList<>();

		@XmlElement(name = "error")
		public List<ErrorItem> getErrors() {
			return errors;
		}

		public void setErrors(List<ErrorItem> errors) {
			this.errors = errors;
		}

		public void addError(ErrorItem error) {
			this.errors.add(error);
		}
	}
	
}
