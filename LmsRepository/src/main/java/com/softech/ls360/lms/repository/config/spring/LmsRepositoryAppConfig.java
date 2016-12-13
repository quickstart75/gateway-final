package com.softech.ls360.lms.repository.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PersistenceConfig.class})
public class LmsRepositoryAppConfig {


}
