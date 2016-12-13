package com.softech.ls360.api.gateway.exception.restful;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ws.client.WebServiceIOException;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpointAdvice;

/**
 * Spring 3.2 introduced the concept of the controller advice pattern with the 
 * @org.springframework.web.bind.annotation.ControllerAdvice annotation. Classes marked with @ControllerAdvice, which is a 
 * @Component, are Spring-managed beans that advise controllers. At this time you can add @ExceptionHandler, @InitBinder, and 
 * @ModelAttribute methods to a controller advice class, and the advice those methods supply apply to all controllers. Being a 
 * @Component, @ControllerAdvice classes would normally get automatically instantiated in your root application context. You don’t 
 * want this because you don’t want such advice to apply to web controllers and endpoint controllers alike, so you need to exclude 
 * this annotation from your root component scanning.
 * 
 * Before you can create controller advice for your endpoint controllers, consider that you might want to also create advice for 
 * your web controllers at some point. So, instead of using @ControllerAdvice directly, it would be best to create your own 
 * stereotype annotation specific to RESTful endpoint controllers.
 * 
 * The last thing you need to do is configure the component scanning for the REST application context to make it pick up 
 * @RestEndpointAdvice classes.
 * 
 * Now you can create a controller advice to handle errors. It has two inner classes, ErrorItem and ErrorResponse, which help it 
 * achieve the goal of cleanly reporting errors back to the user.
 * 
 * @author basit.ahmed
 *
 */
@RestEndpointAdvice
public class RestExceptionHandler {
	
	private static final Logger logger = LogManager.getLogger();
	
	private static final int HTTP_STATUS_BAD_REQUEST = HttpServletResponse.SC_BAD_REQUEST;
	private static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	
	/**
	 * Another thing to consider is the problem of bean validation. In Web controllers, you can caught ConstraintViolationExceptions 
	 * and then added the individual violations to your model to display to the user. It would be great if there was an easy way to 
	 * do something similar in your rest endpoint controllers. The @org.springframework.web.bind.annotation.ExceptionHandler 
	 * annotation, also added in Spring 3.0, enables you to mark a method as an exception handler. It has nearly the same semantics 
	 * as @RequestMapping, enabling you to return most of the same return types and accept most of the same method arguments.
	 * 
	 * The lone handle method, annotated with @ExceptionHandler, converts all the constraint violations into ErrorItems, places them
	 * in an ErrorResponse, and returns the ErrorResponse wrapped in a ResponseEntity with the status code set to 400 Bad Request.
	 * 
	 * Notice how the @ExceptionHandler annotation indicates which exceptions the method is capable of handling. You could put 
	 * multiple exceptions in this annotation to have a single method handle multiple errors. Because Spring always calls the most 
	 * specific match, you could also create a catchall handle method that took any instance of Exception. Spring would call the 
	 * method that most closely matched the thrown exception and then only call your catchall method as a last resort.
	 * 
	 * @param e
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handle(ConstraintViolationException e) {
		ErrorResponse errors = new ErrorResponse();
		for (ConstraintViolation violation : e.getConstraintViolations()) {
			ErrorItem error = new ErrorItem();
			String errorMessage = violation.getMessage();
			error.setStatus(HTTP_STATUS_BAD_REQUEST);
			error.setCode(violation.getMessageTemplate());
			error.setMessage(errorMessage);
			errors.addError(error);
		}

		logger.error("ConstraintViolationException occured: ");
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
	    
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
	    List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
	 
	    ErrorResponse errors = new ErrorResponse();
	    
	    for (FieldError fieldError : fieldErrors) {
	    	ErrorItem error = new ErrorItem();
	    	error.setStatus(HTTP_STATUS_BAD_REQUEST);
	    	error.setCode(fieldError.getField());
	    	error.setMessage(fieldError.getDefaultMessage());
	        errors.addError(error);
	    }
	    
	    for (ObjectError objectError : globalErrors) {
	    	ErrorItem error = new ErrorItem();
	    	error.setStatus(HTTP_STATUS_BAD_REQUEST);
	    	error.setCode(objectError.getObjectName());
	    	error.setMessage(objectError.getDefaultMessage());
	    	errors.addError(error);
	
	    }
	    
	    logger.error("MethodArgumentNotValidException occured: ");
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
   
	@ExceptionHandler(RestException.class)
	public ResponseEntity<ErrorResponse> handleRestException(RestException e) {
		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem();
		String errorCode = e.getErrorCode();
		String errorMessage = e.getErrorMessage();
		error.setStatus(HTTP_STATUS_BAD_REQUEST);
		error.setCode(errorCode);
		error.setMessage(errorMessage);
		errors.addError(error);

		logger.error("RestException occured: " + errorMessage);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(JpaSystemException.class)
	public ResponseEntity<ErrorResponse> handleJpasSystemException(JpaSystemException e) {
		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem();
		String errorCode = "error.database";
		String errorMessage = e.getMessage();
		error.setStatus(HTTP_STATUS_INTERNAL_SERVER_ERROR);
		error.setCode(errorCode);
		error.setMessage(errorMessage);
		errors.addError(error);

		logger.error("JpaSystemException occured: " + errorMessage);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(WebServiceIOException.class)
	public ResponseEntity<ErrorResponse> handleConnectException(WebServiceIOException e) {
		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem();
		String errorCode = "error.connection.webservice";
		String errorMessage = e.getMessage();
		error.setStatus(HTTP_STATUS_INTERNAL_SERVER_ERROR);
		error.setCode(errorCode);
		error.setMessage(errorMessage);
		errors.addError(error);

		logger.error("WebServiceIOException occured: " + errorMessage);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleConnectException(HttpMessageNotReadableException e) {
		ErrorResponse errors = new ErrorResponse();
		ErrorItem error = new ErrorItem();
		String errorCode = "error.request.mapping";
		String errorMessage = e.getMessage();
		error.setStatus(HTTP_STATUS_BAD_REQUEST);
		error.setCode(errorCode);
		error.setMessage(errorMessage);
		errors.addError(error);

		logger.error("HttpMessageNotReadableException occured: " + errorMessage);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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
