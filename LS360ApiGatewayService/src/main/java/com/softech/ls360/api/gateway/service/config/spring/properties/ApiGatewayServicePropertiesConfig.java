package com.softech.ls360.api.gateway.service.config.spring.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource("classpath:api-gateway-service.properties")
})
@Import({DevApiGatewayServicePropertiesConfig.class, QAApiGatewayServicePropertiesConfig.class, ProdApiGatewayServicePropertiesConfig.class})
public class ApiGatewayServicePropertiesConfig {

}
