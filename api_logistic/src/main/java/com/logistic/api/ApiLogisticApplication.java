package com.logistic.api;

import org.javers.spring.auditable.AuthorProvider;
import org.javers.spring.auditable.SpringSecurityAuthorProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.logistic.api")
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = "com.logistic.api.repository")
@EntityScan(basePackages = {"com.logistic.api.model"})
public class ApiLogisticApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApiLogisticApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApiLogisticApplication.class);
    }
    
    @Bean
    public AuthorProvider provideJaversAuthor() {
        return new SpringSecurityAuthorProvider();
//        return new SimpleAuthorProvider();
    }
    
}
