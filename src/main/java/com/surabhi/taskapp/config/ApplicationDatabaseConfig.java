package com.surabhi.taskapp.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "applicationEntityManagerFactory",
        transactionManagerRef = "applicationTransactionManager",
        basePackages = {"com.surabhi.taskapp.repository", "com.surabhi.taskapp.entity"})
public class ApplicationDatabaseConfig {
    @Primary
    @Bean(name = "applicationDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "applicationEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("applicationDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.surabhi.taskapp.repository", "com.surabhi.taskapp.entity")
                .persistenceUnit("application")
                .build();
    }

    @Primary
    @Bean(name = "applicationTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("applicationEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

