//http://websystique.com/spring/spring-4-jms-activemq-example-with-jmslistener-enablejms/

package com.softech.ls360.api.gateway.service.config.spring;

import java.util.Arrays;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
 

 
@Configuration
public class MessagingConfiguration {
 
	@Value( "${spring.activemq.broker-url}" )
    private String DEFAULT_BROKER_URL;	// = "tcp://localhost:61616";
     
	@Value( "${spring.activemq.queue}" )
    private String ORDER_RESPONSE_QUEUE;	// = "Global_BatchImport";
     
   
     
    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
        return connectionFactory;
    }
 
    /*
     * Unused.
     */
    @Bean
    public ConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(connectionFactory());
        connectionFactory.setSessionCacheSize(10);
        return connectionFactory;
    }
 
   
 
    /*
     * Used for Sending Messages.
     */
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(ORDER_RESPONSE_QUEUE);
        return template;
    }
     
     
    @Bean
    MessageConverter converter(){
        return new SimpleMessageConverter();
    }
     
}