package com.softech.ls360.api.gateway.service.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.softech.ls360.api.gateway.service.config.spring.properties.ApiGatewayServicePropertiesConfig;
import com.softech.ls360.api.gateway.service.impl.LS360ApiGatewayServiceMarker;
import com.softech.ls360.lcms.api.config.spring.LcmsApiAppConfig;
import com.softech.ls360.lms.api.config.spring.LmsApiAppConfig;
import com.softech.ls360.lms.repository.config.spring.LmsRepositoryAppConfig;
import com.softech.ls360.storefront.api.config.spring.StorefrontApiAppConfig;
import com.softech.ls360.util.config.spring.AsyncConfig;

@Configuration
@ComponentScan(basePackageClasses = {LS360ApiGatewayServiceMarker.class })
@Import({AsyncConfig.class, LmsApiAppConfig.class, LcmsApiAppConfig.class, LmsRepositoryAppConfig.class, StorefrontApiAppConfig.class, ApiGatewayServicePropertiesConfig.class})
public class LS360ApiGatewayServiceAppConfig {

}
