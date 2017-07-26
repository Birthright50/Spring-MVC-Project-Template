package com.birthright.infrastructure;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;
import java.util.Set;

/**
 * Created by birth on 30.01.2017.
 */
/*
   <filter>
        <filter-name>springSecurityFilterChain</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>springSecurityFilterChain</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

 */
public class SecurityAppInitializer extends AbstractSecurityWebApplicationInitializer {

    /*
         <listener>
            <listener-class>org.springframework.security.web.session.HttpSessionEventPublisher</listener-class>
        </listener>
        This should be true, if session management has specified a maximum number of sessions.
    */
    @Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }

    /*
                    <session-config>
                        <tracking-mode>COOKIE</tracking-mode>
                    </session-config>
                    Don't need to override if your tracking mode is COOKIE
                    */
    @Override
    protected Set<SessionTrackingMode> getSessionTrackingModes() {
        return super.getSessionTrackingModes();
    }

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }

}
