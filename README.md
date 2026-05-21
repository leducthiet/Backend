# Tour Booking Application

A full-stack web application for managing tour packages, bookings, and travel agency operations — built as a university graduation project.

> **Note for reviewers:** This document leads with known technical issues as a deliberate choice. Identifying, articulating, and prioritizing technical debt is part of the engineering skill being demonstrated here.

---

## ⚠️ Known Technical Issues

This project was built within a short academic timeline. The issues below were identified through self-review and are documented transparently as part of the technical assessment.

### 🔴 Critical — must resolve before any production use

**1. No input validation layer**
User-submitted data is passed directly to the database with no validation at the controller, service, or database level. Invalid emails, empty required fields, and out-of-range values are all accepted silently.

**2. Payment flow has no transaction handling**
The booking + payment sequence is not wrapped in a database transaction. If the PayPal API call fails mid-flow, the booking record is left in a `PROCESSING` state with no rollback — requiring manual data correction.

**3. Authorization is role-level only, not resource-level**
The system checks whether a user has the right role, but not whether they own the resource. A manager from Agency A can currently read or modify data belonging to Agency B.

**4. Database entities exposed across all layers**
JPA entities are passed directly from the repository up to the controller and out to the client — leaking internal DB structure and sensitive fields. No DTO layer exists to separate the API contract from the database schema.

---

### 🟠 Important — should resolve before any team or client use

**5. No service interfaces / field injection anti-pattern**
Services are concrete classes with no interfaces, and dependencies are injected via `@Autowired` on fields. This makes unit testing without a full Spring context difficult and creates tight coupling throughout the codebase.

**6. Inconsistent error handling**
Exceptions are caught silently in most controllers with no logging and no structured error response. Each endpoint returns errors in a different format, making client-side handling unreliable.

**7. No unit tests**
The test suite contains a single empty context-load test. There is no coverage for service logic, edge cases, or payment flows — making refactoring risky and regressions invisible until they reach production.

---

### 🟡 Medium — quality of life improvements

**8. No database indexes**
High-traffic query columns (`user_id`, `state`, `created_date`) have no indexes. Queries that filter or sort by these columns perform full table scans.

**9. No structured logging**
There is no logging strategy. Debugging production issues relies entirely on exception stack traces when they happen to surface.

**10. No API documentation**
No Swagger/OpenAPI spec exists. The API contract is only discoverable by reading the source code.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot 3, Spring Security |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL |
| Payment | PayPal REST API |
| Frontend | Thymeleaf, Bootstrap |

---

## Architecture

```
Controller → Service → Repository → Database
```

The project follows a standard layered Spring Boot architecture with repository pattern for data access and service layer for business logic.

---

## Roles & Permissions

| Role | Capabilities |
|------|-------------|
| `SYSTEM_ADMIN` | Full access — manage all agencies, tours, users |
| `MANAGER` | Manage tours and bookings within their own agency |
| `CUSTOMER` | Browse approved tours, make bookings, view own booking history |
