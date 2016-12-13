package com.softech.ls360.oauth2.auth.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * ClientDetails specifies information about an OAuth client that is permitted to access the application. Because clients can access
 * resources on their own behalf (such as for the Client Credentials Grant), ClientDetails includes a Collection of 
 * GrantedAuthoritys that the client holds. It provides the client ID and the client secret, which authenticate clients for 
 * authorization requests and for Client Credentials Grant access token requests. ClientDetails also indicates the set of grants the
 * client is permitted to request, the resources that the client can access (null if unrestricted), and the OAuth scope or scopes 
 * the client is restricted to (null if unrestricted).
 * 
 * @author basit.ahmed
 *
 */
public class WebServiceClientDetails implements ClientDetails {

	private static final long serialVersionUID = 1L;

    private String clientId;
    private String clientSecret;
    private Set<String> scope;
    private Set<String> authorizedGrantTypes;
    private Set<String> registeredRedirectUri;
    private Set<GrantedAuthority> grantedAuthorities;
    private Set<String> resourceIds;
    
	public WebServiceClientDetails(String clientId, String clientSecret, Set<String> scope, Set<String> authorizedGrantTypes, 
			Set<String> registeredRedirectUri, Set<GrantedAuthority> grantedAuthorities, Set<String> resourceIds) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scope = scope;
		this.authorizedGrantTypes = authorizedGrantTypes;
		this.registeredRedirectUri = registeredRedirectUri;
		this.grantedAuthorities = grantedAuthorities;
		this.resourceIds = resourceIds;
	}

	@Override
	public String getClientId() {
		return this.clientId;
	}

	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}
	
	@Override
	public Set<String> getScope() {
		return this.scope;
	}
	
	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return this.authorizedGrantTypes;
	}
	
	@Override
	public Set<String> getRegisteredRedirectUri() {
		return this.registeredRedirectUri;
	}
	
	@Override
	public Integer getAccessTokenValiditySeconds() {
		return 3600;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.grantedAuthorities;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return -1;
	}

	@Override
	public Set<String> getResourceIds() {
		return this.resourceIds;
	}

	@Override
	public boolean isAutoApprove(String arg0) {
		return false;
	}

	@Override
	public boolean isScoped() {
		return true;
	}

	@Override
	public boolean isSecretRequired() {
		return true;
	}

}
