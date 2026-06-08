package com.vistas.controller;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.common.response.Document;
import com.vistas.service.FirebaseService;
import com.vistas.service.UserService;
import com.vistas.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class MarketingAuthController {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private javax.persistence.EntityManager entityManager;

    @GetMapping("/debug-db")
    public ResponseEntity<?> debugDb() {
        try {
            java.util.List<?> processList = entityManager.createNativeQuery("SHOW PROCESSLIST").getResultList();
            java.util.List<?> roles = entityManager.createNativeQuery("SELECT * FROM vl_role").getResultList();
            java.util.List<?> users = entityManager.createNativeQuery("SELECT * FROM vl_user LIMIT 5").getResultList();
            Map<String, Object> result = new HashMap<>();
            result.put("processList", processList);
            result.put("roles", roles);
            result.put("users", users);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/kill-process")
    public ResponseEntity<?> killProcess(@RequestParam Long id) {
        try {
            java.sql.Connection conn = entityManager.unwrap(java.sql.Connection.class);
            try (java.sql.Statement stmt = conn.createStatement()) {
                stmt.execute("KILL " + id);
            }
            return ResponseEntity.ok("Killed process " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @org.springframework.beans.factory.annotation.Value("${app.demo.mode:false}")
    private boolean demoMode;

    // The OTP is sent by Firebase on the frontend directly
    // This endpoint just verifies the Firebase token after OTP success
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
        @RequestBody Map<String, String> request
    ) {
        try {
            String idToken = request.get("idToken");
            String planId = request.get("planId");

            if (idToken == null || idToken.isEmpty()) {
                Document<Map<String, Object>> errorDoc = new Document<>();
                errorDoc.setData(null);
                errorDoc.setStatusCode(400);
                errorDoc.setMessage("Firebase ID token is required");
                return ResponseEntity.badRequest().body(errorDoc);
            }

            // Verify Firebase token and extract phone number
            String phoneNumber;
            if (demoMode && "demo-token".equals(idToken)) {
                phoneNumber = request.get("phoneNumber");
                if (phoneNumber == null || phoneNumber.isEmpty()) {
                    phoneNumber = "9745974709";
                }
            } else {
                phoneNumber = firebaseService.verifyTokenAndGetPhone(idToken);
            }

            // Find or create user using custom user service
            User user = userService
                .findOrCreateByPhone(phoneNumber);

            // Generate JWT using custom JWT service
            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            // Check subscription status
            boolean isSubscribed = userService
                .hasActiveSubscription(user.getUserSurId());

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", user.getUserSurId());
            userMap.put("mobile", phoneNumber);
            userMap.put("email", user.getEmail());
            userMap.put("isSubscribed", isSubscribed);

            if (isSubscribed) {
                StudentSubscription activeSub = userService.getActiveSubscription(user.getUserSurId());
                if (activeSub != null) {
                    userMap.put("subscriptionPlan", activeSub.getSubscriptionType());
                } else {
                    userMap.put("subscriptionPlan", null);
                }
            } else {
                userMap.put("subscriptionPlan", null);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("token", accessToken);
            data.put("user", userMap);

            Document<Map<String, Object>> document = new Document<>();
            document.setData(data);
            document.setStatusCode(200);
            document.setMessage("OTP verified successfully");

            // Set refresh token as HTTP-only cookie
            return ResponseEntity.ok()
                .header("Set-Cookie",
                    "refreshToken=" + refreshToken +
                    "; HttpOnly; Secure; SameSite=Strict; Path=/; Max-Age=604800"
                )
                .body(document);

        } catch (Exception e) {
            e.printStackTrace(); // Log details for debugging
            Document<Map<String, Object>> errorDoc = new Document<>();
            errorDoc.setData(null);
            errorDoc.setStatusCode(401);
            errorDoc.setMessage(e.getMessage());
            return ResponseEntity.status(401)
                .body(errorDoc);
        }
    }

    @PostMapping({"/refresh-token", "/refresh"})
    public ResponseEntity<?> refreshToken(
        @CookieValue(name = "refreshToken", required = false) 
        String refreshToken
    ) {
        try {
            if (refreshToken == null) {
                Document<Map<String, Object>> errorDoc = new Document<>();
                errorDoc.setData(null);
                errorDoc.setStatusCode(401);
                errorDoc.setMessage("No refresh token found");
                return ResponseEntity.status(401).body(errorDoc);
            }

            String userId = jwtService.extractUserId(refreshToken);
            User user = userService.findById(Long.parseLong(userId));
            String newAccessToken = jwtService.generateToken(user);

            Map<String, Object> data = new HashMap<>();
            data.put("token", newAccessToken);

            Document<Map<String, Object>> document = new Document<>();
            document.setData(data);
            document.setStatusCode(200);
            document.setMessage("Token refreshed successfully");

            return ResponseEntity.ok()
                .body(document);

        } catch (Exception e) {
            e.printStackTrace(); // Log details for debugging
            Document<Map<String, Object>> errorDoc = new Document<>();
            errorDoc.setData(null);
            errorDoc.setStatusCode(401);
            errorDoc.setMessage("Invalid refresh token");
            return ResponseEntity.status(401)
                .body(errorDoc);
        }
    }
}
