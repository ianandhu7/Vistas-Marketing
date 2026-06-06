#!/bin/bash

# ═══════════════════════════════════════════════════════
#  Vistas Learning — Safe Cleanup Script
#  Run from your PROJECT ROOT (the folder containing
#  backend/, vistas-learning/, and the Vue src/ folder)
#
#  Usage:
#    bash cleanup.sh          ← dry run (shows what WILL be deleted)
#    bash cleanup.sh --delete ← actually deletes the files
# ═══════════════════════════════════════════════════════

DRY_RUN=true
[ "$1" = "--delete" ] && DRY_RUN=false

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

deleted=0
skipped=0

remove() {
  local path="$1"
  local reason="$2"
  if [ -e "$path" ]; then
    if $DRY_RUN; then
      echo -e "${RED}  WILL DELETE${NC}  $path  ${YELLOW}← $reason${NC}"
    else
      rm -rf "$path"
      echo -e "${RED}  DELETED${NC}      $path  ${YELLOW}← $reason${NC}"
    fi
    ((deleted++))
  fi
}

keep() {
  local path="$1"
  local reason="$2"
  if [ -e "$path" ] || [ -d "$path" ]; then
    echo -e "${GREEN}  KEEP${NC}         $path  ${CYAN}← $reason${NC}"
    ((skipped++))
  fi
}

echo ""
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}  Vistas Learning — Cleanup Plan${NC}"
$DRY_RUN && echo -e "${YELLOW}  DRY RUN — nothing deleted yet. Pass --delete to execute.${NC}"
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"


# ───────────────────────────────────────────────────────
echo -e "\n${CYAN}── backend/ ──${NC}"
# ───────────────────────────────────────────────────────

remove "backend/AGENTS.md"              "AI agent instructions — not needed in production"
remove "backend/CLAUDE.md"              "AI coding context file — not needed in production"
remove "backend/README.md"              "Dev notes — move content to main README if needed"
remove "backend/mock-db.json"           "Mock database — only for early dev, not used in flow"
remove "backend/test-db.js"             "Throwaway DB test script — not part of app flow"
remove "backend/.env.example"           "Template file — keep only if onboarding new devs"
remove "backend/lib/rateLimit.js"       "Old JS version — rateLimit.ts is the active one"
remove "backend/vistas-security-tests.sh" "Test script — move to project root, not inside backend/"

keep   "backend/lib/cors.ts"            "Active helper — imported by middleware.ts"
keep   "backend/lib/securityHeaders.ts" "Active helper — imported by middleware.ts"
keep   "backend/lib/apiResponse.js"     "Shared response helper — used by routes"
keep   "backend/lib/authMiddleware.js"  "Auth middleware — used by routes"
keep   "backend/lib/env.js"             "Env validation — needed at startup"
keep   "backend/lib/firebaseAdmin.js"   "Firebase Admin init — used by auth routes"
keep   "backend/lib/prisma.js"          "Prisma client singleton — used everywhere"
keep   "backend/lib/rateLimit.ts"       "Active rate limiter — used by middleware.ts"
keep   "backend/lib/razorpay.js"        "Razorpay helpers — used by payment routes"
keep   "backend/lib/startup.js"         "Startup checks — runs on server init"
keep   "backend/middleware.ts"          "Core security middleware — critical"
keep   "backend/.env"                   "Live secrets — never delete"
keep   "backend/.env.local"             "Local overrides — never delete"
keep   "backend/.gitignore"             "Git ignore rules — required"
keep   "backend/Dockerfile"             "Docker build — needed if deploying via Docker"
keep   "backend/.dockerignore"          "Docker ignore — needed with Dockerfile"
keep   "backend/instrumentation.ts"     "Next.js instrumentation — used for startup hooks"
keep   "backend/next.config.ts"         "Next.js config — required"
keep   "backend/next-env.d.ts"          "Next.js TS types — required"
keep   "backend/package.json"           "Dependencies — required"
keep   "backend/package-lock.json"      "Lock file — required"
keep   "backend/tsconfig.json"          "TypeScript config — required"
keep   "backend/eslint.config.mjs"      "Linting rules — keep for code quality"
keep   "backend/postcss.config.mjs"     "PostCSS — keep if using Tailwind in backend"
keep   "backend/prisma/"                "Prisma schema and migrations — critical"
keep   "backend/app/"                   "All API route handlers — critical"
keep   "backend/.next/"                 "Build output — auto-generated, do not touch"
keep   "backend/node_modules/"          "Dependencies — do not touch"
keep   "backend/public/"                "Static assets — keep"


