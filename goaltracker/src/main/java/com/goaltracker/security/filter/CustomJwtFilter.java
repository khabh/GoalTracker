package com.goaltracker.security.filter;

import com.goaltracker.auth.util.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

import static com.goaltracker.config.Constants.*;

@Component
public class CustomJwtFilter extends GenericFilterBean {

    private final TokenManager tokenManager;

    @Autowired
    public CustomJwtFilter(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = extractToken((HttpServletRequest) servletRequest);
        if (token != null && tokenManager.isValidToken(token)) {
            Authentication authentication = tokenManager.convertToAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String extractToken(HttpServletRequest request) {
        Cookie authorizationCookie = Arrays.stream(request.getCookies())
                .filter(this::isAuthorizationCookie)
                .findFirst().orElse(null);

        if (authorizationCookie == null) {
            return null;
        }

        String extractedToken = authorizationCookie.getValue().substring(BEARER_PREFIX_LENGTH);
        if (!StringUtils.hasText(extractedToken))
            return null;
        return extractedToken;
    }

    private boolean isAuthorizationCookie(Cookie cookie) {
        return cookie.getName().equals(AUTHORIZATION_HEADER) && StringUtils.hasText(cookie.getValue())
                && cookie.getValue().startsWith(BEARER_PREFIX);
    }
}
