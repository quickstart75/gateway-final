package com.softech.ls360.lms.api.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.softech.ls360.lms.api.service.impl.LmsApiServiceMarker;

@Configuration
@Import({LmsApiWebServiceConfig.class})
@ComponentScan(basePackageClasses = {LmsApiServiceMarker.class })
public class LmsApiAppConfig {

}
