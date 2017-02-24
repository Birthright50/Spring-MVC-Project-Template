package com.birthright.web.listeners;

import com.birthright.constants.SessionConstants;
import com.birthright.entity.User;
import com.birthright.event.OnRegistrationCompleteEvent;
import com.birthright.helper.CreateEmailMessage;
import com.birthright.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by birth on 19.02.2017.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private HttpSession session;

    @Autowired
    private IUserService service;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CreateEmailMessage emailMessage;

    @Autowired
    private MessageSource messages;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        SimpleMailMessage simpleMailMessage = emailMessage.constructVerificationTokenEmail(event, user, token, messages);
        mailSender.send(simpleMailMessage);
        session.setAttribute(SessionConstants.EXISTING_TOKEN, token);
    }

}
