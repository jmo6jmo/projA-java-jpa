package com.projA.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.util.ClassUtils;

import com.projA.jpa.example.Test;
import com.projA.jpa.model.Customer;

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
	public LocalContainerEntityManagerFactoryBean cemf() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceUnitName("projA-java-jpa");
		// Not needed but to be specific which LoadTimeWeaver is used
		em.setLoadTimeWeaver(new org.springframework.context.weaving.DefaultContextLoadTimeWeaver(ClassUtils.getDefaultClassLoader()));
		em.setPackagesToScan(new String[] { "com.projA.jpa.model" });
  		return em;
  	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager();
		return tm;
	}
}
