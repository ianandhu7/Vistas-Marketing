package com.vistas.service;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${app.jwt.name:vlearning}")
    private String appName;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs:604800000}")
    private int jwtExpirationInMs;

    @Value("${app.jwt.refresh:2592000000}")
    private long jwtRefreshInMs;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        String authorities = user.getRoles().stream()
                .map(Role::getRoleName)
                .map(Enum::name)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setIssuer(appName)
                .claim("usid", user.getUserSurId())
                .claim("roles", authorities)
                .claim("ssf", false)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtRefreshInMs);

        return Jwts.builder()
                .setIssuer(appName)
                .claim("usid", user.getUserSurId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("usid").toString();
    }

    public Long extractUserIdAsLong(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return ((Number) claims.get("usid")).longValue();
    }
}
