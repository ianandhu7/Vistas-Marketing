package com.vistas.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    public String verifyTokenAndGetPhone(String idToken) {
        // Check if Firebase Admin SDK was initialized
        if (FirebaseApp.getApps().isEmpty()) {
            throw new RuntimeException(
                "Firebase Admin SDK is not initialized. " +
                "Real Firebase tokens cannot be verified. " +
                "Use demo-token for local development."
            );
        }

        try {
            FirebaseToken decodedToken = FirebaseAuth
                .getInstance()
                .verifyIdToken(idToken);
            
            String phoneNumber = (String) decodedToken.getClaims()
                .get("phone_number");
            
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                throw new RuntimeException(
                    "Phone number not found in Firebase token"
                );
            }
            
            // Remove country code prefix if present
            // Firebase returns +91XXXXXXXXXX for Indian numbers
            if (phoneNumber.startsWith("+91")) {
                phoneNumber = phoneNumber.substring(3);
            }
            
            return phoneNumber;
            
        } catch (Exception e) {
            throw new RuntimeException(
                "Firebase token verification failed: " + e.getMessage()
            );
        }
    }
}
