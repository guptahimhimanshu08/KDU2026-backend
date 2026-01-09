package com.kickdrum.talent_portal.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger log =
            LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            log.warn(
                "Forbidden access attempt by user '{}' with roles {} on endpoint '{}'",
                authentication.getName(),
                authentication.getAuthorities(),
                request.getRequestURI()
            );
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
    }
}
