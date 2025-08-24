package com.hover.project.security.service;

import com.hover.project.employee.entity.Employee;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    public static final String TOKEN_TYPE_CLAIM = "token_type";
    public static final String ACCESS_TOKEN_TYPE = "access";
    public static final String REFRESH_TOKEN_TYPE = "refresh";
    public static final String EMAIL_CLAIM = "email";
    public static final String ID_CLAIM = "id";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Employee employee) {
        if (employee == null || employee.getUserName() == null) {
            throw new IllegalArgumentException("Employee and username must not be null");
        }

        return Jwts.builder()
                .subject(employee.getUserName())
                .claim(ID_CLAIM, employee.getId())
                .claim(EMAIL_CLAIM, employee.getEmail())
                .claim(TOKEN_TYPE_CLAIM, ACCESS_TOKEN_TYPE)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String generateRefreshToken(Employee employee) {
        if (employee == null || employee.getUserName() == null) {
            throw new IllegalArgumentException("Employee and username must not be null");
        }

        return Jwts.builder()
                .subject(employee.getUserName())
                .claim(ID_CLAIM, employee.getId())
                .claim(EMAIL_CLAIM, employee.getEmail())
                .claim(TOKEN_TYPE_CLAIM, REFRESH_TOKEN_TYPE)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
        return true;

    }

    public boolean isRefreshToken(String refreshToken) {
        return REFRESH_TOKEN_TYPE.equals(extractTokenType(refreshToken));
    }

    public boolean isAccessToken(String accessToken) {
        return ACCESS_TOKEN_TYPE.equals(extractTokenType(accessToken));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get(EMAIL_CLAIM, String.class));
    }

    public String extractId(String token) {
        return extractClaim(token, claims -> claims.get(ID_CLAIM, String.class));
    }

    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get(TOKEN_TYPE_CLAIM, String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
