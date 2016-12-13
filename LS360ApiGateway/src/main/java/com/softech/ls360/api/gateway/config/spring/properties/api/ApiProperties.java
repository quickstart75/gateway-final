package com.softech.ls360.api.gateway.config.spring.properties.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ApiDevProperties.class, ApiQAProperties.class, ApiProdProperties.class})
public class ApiProperties {

}
