package com.softech.ls360.lms.repository.config.spring;

import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.softech.ls360.lms.repository.config.spring.properties.DatabasePropertiesConfig;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = false, order = 2)
@EnableJpaRepositories(
	basePackages = "com.softech.ls360.lms.repository.repositories",
    entityManagerFactoryRef = "entityManagerFactory",
	transactionManagerRef = "transactionManager"
)
@Import({DatabasePropertiesConfig.class})
public class PersistenceConfig {
      
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH = "hibernate.max_fetch_depth";
	private static final String PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE = "hibernate.jdbc.fetch_size";
	private static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";
	private static final String[] ENTITYMANAGER_PACKAGES_TO_SCAN = {"com.softech.ls360.lms.repository.entities", "com.softech.ls360.lms.repository.converters"};
	   
	@Autowired
	private Environment env;
	
	 @Bean(destroyMethod = "close")
	 public DataSource dataSource() {
		 BasicDataSource dataSource = new BasicDataSource();
		 dataSource.setDriverClassName(env.getProperty("jdbc.ls360.driverClassName"));
		 dataSource.setUrl(env.getProperty("jdbc.ls360.url"));
		 dataSource.setUsername(env.getProperty("jdbc.ls360.username"));
		 dataSource.setPassword(env.getProperty("jdbc.ls360.password"));
		 return dataSource;
	 }
	 
	 @Bean
     public JpaTransactionManager transactionManager() {
		 JpaTransactionManager transactionManager = new JpaTransactionManager();
         transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
         return transactionManager;
     }
	 
	@Bean
	public HibernateJpaVendorAdapter vendorAdaptor() {
		 HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		 return vendorAdapter;
	}
	 
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		 
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);   
        entityManagerFactoryBean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        entityManagerFactoryBean.setValidationMode(ValidationMode.NONE);
        entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());
      
        return entityManagerFactoryBean;
     }
	 
	 private Properties jpaHibernateProperties() {
         Properties properties = new Properties();
         properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
         properties.put(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH, env.getProperty(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH));
         properties.put(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE, env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE));
         properties.put(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE, env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE));
         
         return properties;       
	 }
	 
	
}
