package com.birthright.infrastructure.root;

import com.birthright.infrastructure.root.async.AsyncConfig;
import com.birthright.infrastructure.root.db.JpaPersistenceConfig;
import com.birthright.infrastructure.root.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by birth on 28.01.2017.
 */
@Configuration
@PropertySources(
        {@PropertySource("classpath:spring/application.properties"),
                @PropertySource("classpath:spring/${spring.active.profiles:dev}/db.properties")}
)
@Import({SecurityConfig.class, JpaPersistenceConfig.class, SchedulerConfig.class, AsyncConfig.class})
@EnableAspectJAutoProxy
@ComponentScan(value = "com.birthright", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.birthright\\.((infrastructure)|(web))\\..*"))
public class ApplicationConfig {
    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSender javaMailSenderImpl() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(environment.getProperty("smtp.host"));
        mailSenderImpl.setPort(environment.getProperty("smtp.port", Integer.class));
        mailSenderImpl.setProtocol(environment.getProperty("smtp.protocol"));
        mailSenderImpl.setUsername(environment.getProperty("smtp.username"));
        mailSenderImpl.setPassword(environment.getProperty("smtp.password"));
        Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        javaMailProps.put("mail.smtp.starttls.required", true);
        javaMailProps.put("mail.smtp.socketFactory.class", SSLSocketFactory.class);
        mailSenderImpl.setJavaMailProperties(javaMailProps);
        return mailSenderImpl;
    }

    @Bean
    public MessageSource messageSource() throws IOException {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:i18n/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
