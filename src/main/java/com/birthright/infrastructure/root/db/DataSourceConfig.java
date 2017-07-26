package com.birthright.infrastructure.root.db;

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

    private static final String PROPERTY_NAME_DB_DRIVER_CLASS = "db.driver";
    private static final String PROPERTY_NAME_DB_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DB_URL = "db.url";
    private static final String PROPERTY_NAME_DB_USER = "db.username";

    /**
     * <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
     */

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty(PROPERTY_NAME_DB_DRIVER_CLASS));
        dataSource.setJdbcUrl(environment.getRequiredProperty(PROPERTY_NAME_DB_URL));
        dataSource.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DB_USER));
        dataSource.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DB_PASSWORD));
        return dataSource;
    }
}
