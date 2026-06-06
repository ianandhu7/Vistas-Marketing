#!/bin/bash

# ═══════════════════════════════════════════════════════
#  Vistas Learning — Security & Flow Test Suite
#  Run: bash vistas-security-tests.sh
#  Make sure your dev server is running: npm run dev
# ═══════════════════════════════════════════════════════

BASE="http://localhost:3000"
PASS=0
FAIL=0

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

pass() { echo -e "${GREEN}  ✓ PASS${NC} — $1"; ((PASS++)); return 0; }
fail() { echo -e "${RED}  ✗ FAIL${NC} — $1"; ((FAIL++)); return 0; }
section() { echo -e "\n${CYAN}━━━ $1 ━━━${NC}"; }

expect_status() {
  local label="$1"
  local expected="$2"
  local actual="$3"
  if [ "$actual" = "$expected" ]; then
    pass "$label (got $actual)"
  else
    fail "$label (expected $expected, got $actual)"
  fi
}

# ───────────────────────────────────────────────────────
section "1. CORS — Origin Checks"
# ───────────────────────────────────────────────────────

# Allowed origin should get 200 or 401 (not 403)
STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -H "Origin: http://localhost:5173" \
  "$BASE/api/auth/send-otp" -X POST \
  -H "Content-Type: application/json" \
  -d '{"phone":"9999999999"}')
[ "$STATUS" != "403" ] && pass "Allowed origin (localhost:5173) not blocked" \
  || fail "Allowed origin (localhost:5173) was blocked — check ALLOWED_ORIGINS"

# Disallowed origin should get 403
STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -H "Origin: https://evil-site.com" \
  "$BASE/api/auth/send-otp" -X POST \
  -H "Content-Type: application/json" \
  -d '{"phone":"9999999999"}')
expect_status "Disallowed origin blocked" "403" "$STATUS"

# Preflight OPTIONS should return 204
STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -X OPTIONS \
  -H "Origin: http://localhost:5173" \
  -H "Access-Control-Request-Method: POST" \
  "$BASE/api/auth/send-otp")
expect_status "Preflight OPTIONS returns 204" "204" "$STATUS"

# ───────────────────────────────────────────────────────
section "2. Security Headers"
# ───────────────────────────────────────────────────────

HEADERS=$(curl -s -I -H "Origin: http://localhost:5173" "$BASE/api/health" 2>&1)

echo "$HEADERS" | grep -qi "x-frame-options: deny" \
  && pass "X-Frame-Options: DENY present" \
  || fail "X-Frame-Options: DENY missing"

echo "$HEADERS" | grep -qi "x-content-type-options: nosniff" \
  && pass "X-Content-Type-Options: nosniff present" \
  || fail "X-Content-Type-Options: nosniff missing"

echo "$HEADERS" | grep -qi "strict-transport-security" \
  && pass "Strict-Transport-Security present" \
  || fail "Strict-Transport-Security missing"

echo "$HEADERS" | grep -qi "content-security-policy" \
  && pass "Content-Security-Policy present" \
  || fail "Content-Security-Policy missing"

echo "$HEADERS" | grep -qi "referrer-policy" \
  && pass "Referrer-Policy present" \
  || fail "Referrer-Policy missing"

# ───────────────────────────────────────────────────────
section "3. Rate Limiting — Auth Route (limit: 10 per 15 min)"
# ───────────────────────────────────────────────────────

echo -e "${YELLOW}  Sending 12 rapid requests to /api/auth/send-otp...${NC}"
RATE_LIMITED=false
for i in $(seq 1 12); do
  STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
    -X POST "$BASE/api/auth/send-otp" \
    -H "Origin: http://localhost:5173" \
    -H "Content-Type: application/json" \
    -d '{"phone":"9999999999"}')
  if [ "$STATUS" = "429" ]; then
    RATE_LIMITED=true
    pass "Rate limit triggered on request $i (429 received)"
    break
  fi
done
$RATE_LIMITED || fail "Rate limit NOT triggered after 12 requests on /api/auth"

# ───────────────────────────────────────────────────────
section "4. Auth — No Token Should Return 401"
# ───────────────────────────────────────────────────────

STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -X POST "$BASE/api/subscribe" \
  -H "Origin: http://localhost:5173" \
  -H "Content-Type: application/json" \
  -d '{"plan":"14month"}')
expect_status "No token → 401 on /api/subscribe" "401" "$STATUS"

STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -X GET "$BASE/api/user/profile" \
  -H "Origin: http://localhost:5173")
expect_status "No token → 401 on /api/user/profile" "401" "$STATUS"

STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -X POST "$BASE/api/payments/create-order" \
  -H "Origin: http://localhost:5173" \
  -H "Content-Type: application/json" \
  -d '{"plan":"14month"}')
expect_status "No token → 401 on /api/payments/create-order" "401" "$STATUS"

