package com.softech.ls360.api.gateway.service.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.inject.Inject;

/**
 * Created by muhammad.sajjad on 10/31/2016.
 */
@Configuration
public class EmailConfig {

    @Inject
    private Environment env;

    @Bean
    public JavaMailSender getMailSenderConfig(){
        JavaMailSenderImpl mailSenderConfig = new JavaMailSenderImpl();
        mailSenderConfig.setProtocol("smtp");
        mailSenderConfig.setHost(env.getProperty("udp.smtp.host"));
        mailSenderConfig.setPort(Integer.parseInt(env.getProperty("udp.smtp.port")));
        return mailSenderConfig;
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfig(){
        FreeMarkerConfigurationFactoryBean configurer = new FreeMarkerConfigurationFactoryBean();
        configurer.setTemplateLoaderPath("classpath:" + env.getProperty("udp.email.template.path"));
        return configurer;
    }
}