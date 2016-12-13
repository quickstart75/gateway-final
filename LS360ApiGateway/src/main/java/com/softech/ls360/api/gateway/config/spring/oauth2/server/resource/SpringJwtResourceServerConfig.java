package com.softech.ls360.api.gateway.config.spring.oauth2.server.resource;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.softech.ls360.api.gateway.entry.points.JwtResourceServerAuthenticationEntryPoint;
import com.softech.ls360.oauth2.auth.service.spring.jwt.SpringJwtAccessTokenConverter;

/**
 * A Resource Server (can be the same as the Authorization Server or a separate application) serves resources that are protected by 
 * the OAuth2 token. Spring OAuth provides a Spring Security authentication filter that implements this protection. You can switch 
 * it on with @EnableResourceServer on an @Configuration class, and configure it (as necessary) using a ResourceServerConfigurer. 
 * The following features can be configured:

    * tokenServices: the bean that defines the token services (instance of ResourceServerTokenServices).
    * resourceId: the id for the resource (optional, but recommended and will be validated by the auth server if present).
    * other extension points for the resourecs server (e.g. tokenExtractor for extracting the tokens from incoming requests)
    * request matchers for protected resources (defaults to all)
    * access rules for protected resources (defaults to plain "authenticated")
    * other customizations for the protected resources permitted by the HttpSecurity configurer in Spring Security

 * The @EnableResourceServer annotation adds a filter of type OAuth2AuthenticationProcessingFilter automatically to the Spring 
 * Security filter chain. This filter intercepts protected resource requests and then extracts and validates the access token. This 
 * filter is written specifically for bearer tokens and is not easily extended, so you must implement your own filter in this case.
 * 
 * You may want to take advantage of Spring Security's expression-based access control. An expression handler will be registered by 
 * default in the @EnableResourceServer setup. The expressions include #oauth2.clientHasRole, #oauth2.clientHasAnyRole, and 
 * #oath2.denyClient which can be used to provide access based on the role of the oauth client
 * 
 * @author basit.ahmed
 *
 */
@Configuration
@EnableResourceServer
public class SpringJwtResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final Logger log = LogManager.getLogger();
	
	@Inject
	private SpringJwtAccessTokenConverter springJwtAccessTokenConverter;
	
	// The OAuth2AuthenticationEntryPoint does the same thing, except for authentication errors instead of access denied errors.
	@Bean
	public OAuth2AuthenticationEntryPoint oauthAuthenticationEntryPoint() throws Exception {
		return new JwtResourceServerAuthenticationEntryPoint();
	}
			   
	// The OAuth2AccessDeniedHandler handles access denied errors and returning responses with the clientrequested content type.
	@Bean
	public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() throws Exception {
	   
		//new OAuth2AuthenticationProcessingFilter()
		
		return new OAuth2AccessDeniedHandler();
	}
	
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
        converter.setSigningKey("123456");
        return converter;
    }
	
	@Bean
	public CorsFilter corsFilter() {
		
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin(CorsConfiguration.ALL);
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.addAllowedMethod(CorsConfiguration.ALL);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return new CorsFilter(source);
	}
	
	
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
	
	/**
	 * 		<oauth2:resource-server id="resourceServerFilter" resource-id="SUPPORT"
				entry-point-ref="oauthAuthenticationEntryPoint" token-services-ref="tokenServices" />
	 * 
	 * This handles bearer tokens out-of-the-box, but because you’re using custom tokens, you need to configure your nonce services 
	 * and custom resource server filter manually
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenServices())
			.accessDeniedHandler(oauthAccessDeniedHandler())
			.authenticationEntryPoint(oauthAuthenticationEntryPoint());
	}

	@Override
	public void configure(HttpSecurity security) throws Exception {
		
		log.info("Configuring OAuth2Resource Security.");
		
		security
			.antMatcher("/services/**") // only process URLs that begin with /services/
			.authorizeRequests()
	    		//.antMatchers("/services/**").hasAuthority("ROLE_LEARNER")
	    		// Any request simply requires authentication, regardless of permissions.
	    		.anyRequest()
	    		.authenticated()
	    	.and()
	    		.httpBasic()
	    		.authenticationEntryPoint(oauthAuthenticationEntryPoint())
	    	.and()
	    		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    	.and()
	    		.cors()
	    	.and()
	    		.exceptionHandling().accessDeniedHandler(oauthAccessDeniedHandler())
	    	.and()
	    		.csrf()
	    			.disable();	
	}
	
}
