package com.birthright.web.listeners;

import com.birthright.constants.SessionConstants;
import com.birthright.entity.User;
import com.birthright.event.OnRegistrationCompleteEvent;
import com.birthright.helper.CreateEmailMessageHelper;
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

    @Autowired
    private HttpSession session;

    @Autowired
    private IVerificationTokenService tokenService;

    @Autowired
    private CreateEmailMessageHelper emailMessage;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(user, token);
        emailMessage.sendVerificationTokenEmail(event, user, token);
        session.setAttribute(SessionConstants.EXISTING_TOKEN, token);
    }

}
