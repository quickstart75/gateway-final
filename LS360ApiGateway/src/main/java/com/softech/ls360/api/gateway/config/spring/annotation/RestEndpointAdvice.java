package com.softech.ls360.api.gateway.config.spring.annotation;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * you might want to also create advice for your web controllers at some point. So, instead of using @ControllerAdvice directly, it 
 * would be best to create your own stereotype annotation specific to RESTful endpoint controllers.
 * 
 * @author basit.ahmed
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ControllerAdvice
public @interface RestEndpointAdvice {
	String value() default "";
}
