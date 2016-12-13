package com.softech.ls360.lms.repository.config.spring.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource("classpath:database/jdbc.properties")
})
@Import({DevDatabasePropertiesConfig.class, QADatabasePropertiesConfig.class, ProdDatabasePropertiesConfig.class})
public class DatabasePropertiesConfig {

}
