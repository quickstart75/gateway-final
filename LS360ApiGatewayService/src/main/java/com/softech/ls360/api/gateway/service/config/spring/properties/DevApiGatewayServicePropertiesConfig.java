package com.softech.ls360.api.gateway.service.config.spring.properties;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.softech.ls360.util.config.spring.environment.DevEnvironment;

@Configuration
@Conditional(DevEnvironment.class)
@PropertySources({
	@PropertySource("classpath:api-gateway-service-dev.properties")
})
public class DevApiGatewayServicePropertiesConfig {

}
