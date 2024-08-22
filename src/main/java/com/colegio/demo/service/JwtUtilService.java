package com.colegio.demo.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;


import java.util.Base64;
import java.util.function.Function;

import javax.crypto.SecretKey;

import java.util.Date;

@Service
public class JwtUtilService {
    private static final String JWT_SECRET_KEY = "TExBVkVfTVVZX1NFQ1JFVEzE3Zmxu7BSGSJx72BSBXM";
    private static final long JWT_TIME_VALIDITY = 1000 * 60 * 15;
    private static final long JWT_TIME_REFRESH_VALIDATE = 1000 * 60 * 60 * 24;

    public String generateToken(UserDetails userDetails,String tipo) {
        return Jwts.builder()
            .subject(userDetails.getUsername())
            .claim("tipo", tipo)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + JWT_TIME_VALIDITY))
            .signWith(getSigningKey())
            .compact();
    }

    public String generateRefreshToken(UserDetails userDetails, String tipo) {
        return Jwts.builder()
            .subject(userDetails.getUsername())
            .claim("tipo", tipo)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + JWT_TIME_REFRESH_VALIDATE))
            .signWith(getSigningKey())
            .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}