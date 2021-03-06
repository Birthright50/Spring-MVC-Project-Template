package com.birthright.listeners;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by birth on 01.02.2017.
 */
//just for example
@Component
@Log4j2
public class SessionListener implements HttpSessionListener {
    // <session-timeout>20</session-timeout>
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.debug(() -> "==== Session is created ====");
        se.getSession().setMaxInactiveInterval(60 * 20);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug(() -> "==== Session is destroyed ====");
    }
}
