package com.softech.ls360.api.gateway.config.spring.properties.api;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.softech.ls360.util.config.spring.environment.ProdEnvironment;

@Configuration
@Conditional(ProdEnvironment.class)
@PropertySources({
	@PropertySource("classpath:api-prod.properties")
})
public class ApiProdProperties {

}
