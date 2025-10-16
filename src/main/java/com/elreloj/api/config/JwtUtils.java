package com.elreloj.api.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;
    private final long EXPIRATION_TIME_MS = 86400000;

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();

    }

    public String extractUser(String token) {

        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();

    }

    public boolean expirationToken(String token) {

        Date expiration = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();

        return expiration.before(new Date());

    }

    public boolean validateToken(String token,UserDetails userDetails) {

        final String username = extractUser(token);
        return (username.equals(userDetails.getUsername()) && !expirationToken(token));

    }

}
