package com.birthright.listeners;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by birth on 01.02.2017.
 */
@Component
public class SessionListener implements HttpSessionListener {
    // <session-timeout>20</session-timeout>
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("==== Session is created ====");
        se.getSession().setMaxInactiveInterval(60 * 20);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("==== Session is destroyed ====");
    }
}
