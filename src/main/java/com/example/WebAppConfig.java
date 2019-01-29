package com.example;

import com.example.dao.*;
import com.example.entity.PropertyKey;
import com.example.entity.PropertyValue;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan("com.example")
@EnableTransactionManagement
@EnableWebMvc
public class WebAppConfig {
    @Bean
    public ViewResolver jspViewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setPrefix("/view/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);

        return viewResolver;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/TestDB");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123");

        return dataSource;
    }

    @Autowired
    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactory = new LocalSessionFactoryBean();
        localSessionFactory.setDataSource(dataSource);
        localSessionFactory.setAnnotatedClasses(PropertyKey.class, PropertyValue.class);

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        hibernateProperties.setProperty("show_sql", "false");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");

        localSessionFactory.setHibernateProperties(hibernateProperties);

        try {
            localSessionFactory.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return localSessionFactory.getObject();
    }

    @Autowired
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Autowired
    @Bean
    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
        return new HibernateTemplate(sessionFactory);
    }

    @Autowired
    @Bean
    public Dictionary wordDictionary(SessionFactory sessionFactory) {
        return new DBProperties(
                sessionFactory, 4, "[a-zA-Z]", "Word Dictionary", 0);
    }

    @Autowired
    @Bean
    public Dictionary numberDictionary(SessionFactory sessionFactory) {
        return new DBProperties(
                sessionFactory, 5, "[0-9]", "Number Dictionary", 1);
    }

    @Bean
    public List<String> dictionaryNameList() {
        List<String> dictionaryNameList = new LinkedList<>();
        Collections.addAll(dictionaryNameList, "word-dictionary", "number-dictionary");

        return dictionaryNameList;
    }
}
