package com.birthright.web.listeners;

import com.birthright.entity.User;
import com.birthright.event.OnRegistrationCompleteEvent;
import com.birthright.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by birth on 19.02.2017.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {


    @Autowired
    private Environment environment;

    @Autowired
    private IUserService service;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MessageSource messages;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        SimpleMailMessage simpleMailMessage = constructEmailMessage(event, user, token);
        mailSender.send(simpleMailMessage);
    }

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registration/confirm?token=" + token;
        final String message = messages.getMessage("message.registration.success", null, event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\nhttp://localhost:8080" + confirmationUrl);
        email.setFrom(environment.getProperty("smtp.username"));
        return email;
    }



}
