# Quick check that the local API is reachable (run while run-backend.ps1 is active)
$url = "http://127.0.0.1:9090/actuator/health"
try {
    $r = Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 5
    Write-Host "Backend is UP on port 9090 (HTTP $($r.StatusCode))" -ForegroundColor Green
    Write-Host "You can log in at http://localhost:3000/login" -ForegroundColor Cyan
} catch {
    Write-Host "Backend is NOT reachable on port 9090." -ForegroundColor Red
    Write-Host "Start it with: .\run-backend.ps1" -ForegroundColor Yellow
    Write-Host "Wait until the log shows: Tomcat started on port(s): 9090" -ForegroundColor Yellow
    exit 1
}
