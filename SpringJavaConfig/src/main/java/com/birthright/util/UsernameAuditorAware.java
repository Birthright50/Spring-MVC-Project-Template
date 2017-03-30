package com.birthright.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by birthright on 19.03.17.
 */
@Log4j2
public class UsernameAuditorAware implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        log.debug("Getting the username of authenticated user.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            log.debug("Current user is anonymous. Returning null.");
            return null;
        }
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        log.debug("Returning username: {}", username);
        return username;
    }
}
