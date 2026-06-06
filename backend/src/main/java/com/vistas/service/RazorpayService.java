package com.vistas.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class RazorpayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    // Plan amounts in paise
    private static final Map<String, Integer> PLAN_AMOUNTS = new HashMap<>();
    static {
        PLAN_AMOUNTS.put("3months", 99900);
        PLAN_AMOUNTS.put("6months", 149900);
        PLAN_AMOUNTS.put("14months", 199900);
    }

    // Plan durations in months
    private static final Map<String, Integer> PLAN_MONTHS = new HashMap<>();
    static {
        PLAN_MONTHS.put("3months", 3);
        PLAN_MONTHS.put("6months", 6);
        PLAN_MONTHS.put("14months", 14);
    }

    public Map<String, Object> createOrder(String plan, Long userId) {
        try {
            Integer amount = PLAN_AMOUNTS.get(plan);
            if (amount == null) {
                throw new RuntimeException("Invalid plan selected: " + plan);
            }

            RazorpayClient client = new RazorpayClient(keyId, keySecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "vistas_" + userId + "_" + plan);
            orderRequest.put("payment_capture", 1);

            JSONObject notes = new JSONObject();
            notes.put("userId", userId.toString());
            notes.put("plan", plan);
            orderRequest.put("notes", notes);

            Order order = client.orders.create(orderRequest);

            Map<String, Object> response = new HashMap<>();
            response.put("orderId", order.get("id"));
            response.put("amount", amount);
            response.put("currency", "INR");
            response.put("keyId", keyId);
            response.put("plan", plan);

            return response;

        } catch (RazorpayException e) {
            throw new RuntimeException(
                "Failed to create Razorpay order: " + e.getMessage()
            );
        }
    }

    public boolean verifyPaymentSignature(
        String orderId,
        String paymentId,
        String signature
    ) {
        try {
            String payload = orderId + "|" + paymentId;
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(
                keySecret.getBytes("UTF-8"), "HmacSHA256"
            );
            mac.init(secretKey);
            byte[] hash = mac.doFinal(payload.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString().equals(signature);

        } catch (Exception e) {
            throw new RuntimeException(
                "Signature verification failed: " + e.getMessage()
            );
        }
    }

    public boolean verifyWebhookSignature(
        String payload,
        String signature
    ) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(
                webhookSecret.getBytes("UTF-8"), "HmacSHA256"
            );
            mac.init(secretKey);
            byte[] hash = mac.doFinal(payload.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString().equals(signature);

        } catch (Exception e) {
            return false;
        }
    }

    public Integer getPlanMonths(String plan) {
        return PLAN_MONTHS.getOrDefault(plan, 0);
    }

    public BigDecimal getPlanAmount(String plan) {
        Integer paise = PLAN_AMOUNTS.get(plan);
        if (paise == null) return BigDecimal.ZERO;
        return new BigDecimal(paise).divide(new BigDecimal(100));
    }
}
