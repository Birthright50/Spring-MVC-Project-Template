package com.birthright.infrastructure.configuration;

import com.birthright.infrastructure.configuration.db.JpaPersistenceConfig;
import com.birthright.infrastructure.configuration.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.net.ssl.SSLSocketFactory;
import java.util.Properties;

/**
 * Created by birth on 28.01.2017.
 */
@Configuration
@Import({PropertySourceConfig.class,
        SecurityConfig.class,
        JpaPersistenceConfig.class
})
@EnableScheduling
@EnableCaching
@ComponentScan(value = "com.birthright",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "com\\.birthright\\.((infrastructure)|(web))\\..*"))
public class ApplicationConfig {



    @Autowired
    private Environment env;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public JavaMailSender javaMailSenderImpl() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(env.getProperty("smtp.host"));
        mailSenderImpl.setPort(env.getProperty("smtp.port", Integer.class));
        mailSenderImpl.setProtocol(env.getProperty("smtp.protocol"));
        mailSenderImpl.setUsername(env.getProperty("smtp.username"));
        mailSenderImpl.setPassword(env.getProperty("smtp.password"));
        Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        javaMailProps.put("mail.smtp.starttls.required", true);
        javaMailProps.put("mail.smtp.socketFactory.class", SSLSocketFactory.class);
        mailSenderImpl.setJavaMailProperties(javaMailProps);
        return mailSenderImpl;
    }



}
