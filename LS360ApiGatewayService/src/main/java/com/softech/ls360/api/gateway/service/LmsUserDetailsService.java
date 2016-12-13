package com.softech.ls360.api.gateway.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * You have defined your interface’s method validation contract, you need to tell Spring’s MethodValidationPostProcessor to actually
 * apply validation to executions of the method. You have a couple of options here. You can either use the standard 
 * @ValidateOnExecution annotation or Spring’s @Validated annotation. Each has its advantages and disadvantages. 
 * @ValidateOnExecution is more granular because you can annotate individual methods as well as an interface (to apply to all its 
 * methods), whereas you can use @Validated only on a class or interface. On the other hand, you can use @Validated on method 
 * parameters, but you cannot use @ValidateOnExecution on method parameters.
 * 
 * UserPrincipal to implement Authentication. Thus we can return it from AuthenticationProvider implementation. It still implements 
 * Principal and Serializable, but indirectly by way of implementing Authentication. The only major change is the addition of the 
 * getAuthorities, getPrincipal, getDetails, getCredentials, isAuthenticated, and setAuthenticated methods specified in 
 * Authentication. Their implementation is boilerplate. 
 * 
 * AuthenticationService now extends AuthenticationProvider, overriding its authenticate method to clarify that this provider returns
 * only UserPrincipals.
 * 
 * @author basit.ahmed
 *
 */
public interface LmsUserDetailsService extends UserDetailsService {

	@Override
	UserDetails loadUserByUsername(String username);
	
}
