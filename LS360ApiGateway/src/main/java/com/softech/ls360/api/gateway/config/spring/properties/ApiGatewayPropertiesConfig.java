package com.softech.ls360.api.gateway.config.spring.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.softech.ls360.api.gateway.config.spring.properties.api.ApiProperties;
import com.softech.ls360.api.gateway.config.spring.properties.linuxfoundation.LinuxFoundationPropertiesConfig;

@Configuration
@Import({LinuxFoundationPropertiesConfig.class, ApiProperties.class})
public class ApiGatewayPropertiesConfig {

}
