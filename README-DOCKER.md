# Library Management System - Docker Setup

## Quick Start

### Prerequisites
- Docker Desktop installed and running
- Docker Compose installed

### Starting the Application

1. **Start all services with Docker Compose:**
   ```bash
   docker-compose up -d --build
   ```

   This will:
   - Start MySQL database on port 3308
   - Build and start the Spring Boot backend on port 8080
   - Wait for MySQL to be healthy before starting the backend

2. **Start the Frontend (in a separate terminal):**
   ```bash
   cd library-frontend
   npm install  # Only needed first time
   npm run dev
   ```

   The frontend will be available at: http://localhost:5173 or http://localhost:5174

### Stopping the Application

```bash
docker-compose down
```

To remove volumes as well (clears all data):
```bash
docker-compose down -v
```

### Viewing Logs

**Backend logs:**
```bash
docker logs library-backend -f
```

**MySQL logs:**
```bash
docker logs library-mysql -f
```

**Both services:**
```bash
docker-compose logs -f
```

### Checking Service Status

```bash
docker-compose ps
```

### Accessing MySQL Directly

```bash
docker exec -it library-mysql mysql -u library -proot librarydb
```

## Services

| Service  | Container Name   | Port  | URL                    |
|----------|------------------|-------|------------------------|
| MySQL    | library-mysql    | 3308  | localhost:3308         |
| Backend  | library-backend  | 8080  | http://localhost:8080  |
| Frontend | (npm run dev)    | 5173/5174 | http://localhost:5174 |

## Environment Variables

The backend uses these environment variables (set in docker-compose.yml):

- `SPRING_DATASOURCE_URL`: MySQL connection string
- `SPRING_DATASOURCE_USERNAME`: library
- `SPRING_DATASOURCE_PASSWORD`: root

## Database

- **Database**: librarydb
- **User**: library
- **Password**: root
- **Root Password**: root

The database will be created automatically if it doesn't exist.

## API Endpoints

The backend exposes these endpoints:

### Books
- `GET /books` - Get all books
- `POST /books` - Create a new book
- `GET /books/category?category={name}` - Get books by category

### Authors
- `GET /authors` - Get all authors
- `POST /authors` - Create a new author

### Publishers
- `GET /publishers` - Get all publishers
- `POST /publishers` - Create a new publisher

### Tags
- `GET /tags` - Get all tags
- `POST /tags` - Create a new tag

## Troubleshooting

### Backend won't start
1. Check if MySQL is healthy:
   ```bash
   docker ps
   ```

2. View backend logs:
   ```bash
   docker logs library-backend
   ```

### Frontend can't connect to backend
1. Make sure backend is running on port 8080
2. Check CORS configuration in `WebConfig.java`
3. Verify the frontend is using the correct API URL in `services/api.js`

### MySQL connection errors
1. Wait for MySQL to be fully started (check with `docker logs library-mysql`)
2. Verify the database credentials match in docker-compose.yml and application.properties

### Port conflicts
If ports are already in use:
- MySQL: Change `3308:3306` to another port in docker-compose.yml
- Backend: Change `8080:8080` to another port in docker-compose.yml

## Development

### Rebuilding after code changes

```bash
docker-compose up -d --build backend
```

This rebuilds only the backend service.

### Hot reload
The frontend has hot reload enabled by default with Vite.

For backend hot reload during development, you can:
1. Use Spring Boot DevTools (already in pom.xml)
2. Or run the backend locally instead of in Docker

## Production Deployment

For production:
1. Remove `-DskipTests` from the Dockerfile to run tests during build
2. Change `spring.jpa.hibernate.ddl-auto` to `validate` in application.properties
3. Use proper secrets management instead of hardcoded passwords
4. Set up proper database backups
5. Use environment-specific configuration files

## Data Persistence

The MySQL data is persisted in a Docker volume named `lab4sring_boot_mysql-data`. This means your data will survive container restarts but will be deleted if you run `docker-compose down -v`.
