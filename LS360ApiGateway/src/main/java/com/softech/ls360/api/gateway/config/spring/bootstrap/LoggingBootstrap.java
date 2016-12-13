package com.softech.ls360.api.gateway.config.spring.bootstrap;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import com.softech.ls360.api.gateway.filter.PostSecurityLoggingFilter;

@Order(3)
public class LoggingBootstrap implements WebApplicationInitializer {
	
	private static final Logger log = LogManager.getLogger();

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		log.info("Executing logging bootstrap.");

		FilterRegistration.Dynamic registration = container.addFilter("postSecurityLoggingFilter", new PostSecurityLoggingFilter());
		registration.addMappingForUrlPatterns(null, false, "/*");
	}
}
