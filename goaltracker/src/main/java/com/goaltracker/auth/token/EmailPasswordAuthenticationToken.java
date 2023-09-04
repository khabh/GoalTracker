package com.goaltracker.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class EmailPasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String email;
    private final Collection<GrantedAuthority> authorities;

    public EmailPasswordAuthenticationToken(String email, String password,
                                            Collection<GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.email = email;
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
