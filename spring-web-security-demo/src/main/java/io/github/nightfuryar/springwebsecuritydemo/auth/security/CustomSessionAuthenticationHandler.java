package io.github.nightfuryar.springwebsecuritydemo.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

public class CustomSessionAuthenticationHandler implements SessionAuthenticationStrategy {
    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        // log ip
        String ipAddress = request.getRemoteAddr();
        System.out.println("User authenticated from IP: " + ipAddress);
    }
}
