package com.softuni.perfumes_shop_shipping.service.impl;

import com.softuni.perfumes_shop_shipping.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

@Service
public class JwtServiceImpl implements JwtService {

    private final String jwtSecret;

    public JwtServiceImpl(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    public UserDetails extractUserInfo(String token) {
        Claims claims = getClaims(token);

        String userName = claims.getSubject();
        List<String> roles = getRoles(claims);

        return new User(userName, "", roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList());
    }

    @SuppressWarnings("unchecked")
    private static List<String> getRoles(Claims claims) {
        return claims.get("roles", List.class);
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
