package com.goaltracker.auth.util;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JwtProvider {
    private final Key jwtSecret;

    private final Long jwtExpirationInMs;

    public JwtProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") Long expiration) {
        this.jwtSecret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationInMs = expiration;
    }

    private List<GrantedAuthority> extractGrantedAuthorities(UserCredential userCredential) {
        return userCredential.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", extractGrantedAuthorities(user.getUserCredential()))
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }
}
