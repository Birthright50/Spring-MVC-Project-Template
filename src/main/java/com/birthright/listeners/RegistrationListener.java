package com.birthright.listeners;

import com.birthright.constants.SessionConstants;
import com.birthright.entity.User;
import com.birthright.event.OnRegistrationCompleteEvent;
import com.birthright.util.EmailMessageSender;
import com.birthright.service.interfaces.IVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by birth on 19.02.2017.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final HttpSession session;
    private final IVerificationTokenService tokenService;
    private final EmailMessageSender emailMessage;

    @Autowired
    public RegistrationListener(HttpSession session, IVerificationTokenService tokenService, EmailMessageSender emailMessage) {
        this.session = session;
        this.tokenService = tokenService;
        this.emailMessage = emailMessage;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(user, token);
        emailMessage.sendVerificationTokenEmail(event, user, token);
        session.setAttribute(SessionConstants.EXISTING_TOKEN, token);
    }
}
