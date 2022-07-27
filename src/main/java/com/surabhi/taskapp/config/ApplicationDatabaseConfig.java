package com.surabhi.taskapp.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
//    @Primary
//    @Bean(name = "applicationDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Primary
//    @Bean(name = "applicationEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("applicationDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.surabhi.taskapp.repository", "com.surabhi.taskapp.entity")
//                .persistenceUnit("application")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "applicationTransactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("applicationEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
@Value("${spring.datasource.driver-class-name}")
private String driverClassName;

    @Value("${spring.datasource.jdbc-url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.hikari.auto-commit}")
    private boolean hikariAutoCommit;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private int hikariMinimumIdle;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int hikariMaximumPoolSize;

    @Value("${spring.datasource.hikari.pool-name}")
    private String hikariPoolName;

    @Primary
    @Bean(name = "applicationDataSource")
    public DataSource getDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setAutoCommit(hikariAutoCommit);
        ds.setMaximumPoolSize(hikariMaximumPoolSize);
        ds.setMinimumIdle(hikariMinimumIdle);
        ds.setPoolName(hikariPoolName);
        return ds;
    }

    @Primary
    @Bean(name = "applicationEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("applicationDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.*")
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

