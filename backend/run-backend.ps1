# PowerShell script to run the Spring Boot backend
# Secrets are loaded from backend/.env (gitignored)
# Copy backend/.env.example to backend/.env and fill in your real values

# ─── Load environment variables from .env ────────────────────────────────────
$envFile = Join-Path $PSScriptRoot ".env"
if (-not (Test-Path $envFile)) {
    Write-Host "ERROR: backend/.env not found." -ForegroundColor Red
    Write-Host "Copy backend/.env.example to backend/.env and fill in your real values." -ForegroundColor Yellow
    exit 1
}

Get-Content $envFile | ForEach-Object {
    # Skip blank lines and comments
    if ($_ -match '^\s*$' -or $_ -match '^\s*#') { return }
    # Parse KEY=VALUE pairs
    if ($_ -match '^([^=]+)=(.*)$') {
        $key   = $matches[1].Trim()
        $value = $matches[2].Trim()
        [System.Environment]::SetEnvironmentVariable($key, $value, "Process")
    }
}
Write-Host ".env loaded successfully." -ForegroundColor Green
# ─────────────────────────────────────────────────────────────────────────────

# ─── Validate Java ────────────────────────────────────────────────────────────
if (-not $env:JAVA_HOME -or -not (Test-Path "$env:JAVA_HOME\bin\java.exe")) {
    Write-Host "ERROR: JAVA_HOME is not set or java.exe was not found." -ForegroundColor Red
    Write-Host "Set JAVA_HOME to a JDK 8+ folder, then run this script again." -ForegroundColor Yellow
    exit 1
}
# ─────────────────────────────────────────────────────────────────────────────

# ─── Validate WAR ─────────────────────────────────────────────────────────────
$war = Join-Path $PSScriptRoot "target\v-learning-2.2.0-SNAPSHOT.war"
if (-not (Test-Path $war)) {
    Write-Host "ERROR: WAR not found at $war" -ForegroundColor Red
    Write-Host "Build first: .\mvnw.cmd clean package -DskipTests" -ForegroundColor Yellow
    exit 1
}
# ─────────────────────────────────────────────────────────────────────────────

Write-Host ""
Write-Host "Starting v-learning backend (local only)..." -ForegroundColor Green
Write-Host "  - Takes about 45-60 seconds on first start" -ForegroundColor Yellow
Write-Host "  - Wait until you see: Tomcat started on port(s): 9090" -ForegroundColor Yellow
Write-Host "  - Keep THIS window open while using the web app" -ForegroundColor Yellow
Write-Host "  - Then test: http://127.0.0.1:9090" -ForegroundColor Cyan
Write-Host ""

& "$env:JAVA_HOME\bin\java.exe" -jar $war
