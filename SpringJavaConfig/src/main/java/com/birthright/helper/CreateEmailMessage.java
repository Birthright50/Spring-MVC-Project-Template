package com.birthright.helper;

import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;
import com.birthright.event.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by birthright on 24.02.17.
 */
@Component
public class CreateEmailMessage {
    @Autowired
    private Environment environment;


    public SimpleMailMessage constructResetVerificationTokenEmail(final String appUrl, final Locale locale, final VerificationToken newToken, final User user, MessageSource messages) {
        final String confirmationUrl =
                appUrl + "/register?token=" + newToken.getToken() + "&u=" + user.getId();
        final String message = messages.getMessage("message.registration.resend", null, locale);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message + " \r\n" + confirmationUrl);
        email.setTo(user.getEmail());
        email.setFrom(environment.getProperty("smtp.username"));
        return email;
    }

    public SimpleMailMessage constructVerificationTokenEmail(final OnRegistrationCompleteEvent event, final User user, final String token, MessageSource messages) {
        final String subject = "Registration Confirmation";
        final String message = messages.getMessage("message.registration.success", null, event.getLocale());
        final String confirmationUrl =
                event.getAppUrl() + "/register?token=" + token + "&u=" + user.getId();
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(environment.getProperty("smtp.username"));
        email.setText(message + " \r\n" + confirmationUrl);
        email.setTo(user.getEmail());
        email.setSubject(subject);
        return email;
    }
}
