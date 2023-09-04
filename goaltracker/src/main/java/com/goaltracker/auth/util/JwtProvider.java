package com.goaltracker.auth.util;

import com.goaltracker.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtProvider {
    public static final String AUTHORITIES_KEY = "authorities";

    private final Key jwtSecret;

    private final Long jwtExpirationInMs;

    public JwtProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") Long expiration) {
        this.jwtSecret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationInMs = expiration;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES_KEY, AuthorityConverter.flattenAuthorities(user.getUserCredential()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, AuthorityConverter.flattenAuthorities(authentication))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }
}
