package com.app.carrental.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
public class HibernateConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("database.driver_class_name")));
        dataSource.setUrl(environment.getProperty("database.url"));
        dataSource.setSchema(environment.getProperty("database.schema"));
        dataSource.setUsername(environment.getProperty("database.username"));
        dataSource.setPassword(environment.getProperty("database.password"));
        return dataSource;
    }

    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(environment.getProperty("entity_package"));
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.c3p0.acquire_increment", Integer.parseInt(Objects.requireNonNull(environment.getProperty("database.connection_increment"))));
        hibernateProperties.put("hibernate.c3p0.min_size", Integer.parseInt(Objects.requireNonNull(environment.getProperty("database.min_connections"))));
        hibernateProperties.put("hibernate.c3p0.max_size", Integer.parseInt(Objects.requireNonNull(environment.getProperty("database.max_connections"))));
        hibernateProperties.put("hibernate.c3p0.timeout", Integer.parseInt(Objects.requireNonNull(environment.getProperty("database.connection_timeout"))));
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean().getObject());
        return transactionManager;
    }

}
