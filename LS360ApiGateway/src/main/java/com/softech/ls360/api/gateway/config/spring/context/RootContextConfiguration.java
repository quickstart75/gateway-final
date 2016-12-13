package com.softech.ls360.api.gateway.config.spring.context;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.softech.ls360.api.gateway.service.config.spring.LS360ApiGatewayServiceAppConfig;
import com.softech.ls360.api.gateway.service.impl.LS360ApiGatewayServiceMarker;

@Configuration
@EnableLoadTimeWeaving
@ComponentScan(
	basePackageClasses = {
			LS360ApiGatewayServiceMarker.class
	},
	excludeFilters = @ComponentScan.Filter({
		Controller.class, ControllerAdvice.class, Endpoint.class
	})
)
@Import({LS360ApiGatewayServiceAppConfig.class, SecurityConfiguration.class})
public class RootContextConfiguration {
	
	//@Autowired
	//private Environment env;
	/**
	 * 
	 * @return
	  */
	@Bean
    public MessageSource messageSource() {
		
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(-1);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasenames("/WEB-INF/i18n/titles", 
        		"/WEB-INF/i18n/messages",
        		"/WEB-INF/i18n/errors", 
        		"/WEB-INF/i18n/validation"
        );
        return messageSource;
    }
	
	@Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(this.messageSource());
        return validator;
    }
	
	@Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(this.localValidatorFactoryBean());
        return processor;
    }
	
	@Bean
    public ObjectMapper objectMapper() {
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(dateFormatter);
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        return mapper;
    }
	
	@Bean
    public Jaxb2Marshaller ls360ApiJaxb2Marshaller() {
    
		Map<String, Object> marshallerPropertiesMap = new HashMap<>();
	    marshallerPropertiesMap.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(new String[] { 
        		"com.softech.ls360.lms.repository.entities",
        		"com.softech.vu360.lms.webservice.message.lmsapi"
        });
        
        marshaller.setMarshallerProperties(marshallerPropertiesMap);
        return marshaller;
    }
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
