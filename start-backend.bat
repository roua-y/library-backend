@echo off
echo ============================================
echo Starting Library Management System Backend
echo ============================================
echo.

REM Check if JAVA_HOME is set
if "%JAVA_HOME%"=="" (
    echo WARNING: JAVA_HOME is not set!
    echo.
    echo Please install Java 17 from: https://adoptium.net/temurin/releases/
    echo.
    echo Or set JAVA_HOME manually with:
    echo set JAVA_HOME=C:\Path\To\Your\Java\Installation
    echo.
    pause
    exit /b 1
)

echo JAVA_HOME: %JAVA_HOME%
echo.
echo Checking Java version...
java -version
echo.

echo Starting MySQL container if not running...
docker-compose up -d mysql
echo.

echo Waiting for MySQL to be ready...
timeout /t 5 /nobreak > nul
echo.

echo Starting Spring Boot application...
echo This may take a minute on first run...
echo.

.\mvnw.cmd spring-boot:run

pause
