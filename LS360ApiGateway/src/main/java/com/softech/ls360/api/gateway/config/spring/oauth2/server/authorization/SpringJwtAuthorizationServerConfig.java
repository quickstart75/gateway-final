package com.softech.ls360.api.gateway.config.spring.oauth2.server.authorization;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.softech.ls360.oauth2.auth.service.WebServiceClientDetailsService;
import com.softech.ls360.oauth2.auth.service.spring.jwt.SpringJwtAccessTokenConverter;
import com.softech.ls360.oauth2.auth.service.spring.jwt.SpringJwtAccessTokenEnhancer;

/**
 * When a client needs to access one of your protected resources but does not have an access token, it redirects the resource owner 
 * to your authorization endpoint. Spring Security OAuth’s authorization server hosts this authorization endpoint. It presents an 
 * approval screen, processes its submission, generates the authorization code, and redirects the resource owner back to the client 
 * application. It also responds to token endpoint requests to exchange authorization codes for access tokens, as well as to exchange
 * client credentials and resource owner credentials for access tokens. 
 * 
 * When configuring the authorization server, you must also configure Spring Security to protect the authorization endpoint. This 
 * way, Spring Security requires the user to authenticate and have the necessary authority for accessing the authorization endpoint.
 * (Spring Security OAuth already protects the token endpoint with HTTP basic authentication using the client credentials.)
 * 
 * @author basit.ahmed
 *
 */
@Configuration
@EnableAuthorizationServer
public class SpringJwtAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Inject
	private AuthenticationManager authenticationManager;
	
	@Inject
	private WebServiceClientDetailsService webServiceClientDetailsService;

	@Inject
	private SpringJwtAccessTokenEnhancer springJwtAccessTokenEnhancer;
	
	@Inject
	private SpringJwtAccessTokenConverter springJwtAccessTokenConverter;
	
	/**
	 * To use JWT tokens you need a JwtTokenStore in your Authorization Server. The Resource Server also needs to be 
	 * able to decode the tokens so the JwtTokenStore has a dependency on a JwtAccessTokenConverter, and the same implementation
	 * is needed by both the Authorization Server and the Resource Server. The tokens are signed by default, and the Resource 
	 * Server also has to be able to verify the signature, so it either needs the same symmetric (signing) key as the 
	 * Authorization Server (shared secret, or symmetric key), or it needs the public key (verifier key) that matches the private
	 * key (signing key) in the Authorization Server (public-private or asymmetric key). The public key (if available) is 
	 * exposed by the Authorization Server on the /oauth/token_key endpoint, which is secure by default with access rule 
	 * "denyAll()". You can open it up by injecting a standard SpEL expression into the AuthorizationServerSecurityConfigurer 
	 * (e.g. "permitAll()" is probably adequate since it is a public key).
	 * 
	 * To use the JwtTokenStore you need "spring-security-jwt" on your classpath (you can find it in the same github repository
	 * as Spring OAuth but with a different release cycle).
	 * 
	 * @return
	 */
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
	
	@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
		
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(springJwtAccessTokenConverter);
        return converter;
    }
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() throws Exception {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * defines the security constraints on the token endpoint.
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder());
	}

	/**
	 * a configurer that defines the client details service. Client details can be initialized, or you can just refer to an 
	 * existing store. The ClientDetailsServiceConfigurer (a callback from your AuthorizationServerConfigurer) can be used to define 
	 * an in-memory or JDBC implementation of the client details service. Important attributes of a client are
	 * 
	 * 	clientId: (required) the client id.
	 * 	secret: (required for trusted clients) the client secret, if any.
	 * 	scope: The scope to which the client is limited. If scope is undefined or empty (the default) the client is not limited by 
	 *         scope.
	 *         
	 *  authorizedGrantTypes: Grant types that are authorized for the client to use. Default value is empty.
	 *  authorities: Authorities that are granted to the client (regular Spring Security authorities).
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(webServiceClientDetailsService);
	}

	/**
	 * defines the authorization and token endpoints and the token services.
	 * 
	 * 	<oauth2:authorization-server token-services-ref="tokenServices"
                client-details-service-ref="webServiceClientService"
                user-approval-page="oauth/authorize" error-page="oauth/error">
            <oauth2:authorization-code />
        </oauth2:authorization-server>
        
     * The <oauth2:authorization-server> element is an important part of the OAuth configuration. It sets up the authorization 
     * endpoint (/oauth/authorize relative to your application root) and access token endpoint (/oauth/token) and handles requests 
     * to those URLs. The configured authorization server even handles responding to these requests automatically with an HTML 
     * authorization form. However, this form is very generic and obviously won’t be styled to match your site. In most cases, 
     * therefore, you’ll want to replace it. The user-approval-page attribute enables you to specify a Spring MVC view name for 
     * the form that displays the authorization request, whereas the error-page attribute enables you to specify a view name for 
     * the error page displayed when an authorization error occurs.
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		endpoints
			.tokenStore(tokenStore())
				.tokenServices(tokenServices())
				.authenticationManager(authenticationManager);
	}
	
	public DefaultTokenServices tokenServices() {
	    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	    defaultTokenServices.setTokenStore(tokenStore());
	    defaultTokenServices.setTokenEnhancer(springJwtAccessTokenEnhancer);
	    return defaultTokenServices;
	}

}
