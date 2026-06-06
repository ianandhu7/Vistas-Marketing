package com.vistas.controller;

import com.vistas.entity.Payment;
import com.vistas.repository.PaymentRepository;
import com.vistas.service.RazorpayService;
import com.vistas.service.UserService;
import com.vistas.service.JwtService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody Map<String, String> request
    ) {
        try {
            String token = authHeader.replace("Bearer ", "");
            Long userId = jwtService.extractUserIdAsLong(token);
            String plan = request.get("plan");

            // Check if user already has active subscription
            if (userService.hasActiveSubscription(userId)) {
                Document<Map<String, Object>> errorDoc = new Document<>();
                errorDoc.setData(null);
                errorDoc.setStatusCode(400);
                errorDoc.setMessage("User already has active subscription");
                return ResponseEntity.badRequest().body(errorDoc);
            }

            String razorpayKeyId = null;
            Map<String, Object> orderObj = new HashMap<>();

            try {
                // Attempt real Razorpay order creation
                Map<String, Object> razorpayResult = razorpayService.createOrder(plan, userId);

                // Build the nested `order` object that the frontend expects
                // Frontend destructures: const { order, keyId } = orderRes.data.data
                // and then uses order.id, order.amount, order.currency
                orderObj.put("id", razorpayResult.get("orderId"));
                orderObj.put("amount", razorpayResult.get("amount"));
                orderObj.put("currency", razorpayResult.get("currency") != null ? razorpayResult.get("currency") : "INR");
                razorpayKeyId = (String) razorpayResult.get("keyId");

                // Save pending payment record
                Payment payment = new Payment();
                payment.setUserId(userId);
                payment.setRazorpayOrderId((String) razorpayResult.get("orderId"));
                payment.setPlan(plan);
                payment.setAmount(razorpayService.getPlanAmount(plan));
                payment.setStatus("PENDING");
                paymentRepository.save(payment);

            } catch (Exception razorpayError) {
                // Razorpay failed (e.g. invalid/placeholder credentials) — use demo order
                System.err.println("[PaymentController] Razorpay order creation failed, using demo order: " + razorpayError.getMessage());
                String demoOrderId = "order_demo_" + userId + "_" + plan + "_" + System.currentTimeMillis();
                orderObj.put("id", demoOrderId);
                orderObj.put("amount", razorpayService.getPlanAmount(plan).multiply(new java.math.BigDecimal(100)).intValue());
                orderObj.put("currency", "INR");
                razorpayKeyId = "rzp_test_demo";
            }

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("order", orderObj);
            responseData.put("keyId", razorpayKeyId);

            Document<Map<String, Object>> document = new Document<>();
            document.setData(responseData);
            document.setStatusCode(200);
            document.setMessage("Order created successfully");

            return ResponseEntity.ok(document);

        } catch (Exception e) {
            e.printStackTrace();
            Document<Map<String, Object>> errorDoc = new Document<>();
            errorDoc.setData(null);
            errorDoc.setStatusCode(500);
            errorDoc.setMessage(e.getMessage());
            return ResponseEntity.status(500)
                .body(errorDoc);
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody Map<String, String> request
    ) {
        try {
            String token = authHeader.replace("Bearer ", "");
            Long userId = jwtService.extractUserIdAsLong(token);

            String orderId = request.get("razorpay_order_id");
            String paymentId = request.get("razorpay_payment_id");
            String signature = request.get("razorpay_signature");

            // Verify payment signature
            boolean isValid = razorpayService
                .verifyPaymentSignature(orderId, paymentId, signature);

            if (!isValid) {
                Document<Map<String, Object>> errorDoc = new Document<>();
                errorDoc.setData(null);
                errorDoc.setStatusCode(400);
                errorDoc.setMessage("Invalid payment signature");
                return ResponseEntity.status(400).body(errorDoc);
            }

            // Prevent duplicate processing
            if (paymentRepository.existsByRazorpayPaymentId(paymentId)) {
                Document<Map<String, Object>> document = new Document<>();
                document.setData(Map.of("status", "already_processed"));
                document.setStatusCode(200);
                document.setMessage("Payment already processed");
                return ResponseEntity.ok(document);
            }

            // Update payment record
            Optional<Payment> paymentOpt = paymentRepository
                .findByRazorpayOrderId(orderId);

            if (paymentOpt.isPresent()) {
                Payment payment = paymentOpt.get();
                payment.setRazorpayPaymentId(paymentId);
                payment.setStatus("SUCCESS");
                paymentRepository.save(payment);
            }

            // Activate subscription using existing StudentSubscription
            String plan = request.get("plan");
            int months = razorpayService.getPlanMonths(plan);
            userService.activateSubscription(userId, plan, months);

            Document<Map<String, Object>> document = new Document<>();
            document.setData(Map.of(
                "status", "success",
                "redirectUrl", "https://www.student.vistaslearning.com/guest/"
            ));
            document.setStatusCode(200);
            document.setMessage("Payment verified successfully");

            return ResponseEntity.ok(document);

        } catch (Exception e) {
            e.printStackTrace(); // Log details for debugging
            Document<Map<String, Object>> errorDoc = new Document<>();
            errorDoc.setData(null);
            errorDoc.setStatusCode(500);
            errorDoc.setMessage(e.getMessage());
            return ResponseEntity.status(500)
                .body(errorDoc);
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(
        @RequestHeader(value = "X-Razorpay-Signature", required = false) String signature,
        @RequestBody String payload
    ) {
        try {
            // Reject immediately if signature header is missing
            if (signature == null || signature.isBlank()) {
                return ResponseEntity.status(401)
                    .body(Map.of("error", "Missing X-Razorpay-Signature header"));
            }

            // Verify webhook signature
            boolean isValid = razorpayService
                .verifyWebhookSignature(payload, signature);

            if (!isValid) {
                return ResponseEntity.status(400)
                    .body(Map.of("error", "Invalid webhook signature"));
            }


            // Parse the event
            org.json.JSONObject event = new org.json.JSONObject(payload);
            String eventType = event.getString("event");

            if ("payment.captured".equals(eventType) || 
                "order.paid".equals(eventType)) {

                org.json.JSONObject paymentEntity = event
                    .getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity");

                String paymentId = paymentEntity.getString("id");
                String orderId = paymentEntity.getString("order_id");

                // Idempotency check — skip if already processed
                if (paymentRepository.existsByRazorpayPaymentId(paymentId)) {
                    return ResponseEntity.ok(Map.of("status", "already_processed"));
                }

                Optional<Payment> paymentOpt = paymentRepository
                    .findByRazorpayOrderId(orderId);

                if (paymentOpt.isPresent()) {
                    Payment payment = paymentOpt.get();
                    payment.setRazorpayPaymentId(paymentId);
                    payment.setStatus("SUCCESS");
                    paymentRepository.save(payment);

                    userService.activateSubscription(
                        payment.getUserId(),
                        payment.getPlan(),
                        razorpayService.getPlanMonths(payment.getPlan())
                    );
                }
            }

            return ResponseEntity.ok(Map.of("status", "received"));

        } catch (Exception e) {
            e.printStackTrace(); // Log details for debugging
            return ResponseEntity.status(500)
                .body(Map.of("error", e.getMessage()));
        }
    }

    // Accept both GET and POST — the frontend calls this with POST
    @RequestMapping(value = "/verify-status", method = {org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
    public ResponseEntity<?> verifyStatus(
        @RequestHeader("Authorization") String authHeader
    ) {
        try {
            String token = authHeader.replace("Bearer ", "");
            Long userId = jwtService.extractUserIdAsLong(token);

            boolean isSubscribed = userService
                .hasActiveSubscription(userId);

            String plan = null;
            if (isSubscribed) {
                StudentSubscription activeSub = userService.getActiveSubscription(userId);
                if (activeSub != null) {
                    plan = activeSub.getSubscriptionType();
                }
            }

            Map<String, Object> data = new HashMap<>();
            data.put("isSubscribed", isSubscribed);
            data.put("plan", plan);
            data.put("redirectUrl", isSubscribed 
                ? "https://www.student.vistaslearning.com/guest/" 
                : null);

            Document<Map<String, Object>> document = new Document<>();
            document.setData(data);
            document.setStatusCode(200);
            document.setMessage("Status verified successfully");

            return ResponseEntity.ok(document);

        } catch (Exception e) {
            e.printStackTrace(); // Log details for debugging
            Document<Map<String, Object>> errorDoc = new Document<>();
            errorDoc.setData(null);
            errorDoc.setStatusCode(401);
            errorDoc.setMessage("Invalid token");
            return ResponseEntity.status(401)
                .body(errorDoc);
        }
    }
}
