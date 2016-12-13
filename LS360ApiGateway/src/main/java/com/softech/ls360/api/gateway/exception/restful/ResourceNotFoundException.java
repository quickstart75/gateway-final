package com.softech.ls360.api.gateway.exception.restful;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * One of the things you need to handle in your web services is requests to resources that don’t exist. Sure, Spring can return 
 * 404 Not Found when requests are made to URLs without method mappings, but what if a request is made to 
 * /services/Rest/account/10 and the account with identifier 10 doesn’t exist. According to the RESTful standards, you’re also 
 * suppose to return 404 Not Found. In the earliest days you simply had to use the HttpServletRequest everywhere to set the response
 * status. Now it is much simpler. From a coding standpoint, the easiest way to report such an issue is to throw an exception. So, 
 * create an exception.
 * 
 * The only thing special about this exception is the @org.springframework.web.bind.annotation.ResponseStatus annotation, added in 
 * Spring 3.0. When placed on an exception declaration, this tells Spring Framework to return a 404 Not Found status code. Without 
 * this, throwing such an exception from a controller method would normally result in a 500 Internal Server Error.
 * 
 * @author basit.ahmed
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
