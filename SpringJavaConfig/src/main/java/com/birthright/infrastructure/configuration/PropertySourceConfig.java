package com.birthright.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * Created by birth on 03.02.2017.
 */
//Moved to ApplicationConfig
@PropertySources(
        {@PropertySource("classpath:spring/application.properties"),
                @PropertySource("classpath:spring/${spring.active.profiles:dev}/db.properties")}
)
public class PropertySourceConfig {

    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }
//From spring 4.3 doesn't need
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySource() {
//        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
//        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
//        return placeholderConfigurer;
//    }
}
