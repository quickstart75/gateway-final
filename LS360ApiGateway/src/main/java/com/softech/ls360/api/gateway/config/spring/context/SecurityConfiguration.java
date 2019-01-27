package com.softech.ls360.api.gateway.config.spring.context;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.softech.ls360.api.gateway.config.spring.oauth2.server.authorization.SpringJwtAuthorizationServerConfig;
import com.softech.ls360.api.gateway.config.spring.oauth2.server.resource.SpringJwtResourceServerConfig;
import com.softech.ls360.api.gateway.filter.LS360ApiGatewayFilterMarker;
import com.softech.ls360.api.gateway.service.LmsUserDetailsService;
import com.softech.ls360.oauth2.auth.config.spring.jwt.SpringJwtAppConfig;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true, 
    order = 0, 
    mode = AdviceMode.PROXY,
    proxyTargetClass = false
)
@ComponentScan(
		basePackageClasses = {LS360ApiGatewayFilterMarker.class}      
)
@Import({SpringJwtAppConfig.class, SpringJwtAuthorizationServerConfig.class, SpringJwtResourceServerConfig.class})
public class SecurityConfiguration {
	
	private static final Logger log = LogManager.getLogger();
	
	@Configuration
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
		@Inject 
		private LmsUserDetailsService lmsUserDetailsService;
	
	    @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
		
		@Bean
	    protected SessionRegistry sessionRegistryImpl() {
	        return new SessionRegistryImpl();
	    }
		
		@Override
		protected void configure(AuthenticationManagerBuilder builder) throws Exception {
			
			ReflectionSaltSource saltSource = new ReflectionSaltSource();
	        saltSource.setUserPropertyToUse("userGuid");
			
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setSaltSource(saltSource);
	        
	        provider.setUserDetailsService(this.lmsUserDetailsService);
	        provider.setPasswordEncoder(new ShaPasswordEncoder());
	        
	        builder.authenticationProvider(provider).eraseCredentials(false);
	        
		}
		
		@Override
		public void configure(WebSecurity security) {
			security.ignoring().antMatchers("/resource/**", "/header/size/**", "/services/rest/reports/**");
		}
		
		@Override
		protected void configure(HttpSecurity security) throws Exception {
			
			log.info("Configuring Web Security.");
			
			security
				.authorizeRequests()
					.antMatchers("/sso/cas/lf/**").hasAuthority("ROLE_ANONYMOUS")
				    .antMatchers("/login/**").permitAll()
				    .antMatchers("/login").permitAll()
				    .antMatchers("/logout").permitAll()
				    
					// Any request simply requires authentication, regardless of permissions.
					.anyRequest()
					.authenticated()
				.and()
					.formLogin()
						.loginPage("/login")
						.failureUrl("/login?loginFailed")
						.defaultSuccessUrl("/dashboard/ui")
						.usernameParameter("username")
						.passwordParameter("password")
						.permitAll()
				.and()
					.logout()
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?loggedOut")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.permitAll()
				.and()
					.sessionManagement()
						.sessionFixation()
						.changeSessionId()
						.maximumSessions(1)
						.maxSessionsPreventsLogin(true)
						.sessionRegistry(this.sessionRegistryImpl())
					.and()
				.and()
					.csrf()
						.requireCsrfProtectionMatcher((r) -> {
							String m = r.getMethod();
							String servletPath = r.getServletPath();
							return !(servletPath.startsWith("/services/") || servletPath.startsWith("/restful/")) && ("POST".equals(m) || "PUT".equals(m) ||
                                "DELETE".equals(m) || "PATCH".equals(m));
						});		
		}	
		
	} //end of class FormLoginWebSecurityConfigurerAdapter
	
}
