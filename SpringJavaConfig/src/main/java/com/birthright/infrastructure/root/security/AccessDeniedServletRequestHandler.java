package com.birthright.infrastructure.root.security;

import com.birthright.constants.Routes;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by birthright on 04.03.17.
 */

public class AccessDeniedServletRequestHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (!response.isCommitted()) {
            // Put exception into request scope (perhaps of use to a view)
            request.setAttribute(WebAttributes.ACCESS_DENIED_403,
                                 accessDeniedException);

            // Set the 404 (deception) status code.
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            // forward to error page.
            RequestDispatcher dispatcher = request.getRequestDispatcher(Routes.ACCESS_DENIED_URI);
            dispatcher.forward(request, response);
        }
    }
}
