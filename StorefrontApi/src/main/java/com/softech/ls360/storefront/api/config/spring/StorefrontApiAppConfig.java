package com.softech.ls360.storefront.api.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.softech.ls360.storefront.api.config.spring.properties.StorefrontApiPropertiesConfig;
import com.softech.ls360.storefront.api.service.impl.StorefrontApiServiceMarker;

@Configuration
@ComponentScan(basePackageClasses = {StorefrontApiServiceMarker.class })
@Import({StorefrontApiPropertiesConfig.class})
public class StorefrontApiAppConfig {

	/**
	 * Using the @PropertySource annotation on your @Configuration does not add a PropertySourcesPlaceholderConfigurer (or a 
	 * PropertyPlaceholderConfigurer). These two mechanisms are independent and the recommended new approach is to use 
	 * environment.getProperty("some.value") instead of "${some.value}". So if you want to continue using @Value annotation you 
	 * need to add an @Bean that creates the PropertySourcesPlaceholderConfigurer
	 * 
	 * To resolve ${} in @Values, you must register a static PropertySourcesPlaceholderConfigurer in either XML or 
	 * annotation configuration file.
	 * 
	 * @return
	 */
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
