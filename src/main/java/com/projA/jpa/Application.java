package com.projA.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.util.ClassUtils;

import com.projA.jpa.example.Test;
import com.projA.jpa.testmodel.Customer;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
        Test test = ctx.getBean(Test.class);

        test.cleanDB();
        test.testPersist();
//        test.testFind();
//        test.testQuery();
//        test.testUpdate();
//        test.testRemove();
        Customer customer = test.testFindCustomer("Smith");
        System.out.println("\nFound customer using a query: " + customer + "\n");
    }
	 
	@Bean
	public LocalContainerEntityManagerFactoryBean testEmf() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceUnitName("projA-java-jpa-test");
		// Not needed but to be specific which LoadTimeWeaver is used
		// Depending on the container either TomcatLoadTimeWeaver or InstrumentationLoadTimeWeaver (VM -javaagent argument) will be used
		// Set TomcatInstrumentableClassLoader in <Context><Loader> config in server.xml in tc Server in order to use TomcatLoadTimeWeaver
		em.setLoadTimeWeaver(new org.springframework.context.weaving.DefaultContextLoadTimeWeaver(ClassUtils.getDefaultClassLoader()));
		// Not used for now since persistence.xml is used and it overrides but specify anyway for reference and possible future usage
		em.setPackagesToScan(new String[] { "com.projA.jpa.testmodel" });
  		return em;
  	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean emf() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceUnitName("projA-java-jpa");
		// Not needed but to be specific which LoadTimeWeaver is used
		// Depending on the container either TomcatLoadTimeWeaver or InstrumentationLoadTimeWeaver (VM -javaagent argument) will be used
		// Set TomcatInstrumentableClassLoader in <Context><Loader> config in server.xml in tc Server in order to use TomcatLoadTimeWeaver
		em.setLoadTimeWeaver(new org.springframework.context.weaving.DefaultContextLoadTimeWeaver(ClassUtils.getDefaultClassLoader()));
		// Not used for now since persistence.xml is used and it overrides but specify anyway for reference and possible future usage
		em.setPackagesToScan(new String[] { "com.projA.jpa.testmodel" });
  		return em;
  	}
	
	@Bean
	public JpaTransactionManager testTransactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager(testEmf().getObject());
		return tm;
	}
	
	@Bean
	@Primary
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager(emf().getObject());
		return tm;
	}
}
