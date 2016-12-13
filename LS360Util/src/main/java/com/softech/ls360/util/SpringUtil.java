package com.softech.ls360.util;

import java.net.InetAddress;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class SpringUtil {
	
	public static GenericXmlApplicationContext loadSpringContext(String springXmlFile) {
		
		/* In Spring, the ApplicationContext interface is an extension to BeanFactory. In addition to DI services, the
		 * ApplicationContext also provides other services, such as transaction and AOP service, message source for 
		 * internationalization (i18n), and application event handling, to name a few.
		 * 
		 * In developing Spring-based application, it’s recommended that you interact with Spring via the ApplicationContext 
		 * interface.
		 * 
		 * The GenericXmlApplicationContext class implements the ApplicationContext interface and is able to bootstrap Spring’s 
		 * ApplicationContext from the configurations defined in XML files.
		 */
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load(springXmlFile);
		context.refresh();
		
		return context;
		
	} //end of loadSpringContext()
	
	public static Properties loadPropertiesFileFromClassPath(String fileClassPath) {
		
		Properties properties = null;
		
		try {
			
			/**
			 * Often an application needs to access a variety of resources in different forms. You might need to access some 
			 * configuration data stored in a file in the file system, some image data stored in a JAR file on the classpath, or maybe 
			 * some data on a server elsewhere. Spring provides a  unified mechanism for accessing resources in a protocol-independent 
			 * way. This means your application can access a file resource in the same way, whether it is stored in the file  system, in
			 * the classpath, or on a remote server.
			 * 
			 * At the core of Spring’s resource support is the org.springframework.core.io.Resource interface. The Resource interface 
			 * defines ten self-explanatory methods: contentLength(), exists(), getDescription(), getFile(), getFileName(), getURI(), 
			 * getURL(), isOpen(), isReadable(), and lastModified(). In addition to these ten methods, there is one that is not quite so 
			 * self-explanatory: createRelative(). The createRelative() method creates a new Resource instance using a path that is 
			 * relative to the instance on which it is invoked. You can provide your own Resource implementations, but in most cases, you
			 * use one of the built-in implementations for accessing file (the FileSystemResource class), 
			 * classpath (the ClassPathResource class), or URL resources (the UrlResource class).
			 * 
			 * Internally, Spring uses another interface, ResourceLoader, and the default implementation, DefaultResourceLoader, to 
			 * locate and create Resource instances. However, you generally won’t interact with DefaultResourceLoader, instead using 
			 * another ResourceLoader implementation—ApplicationContext.
			 */
			Resource resource = new ClassPathResource(fileClassPath);
			properties = PropertiesLoaderUtils.loadProperties(resource);
			
		} catch (Exception e) {
			System.out.println("Unablle to load properties file from path: " + fileClassPath);
		}
		
		return properties;
		
	} //end of loadPropertiesFileFromClassPath()
	
	public static boolean matchEnvironment(Environment environment, String environmentValue, String... ipAdresses) throws Exception {
		
		boolean environmentMatch = matchByEnvironmentVariable(environment, environmentValue);
		if (!environmentMatch) {
			environmentMatch = matchByIpAddresses(ipAdresses);
		}
		
		return environmentMatch;
	}
	
	public static boolean isLinuxEnvironment(Environment environment) {
		
		boolean linuxOperatinSystem = false;
		String operatingSystem = environment.getProperty("os.name");
		if (StringUtils.isNotBlank(operatingSystem)) {
			linuxOperatinSystem = operatingSystem.indexOf("nux") > 0 ||
					operatingSystem.indexOf("aix") > 0 ||
					operatingSystem.indexOf("nix") > 0 ;
		}
		return linuxOperatinSystem;
	}
	
	public static boolean matchByEnvironmentVariable(Environment environment, String providedValue) {
		
		boolean matches = false;
		String environmentVariableValue = environment.getProperty("LS360_ENVIRONMENT");
		if (StringUtils.isNoneBlank(environmentVariableValue, providedValue)) {
			if (environmentVariableValue.equalsIgnoreCase(providedValue)) {
				matches = true;
			}
		}
		return matches;
		
	}
	
	public static boolean matchByIpAddresses(String... ipAdresses) throws Exception {
		boolean ipAddressMatch = false;
		InetAddress localHost = InetAddress.getLocalHost();
		if (localHost != null && ipAdresses != null) {
			String hostAddress = localHost.getHostAddress();
			for (String ipAdress : ipAdresses) {
	             if (hostAddress.equalsIgnoreCase(ipAdress)) {
	            	 ipAddressMatch = true;
	            	 break;
	             }
	         }
		}
		return ipAddressMatch;
	}


} //end of class SpringUtil
