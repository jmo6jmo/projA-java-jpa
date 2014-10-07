package com.projA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

import com.projA.example.Test;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
    	
        Test test = ctx.getBean(Test.class);
//        test.factory = Persistence.createEntityManagerFactory("projA-java-jpa");
        
        test.cleanDB();
        test.testPersist();
//        test.testFind();
//        test.testQuery();
//        test.testUpdate();
//        test.testRemove();
//        test.factory.close();
    }
	
//  @Bean
//  public LocalContainerEntityManagerFactoryBean cemf() {
//  	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//  	em.setPersistenceUnitName("projA-java-jpa");
////  	em.setLoadTimeWeaver(new org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver());
////  	em.setPackagesToScan(new String[] { "com.projA.model" });
////  	EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
////      vendorAdapter.setDatabasePlatform("org.eclipse.persistence.nosql.adapters.mongo.MongoPlatform");
////      vendorAdapter.setShowSql(true);
////      em.setJpaVendorAdapter(vendorAdapter);
//  	return em;
//  }
  
	@Bean
	public LocalEntityManagerFactoryBean emf() {
		LocalEntityManagerFactoryBean em = new LocalEntityManagerFactoryBean();
		em.setPersistenceUnitName("projA-java-jpa");
		return em;
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager();
		return tm;
	}
}
