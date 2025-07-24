package io.github.nightfuryar.springwebsecuritydemo.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.web.session.InvalidSessionStrategy;

import java.io.IOException;

public class CustomInvalidSessionHandler implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{" +
                "\"error\": \"Invalid Session\"," +
                "\"message\": \"Your session has expired or is invalid. Please log in again.\"" +
                "}");
    }
}
