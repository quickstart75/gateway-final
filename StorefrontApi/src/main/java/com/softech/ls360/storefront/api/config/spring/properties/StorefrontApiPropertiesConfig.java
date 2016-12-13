package com.softech.ls360.storefront.api.config.spring.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource("classpath:storefront-api.properties")
})
@Import({DevStorefrontApiPropertiesConfig.class, QAStorefrontApiPropertiesConfig.class, ProdStorefrontApiPropertiesConfig.class})
public class StorefrontApiPropertiesConfig {

}
