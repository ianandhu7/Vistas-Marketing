package com.vistas.service;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${app.jwt.name:vlearning}")
    private String appName;

    // Tokens lifetimes configurations (configured in application.properties or using standard defaults)
    private static final long ACCESS_TOKEN_EXPIRATION_MS = 15 * 60 * 1000L; // 15 minutes
    private static final long REFRESH_TOKEN_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000L; // 7 days

    @Value("${RSA_PRIVATE_KEY:#{null}}")
    private String privateKeyPem;

    @Value("${RSA_PUBLIC_KEY:#{null}}")
    private String publicKeyPem;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void init() {
        try {
            if (privateKeyPem != null && !privateKeyPem.trim().isEmpty() &&
                publicKeyPem != null && !publicKeyPem.trim().isEmpty()) {
                
                // Clean PEM strings from headers/footers and whitespace
                String cleanPrivate = privateKeyPem
                        .replace("-----BEGIN PRIVATE KEY-----", "")
                        .replace("-----END PRIVATE KEY-----", "")
                        .replaceAll("\\s+", "");
                String cleanPublic = publicKeyPem
                        .replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replaceAll("\\s+", "");

                byte[] privateBytes = Base64.getDecoder().decode(cleanPrivate);
                byte[] publicBytes = Base64.getDecoder().decode(cleanPublic);

                KeyFactory kf = KeyFactory.getInstance("RSA");
                this.privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
                this.publicKey = kf.generatePublic(new X509EncodedKeySpec(publicBytes));
                System.out.println("[JwtService] Loaded RSA Key Pair from environment variables.");
            } else {
                // Generate fallback key pair for local dev
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair keyPair = kpg.generateKeyPair();
                this.privateKey = keyPair.getPrivate();
                this.publicKey = keyPair.getPublic();
                System.out.println("[JwtService] Warning: RSA keys not provided in environment. Generated self-signed fallback keys for local development.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RSA KeyPair in JwtService", e);
        }
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_MS);
        
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
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public String generateRefreshToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_MS);

        return Jwts.builder()
                .setIssuer(appName)
                .claim("usid", user.getUserSurId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public String extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("usid").toString();
    }

    public Long extractUserIdAsLong(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
        return ((Number) claims.get("usid")).longValue();
    }
}
