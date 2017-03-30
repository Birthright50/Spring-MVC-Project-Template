package com.birthright.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by birth on 03.02.2017.
 */
@PropertySources(
        {@PropertySource("classpath:spring/application.properties"),
                @PropertySource("classpath:spring/${spring.active.profiles:dev}/db.properties")}
)
@Configuration
public class PropertySourceConfig {

//From spring 4.3 doesn't need
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySource() {
//        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
//        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
//        return placeholderConfigurer;
//    }
}
