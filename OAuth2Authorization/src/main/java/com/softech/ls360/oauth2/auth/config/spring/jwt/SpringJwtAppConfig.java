package com.softech.ls360.oauth2.auth.config.spring.jwt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.softech.ls360.oauth2.auth.service.impl.spring.jwt.SpringJwtServiceMarker;

@Configuration
@ComponentScan(basePackageClasses = {SpringJwtServiceMarker.class })
public class SpringJwtAppConfig {

}