# ───────────────────────────────────────────────────────
echo -e "\n${CYAN}── vistas-learning/ (Next.js frontend) ──${NC}"
# ───────────────────────────────────────────────────────

remove "vistas-learning/AGENTS.md"           "AI agent instructions — not needed in production"
remove "vistas-learning/CLAUDE.md"           "AI coding context file — not needed in production"
remove "vistas-learning/README.md"           "Dev notes — safe to remove or merge"
remove "vistas-learning/RENDER_DEPLOYMENT.md" "Deployment notes — safe after reading"
remove "vistas-learning/.env.production"     "CAUTION: only delete after confirming vars are in hosting dashboard (Render/Vercel)"

keep   "vistas-learning/.env"                "Live secrets — never delete"
keep   "vistas-learning/.env.local"          "Local overrides — never delete"
keep   "vistas-learning/.gitignore"          "Git ignore — required"
keep   "vistas-learning/app/"                "App router pages — critical"
keep   "vistas-learning/lib/"                "Shared utilities — critical"
keep   "vistas-learning/models/"             "OTP.js, User.js — used by auth flow"
keep   "vistas-learning/public/"             "Static assets — keep"
keep   "vistas-learning/next.config.ts"      "Next.js config — required"
keep   "vistas-learning/next-env.d.ts"       "TS types — required"
keep   "vistas-learning/package.json"        "Dependencies — required"
keep   "vistas-learning/package-lock.json"   "Lock file — required"
keep   "vistas-learning/tsconfig.json"       "TypeScript config — required"
keep   "vistas-learning/eslint.config.mjs"   "Linting — keep"
keep   "vistas-learning/postcss.config.mjs"  "PostCSS — keep"
keep   "vistas-learning/Dockerfile"          "Docker build — keep if deploying via Docker"
keep   "vistas-learning/.dockerignore"       "Docker ignore — keep"
keep   "vistas-learning/docker-compose.yml"  "Docker compose — keep if used"
keep   "vistas-learning/index.html"          "Entry point — critical"
keep   "vistas-learning/.next/"              "Build output — do not touch"
keep   "vistas-learning/node_modules/"       "Dependencies — do not touch"


# ───────────────────────────────────────────────────────
echo -e "\n${CYAN}── Vue frontend (src/ root) ──${NC}"
# ───────────────────────────────────────────────────────

remove "README.md"                      "Root readme — safe to remove or keep as project overview"
remove "postcss.config.js"              "Duplicate — postcss.config.mjs is the active one"

keep   "src/"                           "All Vue source files — critical"
keep   "dist/"                          "Build output — do not delete manually"
keep   "public/"                        "Static assets — keep"
keep   "index.html"                     "Vue entry point — critical"
keep   "vite.config.js"                 "Vite build config — required"
keep   "tailwind.config.js"             "Tailwind config — required"
keep   "package.json"                   "Dependencies — required"
keep   "package-lock.json"              "Lock file — required"
keep   ".gitignore"                     "Git ignore — required"
keep   "node_modules/"                  "Dependencies — do not touch"


# ───────────────────────────────────────────────────────
echo -e "\n${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "  Files to delete: ${RED}$deleted${NC}"
echo -e "  Files to keep:   ${GREEN}$skipped${NC}"
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}\n"

if $DRY_RUN; then
  echo -e "${YELLOW}  This was a dry run. To actually delete, run:${NC}"
  echo -e "  ${CYAN}bash cleanup.sh --delete${NC}\n"
fi
