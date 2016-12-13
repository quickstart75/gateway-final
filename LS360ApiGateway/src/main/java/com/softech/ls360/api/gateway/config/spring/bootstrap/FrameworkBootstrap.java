package com.softech.ls360.api.gateway.config.spring.bootstrap;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.web.Log4jServletContainerInitializer;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import com.softech.ls360.api.gateway.config.spring.context.RestServletContextConfiguration;
import com.softech.ls360.api.gateway.config.spring.context.RootContextConfiguration;
import com.softech.ls360.api.gateway.config.spring.context.SoapServletContextConfiguration;
import com.softech.ls360.api.gateway.config.spring.context.WebServletContextConfiguration;
import com.softech.ls360.api.gateway.filter.PreSecurityLoggingFilter;

@Order(1)
public class FrameworkBootstrap extends Log4jServletContainerInitializer implements WebApplicationInitializer {

	private static final Logger log = LogManager.getLogger();
	
	@Override
    public void onStartup(ServletContext container) throws ServletException {
        
		super.onStartup(null, container);
		
		log.info("Executing framework bootstrap.");
		
		container.getServletRegistration("default").addMapping("/resource/*");

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);
        
        container.addListener(new ContextLoaderListener(rootContext));
        container.addListener(new RequestContextListener());

        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebServletContextConfiguration.class);
        
        ServletRegistration.Dynamic dispatcher = container.addServlet("springWebDispatcher", new DispatcherServlet(webContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.setMultipartConfig(new MultipartConfigElement(null, 20_971_520L, 41_943_040L, 512_000));
        dispatcher.addMapping("/");
        
        AnnotationConfigWebApplicationContext restContext = new AnnotationConfigWebApplicationContext();
        restContext.register(RestServletContextConfiguration.class);
        
        DispatcherServlet servlet = new DispatcherServlet(restContext);
        servlet.setDispatchOptionsRequest(true);
        
        dispatcher = container.addServlet("springRestDispatcher", servlet);
        dispatcher.setLoadOnStartup(2);
        dispatcher.addMapping("/services/rest/*");
        
        AnnotationConfigWebApplicationContext soapContext = new AnnotationConfigWebApplicationContext();
        soapContext.register(SoapServletContextConfiguration.class);
        
        MessageDispatcherServlet soapServlet = new MessageDispatcherServlet(soapContext);
        soapServlet.setTransformWsdlLocations(true);
        
        dispatcher = container.addServlet("springSoapDispatcher", soapServlet);
        dispatcher.setLoadOnStartup(3);
        dispatcher.addMapping("/services/soap/*");
        
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        
        FilterRegistration.Dynamic characterEncoding = container.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(null, false, "/*");
        
        FilterRegistration.Dynamic registration = container.addFilter("preSecurityLoggingFilter", new PreSecurityLoggingFilter());
        registration.addMappingForUrlPatterns(null, false, "/*");
       
    }
	
}
