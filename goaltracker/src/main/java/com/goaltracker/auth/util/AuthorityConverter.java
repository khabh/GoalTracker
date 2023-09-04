package com.goaltracker.auth.util;

import com.goaltracker.auth.domain.Authority;
import com.goaltracker.auth.domain.UserCredential;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorityConverter {

    private AuthorityConverter() {}

    public static List<GrantedAuthority> toGrantedAuthorities(UserCredential userCredential) {
        return userCredential.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
    }

    public static String flattenAuthorities(UserCredential userCredential) {
        return userCredential.getAuthorities().stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.joining(","));
    }

    public static String flattenAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
