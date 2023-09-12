package com.goaltracker.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import static com.goaltracker.config.Constants.BEARER_PREFIX;


@Component
public class TokenManager {
    public static final String AUTHORITIES_KEY = "authorities";

    private final Key jwtSecret;

    private final Long jwtExpirationInMs;

    public TokenManager(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") Long expiration) {
        this.jwtSecret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationInMs = expiration;
    }

//    public String generateToken(String username, UserCredential userCredential) {
//        return BEARER_PREFIX + Jwts.builder()
//                .setSubject(username)
//                .claim(AUTHORITIES_KEY, AuthorityConverter.flattenAuthorities(userCredential))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
//                .signWith(jwtSecret, SignatureAlgorithm.HS512)
//                .compact();
//    }

    public String generateToken(Authentication authentication) {
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, AuthorityConverter.flattenAuthorities(authentication))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            return false;
        }
    }

    public Authentication convertToAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        List<GrantedAuthority> authorities = AuthorityConverter.toGrantedAuthorities(claims.get(AUTHORITIES_KEY).toString());
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
