# CineShow Backend - JWT Authentication & Spring Security Guide

## Table of Contents
1. [Introduction](#introduction)
2. [Bearer Tokens](#bearer-tokens)
3. [Authentication Flow](#authentication-flow)
4. [JWT Filter Chain](#jwt-filter-chain)
5. [Key Components](#key-components)
6. [Authorization Rules](#authorization-rules)
7. [Error Handling](#error-handling)
8. [Complete Request Lifecycle](#complete-request-lifecycle)
9. [Testing Guide](#testing-guide)

---

## Introduction

This document explains how JWT-based authentication works in the CineShow Spring Boot backend application. The system uses stateless authentication where clients must provide a valid JWT token in every request to access protected endpoints.

### Key Technologies
- **Framework**: Spring Boot 3.1.5
- **Security**: Spring Security with JWT
- **Token Type**: Bearer tokens
- **Session Management**: Stateless (SessionCreationPolicy.STATELESS)

---

## Bearer Tokens

### What is a Bearer Token?

A Bearer token is a type of authentication credential used in HTTP headers. The format is:
```
Authorization: Bearer <token>
```

### Why Bearer Tokens?

- **Standard Format**: RFC 6750 standard for OAuth 2.0 Bearer Token Usage
- **Stateless**: No server-side session storage needed
- **Simple**: Easy to include in request headers
- **Scalable**: Works well for microservices and distributed systems

### Other Token Types

| Token Type | Usage | Use Case |
|-----------|-------|----------|
| **Bearer** | OAuth 2.0/JWT | JWT-based APIs (your app) |
| **Basic** | `Authorization: Basic base64(user:pass)` | HTTP Basic Auth (less secure) |
| **Digest** | Older HTTP authentication | Legacy systems |
| **Token** | Custom implementation | Alternative to Bearer |

### Example Bearer Token Usage

```bash
# Request with Bearer token
curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
     http://localhost:8080/auth/current-user
```

---

## Authentication Flow

### Complete Authentication Lifecycle

```
1. USER LOGIN
   ├─ POST /auth/generate-token
   ├─ Request body: { "username": "testuser", "password": "testpass" }
   ├─ JwtAuthenticationFilter.doFilterInternal() runs
   │  └─ No Bearer token expected for login
   ├─ AuthenticationManager validates credentials
   ├─ JwtUtils generates JWT token
   └─ Response: { "token": "eyJhbGc..." }

2. SUBSEQUENT REQUESTS
   ├─ Client stores the token
   ├─ Each request includes: Authorization: Bearer <token>
   └─ Repeats authentication process (see below)

3. ACCESSING PROTECTED ENDPOINT
   ├─ GET /auth/current-user
   ├─ Header: Authorization: Bearer <token>
   └─ (See "Complete Request Lifecycle" section)
```

### Principal Object

**What is Principal?**
- Represents the authenticated user/entity making the request
- Automatically injected into controller methods
- Provides access to authenticated user information

**How it Gets the Username:**

```java
// In Controller
public ResponseEntity<ApiResponse<UserDtos.UserResponse>> getCurrentUser(Principal principal) {
    String username = principal.getName();  // Gets authenticated username
    // ...
}
```

**How Username is Embedded:**

1. JWT Token contains username claim
2. JwtAuthenticationFilter extracts username from token
3. Creates `UsernamePasswordAuthenticationToken` with UserDetails
4. Stores in `SecurityContextHolder`
5. Spring injects as `Principal` in controller method

```
JWT Token
    ↓
Extract username: "testuser"
    ↓
Load UserDetails from database
    ↓
Create UsernamePasswordAuthenticationToken(userDetails, ...)
    ↓
SecurityContextHolder.getContext().setAuthentication(authenticationToken)
    ↓
Spring injects as Principal in controller
    ↓
principal.getName() → "testuser"
```

---

## JWT Filter Chain

### JwtAuthenticationFilter Overview

**File**: `src/main/java/com/cinema/cineshow/infrastructure/security/JwtAuthenticationFilter.java`

- **Type**: `OncePerRequestFilter` (guaranteed to run once per request)
- **Extends**: Spring's filter mechanism
- **Called For**: **EVERY** HTTP request to your application

### Filter Flow

```
HTTP Request arrives
    ↓
JwtAuthenticationFilter.doFilterInternal() ← RUNS FOR EVERY REQUEST
    ├─ 1. Looks for "Authorization" header
    ├─ 2. Checks if it starts with "Bearer "
    ├─ 3. If yes, extracts the token (substring after "Bearer ")
    ├─ 4. Validates JWT token using JwtUtils
    ├─ 5. If valid:
    │  ├─ Extracts username from token claims
    │  ├─ Loads UserDetails from database
    │  ├─ Creates UsernamePasswordAuthenticationToken
    │  └─ Sets in SecurityContextHolder
    ├─ 6. If invalid/missing: Does nothing, auth stays null
    ├─ 7. Calls filterChain.doFilter(request, response)
    │
    └─ Request continues to next filter
```

### What `filterChain.doFilter()` Does

**Critical Line**: `filterChain.doFilter(request, response);` at the end of `doFilterInternal()`

**Purpose**: Passes the request to the next component in the filter chain

**Without this call**: 
- Request gets stuck in the filter
- Controller never executes
- Client gets no response (timeout)

**With this call**:
- Request continues through filter chain
- Reaches DispatcherServlet
- Gets routed to correct controller
- Response flows back through filters

---

## Key Components

### 1. MySecurityConfig.java

**Location**: `src/main/java/com/cinema/cineshow/infrastructure/security/MySecurityConfig.java`

**Purpose**: Central configuration for all security settings and filters

**Key Configuration:**

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(AbstractHttpConfigurer::disable)                    // Disable CSRF (stateless JWT)
            .cors(cors -> cors.configurationSource(...))             // Enable CORS
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(PUBLIC_URLS).permitAll()        // Public endpoints
                    .anyRequest().authenticated()                     // Protected endpoints
            )
            .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // No sessions
            .authenticationProvider(daoAuthenticationProvider())      // Database-based auth
            .addFilterBefore(jwtAuthenticationFilter, 
                    UsernamePasswordAuthenticationFilter.class)       // JWT filter position
            .exceptionHandling(exception -> 
                    exception.authenticationEntryPoint(unauthorizedHandler))  // Error response
            .build();
}
```

### 2. Public URLs Configuration

```java
private static final String[] PUBLIC_URLS = {
    "/auth/generate-token",    // User login - no token needed
    "/users",                  // User registration - no token needed
    "/v3/api-docs/**",         // OpenAPI docs
    "/swagger-ui/**",          // Swagger UI
    "/swagger-ui.html"         // Swagger HTML
};
```

**These URLs can be accessed WITHOUT authentication.**

### 3. JwtUtils.java

**Purpose**: JWT token generation and validation

**Key Methods**:
- `generateToken(UserDetails userDetails)` - Creates JWT token
- `extractUsername(String token)` - Extracts username claim
- `validateToken(String token, UserDetails userDetails)` - Validates token signature

### 4. JwtAuthenticationEntryPoint.java

**Location**: `src/main/java/com/cinema/cineshow/infrastructure/security/JwtAuthenticationEntryPoint.java`

**Purpose**: Handles authentication failures with proper error responses

**Implements**: `AuthenticationEntryPoint` interface

---

## Authorization Rules

### permitAll() Explained

**What it Does**: Bypasses authentication requirement for specific URLs

**Configuration**:
```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers(PUBLIC_URLS).permitAll()    // Rule 1: These don't need auth
    .anyRequest().authenticated()                 // Rule 2: Everything else needs auth
)
```

### How Authorization Works

**For Public URLs** (e.g., `/auth/generate-token`):

```
Request arrives
    ↓
JwtAuthenticationFilter.doFilterInternal() ← Checks for token (optional)
    ├─ No token? → No problem
    ├─ Token present? → Validates and sets up auth
    ├─ Calls filterChain.doFilter()
    ↓
AuthorizationFilter checks rules:
    ├─ Is URL in PUBLIC_URLS? → YES
    ├─ Rule: .permitAll() → ALLOW ✓
    ↓
Request proceeds to controller
```

**For Protected URLs** (e.g., `/auth/current-user`):

```
Request arrives WITHOUT token
    ↓
JwtAuthenticationFilter.doFilterInternal() ← No token found → No auth set
    ├─ Calls filterChain.doFilter()
    ↓
AuthorizationFilter checks rules:
    ├─ Is URL in PUBLIC_URLS? → NO
    ├─ Rule: .anyRequest().authenticated() → REQUIRES authentication
    ├─ Is user authenticated? → NO → THROW AuthenticationException
    ↓
JwtAuthenticationEntryPoint.commence() ← ERROR HANDLER CALLED
    ├─ Returns 401 Unauthorized
    ├─ Response: { "success": false, "error": { "code": 401, ... } }
```

**With Valid Token**:

```
Request arrives WITH Authorization: Bearer <valid_token>
    ↓
JwtAuthenticationFilter.doFilterInternal()
    ├─ Extracts token
    ├─ Validates JWT
    ├─ Sets SecurityContext with authenticated user
    ├─ Calls filterChain.doFilter()
    ↓
AuthorizationFilter checks rules:
    ├─ Is user authenticated? → YES ✓
    ↓
Request proceeds to controller
```

---

## Error Handling

### JwtAuthenticationEntryPoint

**When Is It Called?**

1. **Request to protected URL without token**
2. **Request with invalid/expired JWT token**
3. **Request with malformed Authorization header**
4. **Any other authentication failure**

**What It Does**:

```java
@Override
public void commence(HttpServletRequest request, HttpServletResponse response, 
                    AuthenticationException authException) throws IOException, ServletException {
    // Set HTTP 401 status code
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    
    // Set response content type to JSON
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    
    // Create standardized error response
    ApiResponse<Void> apiResponse = ApiResponse.error(
        HttpServletResponse.SC_UNAUTHORIZED,
        "UNAUTHORIZED",
        "Unauthorized: " + authException.getMessage()
    );
    
    // Write JSON to response
    objectMapper.writeValue(response.getOutputStream(), apiResponse);
}
```

**Example Response**:

```json
{
    "success": false,
    "error": {
        "code": 401,
        "type": "UNAUTHORIZED",
        "message": "Unauthorized: Full authentication is required to access this resource"
    }
}
```

---

## Complete Filter Chain

### All Filters in Your Application (In Order)

```
1. CorsFilter
   ├─ Configured in: corsConfigurationSource()
   ├─ Allows origins: http://localhost:* and http://127.0.0.1:*
   ├─ Allows methods: GET, POST, PUT, PATCH, DELETE, OPTIONS
   └─ Allows all headers

2. JwtAuthenticationFilter ← YOUR CUSTOM FILTER
   ├─ Validates Bearer tokens
   ├─ Extracts username from JWT
   ├─ Sets up SecurityContext
   └─ Runs BEFORE UsernamePasswordAuthenticationFilter

3. UsernamePasswordAuthenticationFilter (Built-in)
   ├─ Handles form-based login (not used in your JWT-based app)
   ├─ Checks if authentication already exists
   └─ Passes through without action if JWT auth is set

4. AuthorizationFilter (Built-in)
   ├─ Checks URL against your authorizeHttpRequests() rules
   ├─ Applies .permitAll() or .authenticated() rules
   ├─ Throws AuthenticationException if auth required but missing
   └─ Calls JwtAuthenticationEntryPoint on failure

5. ExceptionTranslationFilter (Built-in)
   ├─ Catches AuthenticationException
   ├─ Calls JwtAuthenticationEntryPoint.commence()
   └─ Handles access denied scenarios

6. Other Spring Security Filters
   ├─ SecurityContextPersistenceFilter
   ├─ LogoutFilter
   └─ FilterSecurityInterceptor

7. DispatcherServlet
   ├─ Routes to correct controller based on URL
   ├─ Injects Principal (from SecurityContextHolder)
   └─ Executes controller method
```

### Request Flow Through Entire Chain

```
HTTP Request → CorsFilter → JwtAuthenticationFilter → 
UsernamePasswordAuthenticationFilter → AuthorizationFilter → 
ExceptionTranslationFilter → ... → DispatcherServlet → 
Your Controller → Response flows back through all filters
```

---

## Complete Request Lifecycle

### Example: GET /auth/current-user

**Prerequisites:**
1. Valid JWT token obtained from `/auth/generate-token`
2. Token included in request header: `Authorization: Bearer <token>`

**Step-by-Step Flow:**

```
1. CLIENT SENDS REQUEST
   GET /auth/current-user HTTP/1.1
   Host: localhost:8080
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

2. SPRING BOOT RECEIVES REQUEST
   ├─ Tomcat receives HTTP request
   └─ Routes to Spring Security's FilterChainProxy

3. CORS FILTER RUNS
   ├─ Checks if Origin (http://localhost:4200) is allowed
   └─ Checks CORS headers

4. JWT AUTHENTICATION FILTER RUNS ← doFilterInternal()
   ├─ Extracts "Authorization" header
   ├─ Checks if it starts with "Bearer "
   ├─ Extracts token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
   ├─ Calls jwtUtils.extractUsername(token)
   │  └─ Returns: "testuser"
   ├─ Calls jwtUtils.validateToken(token, userDetails)
   │  └─ Validates JWT signature and expiration
   ├─ Loads UserDetails from database
   ├─ Creates UsernamePasswordAuthenticationToken
   ├─ Sets: SecurityContextHolder.getContext().setAuthentication(authToken)
   └─ Calls filterChain.doFilter(request, response) ← PASSES FORWARD

5. USERNAME PASSWORD AUTHENTICATION FILTER RUNS
   ├─ Checks if authentication already exists
   ├─ Sees authentication is set by JWT filter
   └─ Does nothing, passes through

6. AUTHORIZATION FILTER RUNS
   ├─ Gets request URI: "/auth/current-user"
   ├─ Checks if "/auth/current-user" is in PUBLIC_URLS
   │  └─ It's NOT in PUBLIC_URLS
   ├─ Applies rule: .anyRequest().authenticated()
   ├─ Checks: Is user authenticated?
   │  └─ YES (from JWT filter) ✓
   └─ ALLOWS request to continue

7. OTHER SECURITY FILTERS RUN
   ├─ ExceptionTranslationFilter (no exception, passes through)
   └─ Other filters...

8. DISPATCHER SERVLET ROUTES REQUEST
   ├─ Examines URL: "/auth/current-user"
   ├─ Examines method: "GET"
   ├─ Searches registered controllers for matching @RequestMapping/@GetMapping
   ├─ Finds: AuthenticateController.getCurrentUser()
   └─ Prepares to invoke

9. SPRING INJECTS DEPENDENCIES
   ├─ Sees method parameter: Principal principal
   ├─ Gets from SecurityContextHolder: UsernamePasswordAuthenticationToken
   ├─ Injects as Principal ✓
   └─ All dependencies injected

10. CONTROLLER METHOD EXECUTES
    ├─ AuthenticateController.getCurrentUser(Principal principal)
    ├─ Gets username: principal.getName() → "testuser"
    ├─ Calls userDetailsService.loadUserByUsername("testuser")
    ├─ Maps User entity to UserResponse DTO
    ├─ Returns: ResponseEntity<ApiResponse<UserDtos.UserResponse>>
    │  └─ Body: { "success": true, "data": { "csid": "...", "username": "testuser", ... } }
    └─ HTTP 200 OK

11. RESPONSE FLOWS BACK THROUGH FILTERS
    ├─ ExceptionTranslationFilter
    ├─ AuthorizationFilter
    ├─ UsernamePasswordAuthenticationFilter
    ├─ JwtAuthenticationFilter
    ├─ CorsFilter
    └─ Returns to client

12. CLIENT RECEIVES RESPONSE
    HTTP/1.1 200 OK
    Content-Type: application/json
    {
        "success": true,
        "data": {
            "csid": "123e4567-89ab-cdef-0123-456789abcdef",
            "username": "testuser",
            "email": "test@example.com",
            "firstName": "Test",
            "lastName": "User"
        },
        "message": "Current user fetched successfully"
    }
```

### Example: GET /auth/current-user WITHOUT Token

```
REQUEST: GET /auth/current-user (NO Authorization header)

1-3. CorsFilter and earlier filters pass through

4. JWT AUTHENTICATION FILTER
   ├─ requestTokenHeader = request.getHeader("Authorization")
   ├─ requestTokenHeader = null → NO header present
   ├─ username = null → NOT EXTRACTED
   ├─ No authentication set in SecurityContext
   └─ Calls filterChain.doFilter() ← REQUEST CONTINUES

5. UsernamePasswordAuthenticationFilter passes through

6. AUTHORIZATION FILTER
   ├─ Checks: Is "/auth/current-user" in PUBLIC_URLS?
   │  └─ NO
   ├─ Applies rule: .anyRequest().authenticated()
   ├─ Checks: SecurityContextHolder.getAuthentication()
   │  └─ Returns null (no authentication set)
   ├─ THROWS: AuthenticationException("Full authentication is required...")
   └─ BLOCKS REQUEST

7. EXCEPTION TRANSLATION FILTER CATCHES EXCEPTION
   ├─ Recognizes AuthenticationException
   ├─ Calls JwtAuthenticationEntryPoint.commence(request, response, exception)

8. JWT AUTHENTICATION ENTRY POINT HANDLES ERROR
   ├─ Sets response status: 401 Unauthorized
   ├─ Sets content type: application/json
   ├─ Creates error response:
   │  {
   │      "success": false,
   │      "error": {
   │          "code": 401,
   │          "type": "UNAUTHORIZED",
   │          "message": "Unauthorized: Full authentication is required to access this resource"
   │      }
   │  }
   └─ Writes response to output stream

9. CLIENT RECEIVES
   HTTP/1.1 401 Unauthorized
   Content-Type: application/json
   {
       "success": false,
       "error": {
           "code": 401,
           "type": "UNAUTHORIZED",
           "message": "Unauthorized: Full authentication is required to access this resource"
       }
   }
```

---

## Testing Guide

### 1. Get a JWT Token

**Endpoint**: `POST http://localhost:8080/auth/generate-token`

**Request**:
```bash
curl -X POST http://localhost:8080/auth/generate-token \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "testpass"
  }'
```

**Response**:
```json
{
    "success": true,
    "data": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTcxNTczMDAwMCwiZXhwIjoxNzE1ODE2NDAwfQ...."
    },
    "message": "Token generated successfully"
}
```

### 2. Test Protected Endpoint WITH Token

**Endpoint**: `GET http://localhost:8080/auth/current-user`

**Request**:
```bash
curl -X GET http://localhost:8080/auth/current-user \
  -H "Authorization: Bearer <your_token_here>" \
  -H "Accept: application/json"
```

**Success Response (200 OK)**:
```json
{
    "success": true,
    "data": {
        "csid": "123e4567-89ab-cdef-0123-456789abcdef",
        "username": "testuser",
        "email": "test@example.com",
        "firstName": "Test",
        "lastName": "User"
    },
    "message": "Current user fetched successfully"
}
```

### 3. Test Protected Endpoint WITHOUT Token

**Request**:
```bash
curl -X GET http://localhost:8080/auth/current-user \
  -H "Accept: application/json"
```

**Error Response (401 Unauthorized)**:
```json
{
    "success": false,
    "error": {
        "code": 401,
        "type": "UNAUTHORIZED",
        "message": "Unauthorized: Full authentication is required to access this resource"
    }
}
```

### 4. Test Invalid/Expired Token

**Request**:
```bash
curl -X GET http://localhost:8080/auth/current-user \
  -H "Authorization: Bearer invalid_token_here" \
  -H "Accept: application/json"
```

**Response (401 Unauthorized)**:
```json
{
    "success": false,
    "error": {
        "code": 401,
        "type": "UNAUTHORIZED",
        "message": "Unauthorized: <token_validation_error>"
    }
}
```

### 5. Test Public Endpoint WITHOUT Token

**Endpoint**: `POST http://localhost:8080/users` (User Registration)

**Response**: Should work WITHOUT Authorization header (returns 200/201)

---

## Configuration Files

### File Locations and Purposes

| File | Location | Purpose |
|------|----------|---------|
| **MySecurityConfig.java** | `infrastructure/security/` | Central security configuration |
| **JwtAuthenticationFilter.java** | `infrastructure/security/` | JWT token validation filter |
| **JwtAuthenticationEntryPoint.java** | `infrastructure/security/` | Error response handler |
| **JwtUtils.java** | `infrastructure/security/` | Token generation and validation |
| **AuthenticateController.java** | `api/controller/` | Login and current-user endpoints |
| **UserDetailsServiceImpl.java** | `core/service/impl/` | Loads user from database |

### Key Properties

**File**: `src/main/resources/application-postgres.properties`

```properties
# JWT Configuration (if externalized)
# Add these if you want to customize JWT behavior:
# jwt.secret=your-secret-key
# jwt.expiration=86400000 (24 hours in milliseconds)
```

---

## Summary

### Key Takeaways

1. **Bearer tokens** are the standard format for JWT authentication (`Authorization: Bearer <token>`)

2. **JwtAuthenticationFilter** runs for EVERY request and:
   - Checks for Bearer token in Authorization header
   - Validates JWT signature and expiration
   - Sets up SecurityContext with authenticated user
   - Passes request forward via `filterChain.doFilter()`

3. **Principal** is automatically injected by Spring with the authenticated user information

4. **Public URLs** (defined in `PUBLIC_URLS`) can be accessed without authentication via `permitAll()`

5. **Protected URLs** require valid authentication via `authenticated()` rule

6. **Authorization checks** happen in Spring Security's built-in `AuthorizationFilter`

7. **Error handling** is done by `JwtAuthenticationEntryPoint.commence()` which returns 401 with JSON error response

8. **Session management** is set to Stateless - no server-side sessions

9. **Filter chain** is well-ordered: CORS → JWT → Built-in filters → DispatcherServlet → Controller

10. **Complete flow** ensures security at multiple levels: token validation, authorization rules, and proper error responses


