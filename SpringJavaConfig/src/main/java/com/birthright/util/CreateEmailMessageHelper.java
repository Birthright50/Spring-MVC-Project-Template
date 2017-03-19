package com.birthright.util;

import com.birthright.entity.User;
import com.birthright.entity.VerificationToken;
import com.birthright.event.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by birthright on 24.02.17.
 */
@Component
public class CreateEmailMessageHelper {
    @Autowired
    private Environment environment;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private MessageSource messageSource;

    private void sendEmail(String subject, String text,
                           String from, String to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        mailSender.send(simpleMailMessage);
    }

    public void resendVerificationTokenEmail(String appUrl, Locale locale, VerificationToken newToken, User user) {
        String subject = "Resend Registration Token";
        String from = environment.getProperty("smtp.username");
        String to = user.getEmail();
        String message = messageSource.getMessage("register.message.resend", null, locale);
        String confirmationUrl =
                appUrl + "/register?token=" + newToken.getToken() + "&u=" + user.getId();
        String text = message + "\r\n" + confirmationUrl;
        sendEmail(subject, text, from, to);
    }

    public void sendVerificationTokenEmail(OnRegistrationCompleteEvent event, User user, String token) {
        String subject = "Registration Confirmation";
        String from = environment.getProperty("smtp.username");
        String to = user.getEmail();
        System.out.println(event.getLocale());
        String message = messageSource.getMessage("register.message.success", null, event.getLocale());
        String confirmationUrl =
                event.getAppUrl() + "/register?token=" + token + "&u=" + user.getId();
        String text = message + "\r\n" + confirmationUrl;
        sendEmail(subject, text, from, to);
    }

    public void sendResetPasswordEmail(String appUrl, Locale locale, String token, User user) {
        String subject = "Reset Password";
        String from = environment.getProperty("smtp.username");
        String to = user.getEmail();
        String confirmationUrl =
                appUrl + "/login?reset_password&token=" + token + "&u=" + user.getId();
        String message = messageSource.getMessage("auth.message.reset_password", null, locale);
        String text = message + "\r\n" + confirmationUrl;
        sendEmail(subject, text, from, to);
    }
}
