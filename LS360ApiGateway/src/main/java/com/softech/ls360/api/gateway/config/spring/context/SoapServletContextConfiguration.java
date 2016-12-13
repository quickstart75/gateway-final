package com.softech.ls360.api.gateway.config.spring.context;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.softech.ls360.api.gateway.endpoint.soap.SoapEndpointMarker;

@Configuration
@ComponentScan(
    basePackageClasses = {SoapEndpointMarker.class}, 
    useDefaultFilters = false, 
    includeFilters = @ComponentScan.Filter(Endpoint.class)
)

@ImportResource("classpath:config/soapServletContext.xml")
public class SoapServletContextConfiguration {
	
	/**
	 * The explicit messageFactory bean (the name is important) overrides the default message factory so that the supported SOAP 
	 * version is 1.2 instead of the default 1.1. There are several enhancements in SOAP 1.2 regarding protocol binding, 
	 * extensibility, and XML formats
	 * 
	 * The @Bean annotation is used to declare a Spring bean and the DI requirements. The @Bean annotation is equivalent to
	 * the <bean> tag, the method name is equivalent to the id attribute within the <bean> tag.
	 * 		
			<bean id="messageFactory" class="org.springframework.ws.soap.saaj.SaajSoapMessageFactory">
        		<property name="soapVersion">
            		<util:constant static-field="org.springframework.ws.soap.SoapVersion.SOAP_12"/>
        		</property>
    		</bean>
	 * 
	 * @return
	 */
	@Bean
	public WebServiceMessageFactory messageFactory() {
		SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
		factory.setSoapVersion(SoapVersion.SOAP_11);
		return factory;
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
