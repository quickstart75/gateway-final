package com.softech.ls360.oauth2.auth.service.impl.spring.jwt;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.WebServiceClient;
import com.softech.ls360.lms.repository.repositories.WebServiceClientRepository;
import com.softech.ls360.oauth2.auth.model.WebServiceClientDetails;
import com.softech.ls360.oauth2.auth.service.WebServiceClientDetailsService;

/**
 * The WebServiceClientService interface extends ClientDetailsService to clarify that the service always returns WebServiceClient 
 * entities, and the implementation is straightforward. Notice that loadClientByClientId never returns null. If a client cannot be 
 * found, it throws Spring Security OAuth’s ClientRegistrationException to indicate that the client is not valid. Also notice that 
 * the service does not provide any mechanism for registering new clients. (Likewise, there is no UI for doing so.) How this happens
 * varies greatly from one business case to another
 * 
 * The ClientDetailsService enables you to load a ClientDetails instance by client ID. Whenever a client authenticates, Spring 
 * Security OAuth loads the client using the ClientDetailsService and compares the client secret to the one in the HTTP basic 
 * Authorization header. The authorization server component of Spring Security OAuth requires a UserDetailsService to function 
 * properly. The two default implementations of this service are in the same package.

	1) InMemoryClientDetailsService is configured on its startup with all the clients your application supports.
	2) JdbcClientDetailsService uses a hard-coded table definition for storing and retrieving client details.
 
 * Because clients authenticate using HTTP basic authentication, which Spring Security already supports, you configure a standard 
 * authentication manager with a DaoAuthenticationProvider and an 
 * org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService.
 * 
 * @author basit.ahmed
 *
 */
@Service
public class WebServiceClientDetailsServiceImpl implements WebServiceClientDetailsService {

	@Inject 
	private WebServiceClientRepository webServiceClientRepository;
	
	/**
	 * loadClientByClientId never returns null. If a client cannot be found, it throws Spring Security OAuth’s 
	 * ClientRegistrationException to indicate that the client is not valid. Also notice that the service does not provide any 
	 * mechanism for registering new clients. (Likewise, there is no UI for doing so.)
	 */
	@Override
	@Transactional
	public ClientDetails loadClientByClientId(String clientId) {
		
		WebServiceClient client = webServiceClientRepository.getByClientId(clientId);
		if(client == null) {
			throw new ClientRegistrationException("Client not found");
		}
		
		String clientName = client.getClientId();
		String clientSecret = client.getClientSecret();
		Set<String> scope = client.getScope();
		Set<String> authorizedGrantTypes = client.getAuthorizedGrantTypes();
		Set<String> registeredRedirectUri = client.getRegisteredRedirectUri();
		
		Set<String> resourceIds = new HashSet<>();
		//resourceIds.add("10");
      
        Set<String> roles = new HashSet<>();
        roles.add("OAUTH_CLIENT");
        
        Set<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(roles);
        ClientDetails clientDetails = new WebServiceClientDetails(clientName, clientSecret, scope, authorizedGrantTypes, registeredRedirectUri, grantedAuthorities, resourceIds);
		
		return clientDetails;
	}
	
	private Set<GrantedAuthority> getGrantedAuthorities(Set<String> authorityRole) {
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
		for(String role :  authorityRole) {
			GrantedAuthority authority = getGrantedAuthority(role);
			grantedAuthorities.add(authority);
		}
		
		return grantedAuthorities;
	}
	
	private GrantedAuthority getGrantedAuthority(String role) {
		GrantedAuthority authority = new SimpleGrantedAuthority(role);
		return authority;
	}

}
