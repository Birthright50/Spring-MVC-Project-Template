package com.birthright.event;

import com.birthright.entity.User;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by birth on 19.02.2017.
 */
@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;

    public OnRegistrationCompleteEvent(
            User user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
