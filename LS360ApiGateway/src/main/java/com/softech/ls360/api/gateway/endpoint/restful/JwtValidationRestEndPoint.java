package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.model.response.JwtValidationResponse;

@RestEndpoint
@RequestMapping(value="/jwt")
public class JwtValidationRestEndPoint {

	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<JwtValidationResponse> validateJwt(@AuthenticationPrincipal OAuth2Authentication authentication) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for JsonWebToken(Jwt) Validation");
		
		JwtValidationResponse jwtValidationResponse = new JwtValidationResponse();
		
		Authentication userAuthentication = authentication.getUserAuthentication();
		if (userAuthentication != null) {
			Collection<? extends GrantedAuthority> lmsUserGrantedAuthorities = userAuthentication.getAuthorities();
			String lmsUserName = userAuthentication.getName();
			jwtValidationResponse.setUserName(lmsUserName);
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
		
		return new ResponseEntity<JwtValidationResponse>(jwtValidationResponse, HttpStatus.OK);
	}
	
}
