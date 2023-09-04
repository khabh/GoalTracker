package com.goaltracker.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class EmailPasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public EmailPasswordAuthenticationToken(String email, String password) {
        super(email, password);
    }
}
