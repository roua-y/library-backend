# Library Management System - Complete Setup Guide

## Step-by-Step Setup (Recommended)

### Step 1: Start MySQL in Docker

```bash
# Navigate to project directory
cd c:\Users\MSI\Downloads\license3\lab4Sring_Boot

# Start only MySQL (not the backend)
docker-compose up -d mysql
```

Wait for MySQL to be ready (about 10-15 seconds):
```bash
docker logs library-mysql
```

You should see: `ready for connections`

### Step 2: Test MySQL Connection (Optional)

```bash
docker exec -it library-mysql mysql -u library -proot librarydb
```

If connected successfully, type `exit;` to exit MySQL shell.

### Step 3: Check if Java is Installed

```bash
java -version
```

**If Java is NOT installed:**

#### Option A: Install Java 17
Download and install Java 17 from: https://adoptium.net/temurin/releases/

After installation, verify:
```bash
java -version
```

#### Option B: Use Docker for Backend (Alternative)
If you prefer not to install Java, see "Alternative: Full Docker Setup" section below.

### Step 4: Start Spring Boot Backend

Using Maven Wrapper (no Maven installation needed):

**Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**If you see "JAVA_HOME is not defined" error:**
1. Find your Java installation path (e.g., `C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot`)
2. Set JAVA_HOME temporarily:
   ```bash
   $env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot"
   .\mvnw.cmd spring-boot:run
   ```

The backend will start on **http://localhost:8080**

### Step 5: Access the Application

✅ **Frontend**: http://localhost:5174 (already running!)  
✅ **Backend API**: http://localhost:8080  
✅ **MySQL**: localhost:3308 (running in Docker)

---

## Alternative: Full Docker Setup

If you want everything in Docker (no local Java needed):

### Step 1: Create Simpler Dockerfile

The current build might be failing. Let's use a pre-built JAR approach:

**First, build the JAR locally using Maven wrapper:**

```bash
# If you have Java installed:
.\mvnw.cmd clean package -DskipTests

# The JAR will be created in: target\lab4Sring_Boot-0.0.1-SNAPSHOT.jar
```

### Step 2: Create Simple Dockerfile

Create a new file `Dockerfile.simple`:

```dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Step 3: Update docker-compose.yml

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: library-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: librarydb
      MYSQL_USER: library
      MYSQL_PASSWORD: root
    ports:
      - "3308:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-proot"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - library-network

  backend:
    build:
      context: .
      dockerfile: Dockerfile.simple
    container_name: library-backend
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/librarydb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
      SPRING_DATASOURCE_USERNAME: library
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      mysql:
        condition: service_healthy
    networks:
      - library-network

volumes:
  mysql-data:

networks:
  library-network:
    driver: bridge
```

### Step 4: Build and Run

```bash
# Build JAR first (if not done already)
.\mvnw.cmd clean package -DskipTests

# Start everything
docker-compose up -d
```

---

## Quick Reference: Common Commands

### Check Running Containers
```bash
docker ps
```

### View Backend Logs
```bash
docker logs library-backend -f
```

### View MySQL Logs
```bash
docker logs library-mysql -f
```

### Stop Everything
```bash
docker-compose down
```

### Restart Backend Only
```bash
docker-compose restart backend
```

### Access MySQL Shell
```bash
docker exec -it library-mysql mysql -u library -proot librarydb
```

---

## Troubleshooting

### Error: "JAVA_HOME is not defined"
**Solution**: Install Java 17 or set JAVA_HOME environment variable.

### Error: "No such container: library-mysql"
**Solution**: MySQL container is not running. Start it with:
```bash
docker-compose up -d mysql
```

### Error: "Port 3308 is already in use"
**Solution**: Another MySQL is running on that port. Stop it or change the port in docker-compose.yml.

### Error: "Port 8080 is already in use"
**Solution**: Another application is using port 8080. Stop it or change the port.

### Backend won't connect to MySQL
**Solution**: 
1. Make sure MySQL container is running: `docker ps`
2. Check MySQL logs: `docker logs library-mysql`
3. Wait a few seconds for MySQL to initialize
4. Restart backend: `docker-compose restart backend` (or `.\mvnw.cmd spring-boot:run`)

### Frontend shows CORS errors
**Solution**: Make sure backend is running on port 8080 and CORS is configured correctly (already done).

---

## Testing the Connection

Once everything is running, test the API:

### Using PowerShell:
```powershell
# Test backend health
Invoke-RestMethod -Uri "http://localhost:8080/books" -Method Get

# Create an author
$author = @{name="John Doe"; email="john@example.com"} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/authors" -Method Post -Body $author -ContentType "application/json"

# Get all authors
Invoke-RestMethod -Uri "http://localhost:8080/authors" -Method Get
```

---

## Current Status

✅ **MySQL Docker container** - Created and configured  
✅ **Frontend** - Running on http://localhost:5174  
⏳ **Backend** - Needs to be started (see Step 4 above)

Choose either:
- **Option 1 (Recommended)**: Run backend locally with Maven wrapper
- **Option 2**: Run backend in Docker (requires building JAR first)
