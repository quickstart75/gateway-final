package com.softech.ls360.oauth2.auth.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * The WebServiceClientService interface extends ClientDetailsService to clarify that the service always returns WebServiceClient 
 * entities.
 * 
 * @author basit.ahmed
 */
public interface WebServiceClientDetailsService extends ClientDetailsService {

	@Override
	ClientDetails loadClientByClientId(String clientId);
	
}
