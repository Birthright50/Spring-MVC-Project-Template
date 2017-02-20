package com.birthright.infrastructure.configuration.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by birth on 02.02.2017.
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    /**
     * <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
     */

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driver"));
        dataSource.setJdbcUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        return dataSource;
    }
}
