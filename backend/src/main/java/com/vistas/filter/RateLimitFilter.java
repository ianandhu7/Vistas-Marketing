package com.vistas.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


/**
 * In-memory rate limiter for OTP endpoints.
 *
 * Rules (per IP, extracted from X-Forwarded-For or remote address):
 *   /api/v1/auth/verify-otp  → max 5 attempts per 15 minutes
 *   /api/v1/auth/send-otp    → max 3 attempts per 10 minutes
 *
 * Registered as a @Bean in SecurityConfig — do NOT add @Component here.
 * Uses ConcurrentHashMap — sufficient for single-instance deployments.
 * For multi-instance, replace with Redis-backed rate limiter.
 */
public class RateLimitFilter implements Filter {


    // Key: "ENDPOINT:IDENTIFIER" → [attemptCount, windowStartMs]
    private final ConcurrentHashMap<String, long[]> rateLimitStore = new ConcurrentHashMap<>();

    private static final int VERIFY_OTP_MAX_ATTEMPTS  = 5;
    private static final long VERIFY_OTP_WINDOW_MS    = 15 * 60 * 1000L; // 15 minutes

    private static final int SEND_OTP_MAX_ATTEMPTS    = 3;
    private static final long SEND_OTP_WINDOW_MS      = 10 * 60 * 1000L; // 10 minutes

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest  = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        if (uri.contains("/api/v1/auth/verify-otp") || uri.contains("/api/v1/auth/send-otp")) {
            String clientIp = extractIdentifier(httpRequest);
            if ("127.0.0.1".equals(clientIp) || "0:0:0:0:0:0:0:1".equals(clientIp) || "::1".equals(clientIp) || "localhost".equals(clientIp)) {
                chain.doFilter(request, response);
                return;
            }

            // Determine window size and max attempts for this endpoint
            int maxAttempts;
            long windowMs;
            String endpointKey;

            if (uri.contains("/verify-otp")) {
                maxAttempts = VERIFY_OTP_MAX_ATTEMPTS;
                windowMs    = VERIFY_OTP_WINDOW_MS;
                endpointKey = "VERIFY";
            } else {
                maxAttempts = SEND_OTP_MAX_ATTEMPTS;
                windowMs    = SEND_OTP_WINDOW_MS;
                endpointKey = "SEND";
            }

            // Extract phone number from request body to rate-limit per phone
            // We cache the body because it can only be read once
            String identifier = extractIdentifier(httpRequest);
            String storeKey   = endpointKey + ":" + identifier;

            if (isRateLimited(storeKey, maxAttempts, windowMs)) {
                httpResponse.setStatus(429);
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write(
                    "{\"statusCode\":429,\"message\":\"Too many attempts. Please wait before trying again.\",\"data\":null}"
                );
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Checks and increments the attempt counter for the given key.
     * Returns true if the request should be blocked (rate limited).
     */
    private boolean isRateLimited(String key, int maxAttempts, long windowMs) {
        long now = System.currentTimeMillis();

        rateLimitStore.compute(key, (k, existing) -> {
            if (existing == null || now - existing[1] > windowMs) {
                // First attempt or window has expired — start fresh
                return new long[]{1L, now};
            }
            existing[0]++; // increment count within window
            return existing;
        });

        long[] state = rateLimitStore.get(key);
        return state != null && state[0] > maxAttempts;
    }

    /**
     * Extracts the best available identifier: phone number from body, falling back to IP.
     * Since we can't re-read the body after buffering, we use IP as a pragmatic fallback.
     * The phone number check happens in the controller level; IP-based is sufficient here
     * because it's the network-layer defense.
     */
    private String extractIdentifier(HttpServletRequest request) {
        // Use X-Forwarded-For if behind a proxy/load balancer; else direct IP
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // Take only the first IP in case of proxy chain (e.g. "1.2.3.4, 5.6.7.8")
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip != null ? ip : "unknown";
    }

    /**
     * Periodically clean up expired entries to prevent memory growth.
     * In production consider scheduling this via @Scheduled.
     */
    public void cleanup() {
        long now = System.currentTimeMillis();
        long maxWindow = Math.max(VERIFY_OTP_WINDOW_MS, SEND_OTP_WINDOW_MS);
        rateLimitStore.entrySet().removeIf(e -> now - e.getValue()[1] > maxWindow);
    }
}
