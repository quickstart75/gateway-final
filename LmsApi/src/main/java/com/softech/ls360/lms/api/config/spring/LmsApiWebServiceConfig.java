package com.softech.ls360.lms.api.config.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.softech.ls360.lms.api.config.spring.properties.LmsApiPropertiesConfig;

/**
 * We first use the @Configuration annotation to inform Spring that this is a Java-based configuration file.
 * 
 * @ComponentScan defines the packages that Spring should scan for annotations for bean definitions. It’s the same as the
 * <context:component-scan> tag in the XML configuration.
 * 
 *  When using the @ComponentScan annotation, you tell Spring which Java package or packages to scan for candidate classes using the
 * String[] basePackages attribute. Spring locates all classes belonging to these packages or subpackages and applies the resource 
 * filters against each class. The downside of basePackages is that it is not type-safe, and so a typo can easily go unnoticed. As 
 * an alternative, you can use the Class<?>[] basePackageClasses attribute. Spring determines the package names to scan from the 
 * classes specified.
 * 
 * When you specify basePackageClasses, Spring will scan the package (and subpackages) of the classes you specify. This is a nice 
 * trick with no-op classes/interfaces like Controllers, Services, etc. Put all your services and repositories in their own packages.
 * Create ServiceMarker empty class/interface in the pk.training.basit.site.service.impl package and RepositoryMarker empty 
 * class/interface in the pk.training.basit.site.repository.impl package. Spring will pick all the services and repositories. Since
 * all the services are in the package pk.training.basit.site.service.impl and serviceMarker empty class/interface too in this 
 * package so spring will pick all the @Service in this package. Same for @Repository
 * 
 * The @PropertySource annotation is used to load properties files into the Spring ApplicationContext, which accepts the 
 * location as the argument (more than one location can be provided). For XML, <context:property-placeholder> serves the same 
 * purpose.  @PropertySource annotation provides a mechanism for adding a source of name/value property pairs to Spring's 
 * Environment and it is used in conjunction with @Configuration classes. Loads in external properties into the Environment
 * 
 * Equivalent xml configuration file
 * 
 * 
 * 		<context:property-placeholder location="classpath:lmsapi-webservice.properties" ignore-unresolvable="true"/>
    	 
    	<bean id="soapMessageFactory" class="org.springframework.ws.soap.saaj.SaajSoapMessageFactory">
        	<property name="soapVersion">
            	<util:constant static-field="org.springframework.ws.soap.SoapVersion.SOAP_12"/>
        	</property>
    	</bean>
    	 
 		<bean id="lmsApiJaxbMarshaller" class="org.springframework.oxm.jaxb.Jaxb2Marshaller"
 			p:packagesToScan-ref="packagesToScanList"/>
 			
 		<util:list id="packagesToScanList" list-class="java.util.ArrayList">
    		<value>com.softech.ls360.lms.proxy.webservice.messages</value>
		</util:list>
		
		<bean id="lmsApiWebServiceTemplate" class="org.springframework.ws.client.core.WebServiceTemplate"
    		c:messageFactory-ref="soapMessageFactory"
        	p:defaultUri="${lmsapi.uri}"
  			p:marshaller-ref="lmsApiJaxbMarshaller"
  			p:unmarshaller-ref="lmsApiJaxbMarshaller" />
 * 
 * @author basit.ahmed
 *
 */
@Configuration
@Import({LmsApiPropertiesConfig.class})
public class LmsApiWebServiceConfig {

	/**
	 * Notice the @Autowired property of the env variable, which is of the Environment type. This is the Environment 
	 * abstraction feature that Spring provides.
	 */
	@Autowired
	private Environment env;
	
	/**
	 * The explicit messageFactory bean (the name is important) overrides the default message factory so that the supported SOAP 
	 * version is 1.2 instead of the default 1.1. There are several enhancements in SOAP 1.2 regarding protocol binding, 
	 * extensibility, and XML formats
	 * 
	 * The @Bean annotation is used to declare a Spring bean and the DI requirements. The @Bean annotation is equivalent to
	 * the <bean> tag, the method name is equivalent to the id attribute within the <bean> tag.
	 * 		
			<bean id="messageFactory" class="org.springframework.ws.soap.saaj.SaajSoapMessageFactory">
        		<property name="soapVersion">
            		<util:constant static-field="org.springframework.ws.soap.SoapVersion.SOAP_11"/>
        		</property>
    		</bean>
	 * 
	 * @return
	 */
	@Bean
	public SaajSoapMessageFactory soapMessageFactory() {
		 
		SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
		factory.setSoapVersion(SoapVersion.SOAP_11);
		 
		return factory;
		 
	}
	
	/**
	 * The @Bean annotation is used to declare a Spring bean and the DI requirements. The @Bean annotation is equivalent to
	 *  the <bean> tag, the method name is equivalent to the id attribute within the <bean> tag.
	 * 
	 * <bean id="lmsApiJaxbMarshaller" class="org.springframework.oxm.jaxb.Jaxb2Marshaller"
 			p:packagesToScan-ref="packagesToScanList"/>
   
	 * Spring WS uses Spring Framework’s OXM module which has the Jaxb2Marshaller to serialize and deserialize XML requests. The
	 * marshaller is pointed at the collection of generated domain objects and will use them to both serialize and deserialize
	 * between XML and POJOs.
	 * 
	 * @return
	 */
	@Bean
	public Jaxb2Marshaller lmsApiJaxb2Marshaller() {
		 
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		String[] packagesToScan = {"com.softech.vu360.lms.webservice.message"};
		
		marshaller.setPackagesToScan(packagesToScan);
		return marshaller;
		 
	}
	
	 /**
	  * The @Bean annotation is used to declare a Spring bean and the DI requirements. The @Bean annotation is equivalent to
	  * the <bean> tag, the method name is equivalent to the id attribute within the <bean> tag. Setter injection was 
	  * achieved by calling the corresponding method to get the entityManagerFactory, which is the same as using the 
	  * p:entityManagerFactory-ref tag in the XML configuration.
	  * 
	  * <bean id="lmsApiWebServiceTemplate" class="org.springframework.ws.client.core.WebServiceTemplate"
    		c:messageFactory-ref="soapMessageFactory"
        	p:defaultUri="${lmsapi.uri}"
  			p:marshaller-ref="lmsApiJaxbMarshaller"
  			p:unmarshaller-ref="lmsApiJaxbMarshaller" />
	  * 
	  * The LmsApiClient is created and configured with the URI of the web service which is in the WSDL, find the 
 	     service endpoint location. (It will be inside a <soap:address location=""/> element). It is also configured 
 	     to use the JAXB marshaller.
	  *
	  * @return
	  */
	@Bean
	public WebServiceTemplate lmsApiWebServiceTemplate() {
		 
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMessageFactory(soapMessageFactory());
		webServiceTemplate.setDefaultUri(env.getProperty("lmsapi.uri"));
		webServiceTemplate.setMarshaller(lmsApiJaxb2Marshaller());
		webServiceTemplate.setUnmarshaller(lmsApiJaxb2Marshaller());
		
		return webServiceTemplate;
		 
	}
		
}
