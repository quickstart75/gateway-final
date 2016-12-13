package com.softech.ls360.lcms.api.config.spring.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource("classpath:lcms-api-webservice.properties")
})
public class LcmsApiPropertiesConfig {

}
