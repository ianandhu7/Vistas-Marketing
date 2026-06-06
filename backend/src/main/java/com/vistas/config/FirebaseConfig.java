package com.vistas.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials.path}")
    private Resource firebaseCredentials;

    @PostConstruct
    public void initialize() {
        // Skip initialization if Firebase is already set up
        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }

        try {
            InputStream credentialsStream = firebaseCredentials.getInputStream();

            // Read credentials JSON as string to check if it's a placeholder
            byte[] bytes = credentialsStream.readAllBytes();
            String content = new String(bytes);

            // If it contains placeholder values, skip real initialization
            // Demo-token flow in MarketingAuthController handles this case
            if (content.contains("your-firebase-project-id") || content.contains("dummy-private-key")) {
                System.out.println("[FirebaseConfig] Placeholder credentials detected. " +
                    "Firebase Admin SDK not initialized. " +
                    "Demo mode (demo-token) will be used for OTP verification.");
                return;
            }

            // Real credentials — initialize Firebase Admin SDK
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(
                    GoogleCredentials.fromStream(
                        new java.io.ByteArrayInputStream(bytes)
                    )
                )
                .build();
            FirebaseApp.initializeApp(options);
            System.out.println("[FirebaseConfig] Firebase Admin SDK initialized successfully.");

        } catch (Exception e) {
            // Log warning but do NOT crash the application
            // The demo-token flow in MarketingAuthController will still work
            System.err.println("[FirebaseConfig] WARNING: Could not initialize Firebase Admin SDK: " + e.getMessage());
            System.err.println("[FirebaseConfig] Firebase OTP verification will only work in demo mode (demo-token).");
        }
    }
}
