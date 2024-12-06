package com.startlet.starlet_academy.configs;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.startlet.starlet_academy.repositorys")
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.startlet.starlet_academy.models") // Specify your model package
                .persistenceUnit("schoolPU")
                .build();
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}