# ───────────────────────────────────────────────────────
section "5. Auth — Fake/Expired Token Should Return 401"
# ───────────────────────────────────────────────────────

STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -X POST "$BASE/api/subscribe" \
  -H "Origin: http://localhost:5173" \
  -H "Authorization: Bearer fake.jwt.token" \
  -H "Content-Type: application/json" \
  -d '{"plan":"14month"}')
expect_status "Fake token → 401" "401" "$STATUS"

# ───────────────────────────────────────────────────────
section "6. Input Validation"
# ───────────────────────────────────────────────────────

# Invalid plan name
STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -X POST "$BASE/api/subscribe" \
  -H "Origin: http://localhost:5173" \
  -H "Content-Type: application/json" \
  -d '{"plan":"free"}')
expect_status "Invalid plan name → 400 or 401" "400" "$STATUS" 2>/dev/null \
  || expect_status "Invalid plan name → 401 (auth first)" "401" "$STATUS"

# Missing body
STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -X POST "$BASE/api/payments/create-order" \
  -H "Origin: http://localhost:5173" \
  -H "Content-Type: application/json" \
  -d '{}')
[ "$STATUS" = "400" ] || [ "$STATUS" = "401" ] \
  && pass "Empty body → 400 or 401" \
  || fail "Empty body returned $STATUS (expected 400 or 401)"

# ───────────────────────────────────────────────────────
section "7. Razorpay Webhook — Signature Verification"
# ───────────────────────────────────────────────────────

# No signature header → should be rejected
STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -X POST "$BASE/api/payments/webhook" \
  -H "Content-Type: application/json" \
  -d '{"event":"payment.captured"}')
expect_status "Webhook with no signature → 400" "400" "$STATUS"

# Wrong signature → should be rejected
STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
  -X POST "$BASE/api/payments/webhook" \
  -H "Content-Type: application/json" \
  -H "x-razorpay-signature: invalidsignature123" \
  -d '{"event":"payment.captured"}')
expect_status "Webhook with wrong signature → 400" "400" "$STATUS"

# ───────────────────────────────────────────────────────
section "8. Error Response — No Stack Traces Leaked"
# ───────────────────────────────────────────────────────

BODY=$(curl -s \
  -X POST "$BASE/api/subscribe" \
  -H "Origin: http://localhost:5173" \
  -H "Content-Type: application/json" \
  -d '{"plan":"invalid"}')

echo "$BODY" | grep -qi "prisma\|stack\|at Object\|node_modules\|TypeError" \
  && fail "Error response leaks internal details: $BODY" \
  || pass "Error response does not leak stack trace or Prisma internals"

# ───────────────────────────────────────────────────────
section "9. .env / Secrets Hygiene"
# ───────────────────────────────────────────────────────

[ -f ".env" ] && git check-ignore -q .env \
  && pass ".env is in .gitignore" \
  || fail ".env is NOT ignored by git — risk of secret leak"

[ -f ".env.local" ] && git check-ignore -q .env.local \
  && pass ".env.local is in .gitignore" \
  || fail ".env.local is NOT ignored by git"

# Check for secrets accidentally hardcoded in source
FOUND=$(grep -r "rzp_live_\|rzp_test_" --include="*.ts" --include="*.js" \
  --exclude-dir=node_modules --exclude-dir=.next -l 2>/dev/null)
[ -z "$FOUND" ] \
  && pass "No Razorpay keys hardcoded in source files" \
  || fail "Razorpay key found hardcoded in: $FOUND"

FOUND=$(grep -r "DEMO_MODE\s*=\s*true\|SKIP_AUTH\s*=\s*true\|DEBUG_MODE\s*=\s*true" \
  --include="*.ts" --include="*.js" \
  --exclude-dir=node_modules --exclude-dir=.next -l 2>/dev/null)
[ -z "$FOUND" ] \
  && pass "No debug bypass flags enabled in source" \
  || fail "Debug bypass flag found in: $FOUND"

# ───────────────────────────────────────────────────────
section "10. CORS Response Headers Present"
# ───────────────────────────────────────────────────────

CORS_HEADER=$(curl -s -I \
  -H "Origin: http://localhost:5173" \
  "$BASE/api/health" | grep -i "access-control-allow-origin")

[ -n "$CORS_HEADER" ] \
  && pass "Access-Control-Allow-Origin header present: $CORS_HEADER" \
  || fail "Access-Control-Allow-Origin header missing on API response"

# ═══════════════════════════════════════════════════════
echo -e "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo -e "  Results: ${GREEN}$PASS passed${NC}  ${RED}$FAIL failed${NC}"
echo -e "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"

if [ $FAIL -gt 0 ]; then
  echo -e "${RED}  Some checks failed. Fix the issues above before deploying.${NC}\n"
  exit 1
else
  echo -e "${GREEN}  All security checks passed. Safe to deploy.${NC}\n"
  exit 0
fi
