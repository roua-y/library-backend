# Start Library Management System Backend

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "Starting Library Management System Backend" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Check if Java is available
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java found!" -ForegroundColor Green
    Write-Host $javaVersion[0] -ForegroundColor Gray
    Write-Host ""
}
catch {
    Write-Host "ERROR: Java not found!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please install Java 17 from: https://adoptium.net/temurin/releases/" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "After installation, restart PowerShell and try again." -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Start MySQL container
Write-Host "Starting MySQL container..." -ForegroundColor Yellow
docker-compose up -d mysql
Write-Host ""

# Wait for MySQL
Write-Host "Waiting for MySQL to be ready..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

# Test MySQL connection
Write-Host "Testing MySQL connection..." -ForegroundColor Yellow
try {
    $result = docker exec library-mysql mysqladmin ping -h localhost -u root -proot 2>&1
    if ($result -match "alive") {
        Write-Host "✓ MySQL is ready!" -ForegroundColor Green
    }
}
catch {
    Write-Host "⚠ MySQL might not be fully ready yet, but continuing..." -ForegroundColor Yellow
}
Write-Host ""

# Start Spring Boot
Write-Host "Starting Spring Boot application..." -ForegroundColor Yellow
Write-Host "This may take 30-60 seconds on first run..." -ForegroundColor Gray
Write-Host ""
Write-Host "Once started, the backend will be available at: http://localhost:8080" -ForegroundColor Cyan
Write-Host "Press Ctrl+C to stop the backend" -ForegroundColor Gray
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

.\mvnw.cmd spring-boot:run
