package com.softech.ls360.lms.api.config.spring.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource("classpath:lmsapi-webservice.properties")
})
@Import({DevLmsApiPropertiesConfig.class, QALmsApiPropertiesConfig.class, ProdLmsApiPropertiesConfig.class})
public class LmsApiPropertiesConfig {

}
