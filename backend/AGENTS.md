# CineShow Backend - AI Agent Guidelines

## Architecture Overview
- **Spring Boot 3.1.5** microservice with multi-database architecture
- **PostgreSQL** for relational data (users, roles, movies, theatres, shows) via JDBC/JdbcTemplate
- **MongoDB** for transactional data (bookings, reviews) via Spring Data MongoDB
- JWT-based authentication with Spring Security
- MapStruct for entity-DTO mapping, Flyway for schema migrations

## Key Patterns & Conventions

### Entity Design
- All entities use `String csid` (UUID) as primary key, generated in repositories
- Relational entities map to BIGINT database IDs but expose String csids externally
- Shows store seat bookings as `boolean[][] isSeatBooked` (10x10 default grid)
- MongoDB entities reference relational data by csid strings

### Data Flow
- Controllers → DTOs (MapStruct mapped) → Services → Repositories → Entities
- Example: `UserController.createUser()` uses `UserMapper.toEntity(UserDtos.UserCreateRequest)`
- Bookings stored in MongoDB reference PostgreSQL entities by csid

### Authentication & Security
- JWT tokens via `/auth/generate-token` endpoint
- Public endpoints: `/auth/generate-token`, `/user`, `/v3/api-docs/**`, `/swagger-ui/**`
- CORS configured for `http://localhost:*` origins
- Passwords encoded with BCrypt

### Database Operations
- Flyway migrations in `src/main/resources/db/migration/`
- PostgreSQL profile disables MongoDB auto-configuration
- Seat booking updates both `shows.seat_booked_map` (JSONB) and `bookings` collection

### Development Workflow
- Build: `./mvnw clean install`
- Run: `./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres`
- Test: `./mvnw test`
- Requires `POSTGRES_URL`, `POSTGRES_USER`, `POSTGRES_PASSWORD` environment variables

### Code Structure Reference
- `infrastructure/entity/` - PostgreSQL entities with builder patterns
- `infrastructure/nosql/entity/` - MongoDB documents
- `infrastructure/repository/` - JDBC repositories with custom RowMappers
- `core/service/impl/` - Business logic implementations
- `api/controller/` - REST endpoints with MapStruct DTOs</content>
<parameter name="filePath">/Users/ris_adrsh/Documents/AceJava/cine-show/backend/AGENTS.md
