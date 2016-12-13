package com.softech.ls360.lcms.api.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.softech.ls360.lcms.api.service.impl.LcmsApiServiceMarker;

@Configuration
@Import({LcmsApiWebServiceConfig.class})
@ComponentScan(basePackageClasses = {LcmsApiServiceMarker.class })
public class LcmsApiAppConfig {

}
