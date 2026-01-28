# Personal Finance Tracker API (Spring Boot)

REST API to track **income** and **expenses**, generate **summaries**, and **export** data as CSV.

## Features included

- **Create** income/expense entry
- **Read** entries (all / by id) with optional filters
- **Update** entry (full replace)
- **Delete** entry
- **Overall summary** (total income, total expenses, balance) with optional date range
- **Monthly summary** for a given year
- **Yearly summary** across all years
- **CSV export** of all entries
- **Validation + standardized errors** (400/404/500)
- **Swagger/OpenAPI UI**
- **H2 in-memory database** + optional H2 console

## Tech stack

- **Spring Boot** (Web, Validation, Data JPA)
- **H2** in-memory DB
- **springdoc-openapi** (Swagger UI)

## Requirements (to run neatly in VS Code)

### Required

- **Java JDK 17+**
  - Ensure `java -version` prints 17 or higher
- **Apache Maven**
  - Ensure `mvn -v` works (Maven must be on PATH)

### Recommended (VS Code extensions)

- **Extension Pack for Java** (Microsoft)
- **Spring Boot Extension Pack** (VMware)

## Project structure (important)

This project uses a **non-standard folder layout**:

- Java sources: `main/java` (instead of `src/main/java`)
- Resources: `main/resources` (instead of `src/main/resources`)

`pom.xml` is configured to treat these as the source roots.

## Run neatly in VS Code

- Open this folder (`Personal finance manager`) in VS Code
- Open **Terminal** (PowerShell) and run:

```powershell
mvn spring-boot:run
```

- Then open:
  - Swagger UI: `http://localhost:8080/swagger-ui.html`

## Run command (Windows PowerShell)

### Prerequisites

- Java **17+**
- Maven (**mvn**) installed and on PATH

### Start the API

From the project root:

```powershell
mvn spring-boot:run
```

The API runs on:

- `http://localhost:8080`

### Build a JAR and run it

```powershell
mvn -DskipTests package
java -jar .\target\personal-finance-manager-0.0.1-SNAPSHOT.jar
```

## Swagger UI + H2 console

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- **H2 console** (enabled): `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:financedb`
  - username: `sa`
  - password: (empty)

## API base URL

All endpoints are under:

- **Base**: `/api/v1`

## Request body (used by POST and PUT)

### `FinanceEntryRequest` JSON

Used by:

- `POST /api/v1/entries`
- `PUT /api/v1/entries/{id}`

Rules (from validation annotations):

- **amount**: required, \( \ge 0.01 \), up to 2 decimals
- **category**: required, 1–100 characters
- **type**: required, `INCOME` or `EXPENSE`
- **date**: required, must be **past or present** (format `yyyy-MM-dd`)
- **description**: optional, max 500 characters

Example body:

```json
{
  "amount": 1500.00,
  "category": "Salary",
  "type": "INCOME",
  "date": "2026-01-28",
  "description": "January salary"
}
```

## Endpoints + ready-to-run commands

All commands below assume:

- Host: `http://localhost:8080`
- Header: `Content-Type: application/json` (only for JSON endpoints)

### 1) Create entry

- **Endpoint**: `POST /api/v1/entries`
- **Request body**: `FinanceEntryRequest`
- **Response**: `201 Created` with `FinanceEntryResponse`

```powershell
curl.exe -X POST "http://localhost:8080/api/v1/entries" ^
  -H "Content-Type: application/json" ^
  -d "{ \"amount\": 25.50, \"category\": \"Food\", \"type\": \"EXPENSE\", \"date\": \"2026-01-28\", \"description\": \"Lunch\" }"
```

### 2) Get all entries (optional filters)

- **Endpoint**: `GET /api/v1/entries`
- **Query params** (optional):
  - `type`: `INCOME` or `EXPENSE`
  - `category`: case-insensitive exact match
  - `startDate`: `yyyy-MM-dd`
  - `endDate`: `yyyy-MM-dd`
- **Response**: `200 OK` with `List<FinanceEntryResponse>`

Examples:

```powershell
curl.exe "http://localhost:8080/api/v1/entries"
```

```powershell
curl.exe "http://localhost:8080/api/v1/entries?type=EXPENSE&category=Food&startDate=2026-01-01&endDate=2026-01-31"
```

### 3) Get entry by id

- **Endpoint**: `GET /api/v1/entries/{id}`
- **Response**: `200 OK` with `FinanceEntryResponse`

```powershell
curl.exe "http://localhost:8080/api/v1/entries/1"
```

### 4) Update entry (full replace)

- **Endpoint**: `PUT /api/v1/entries/{id}`
- **Request body**: `FinanceEntryRequest`
- **Response**: `200 OK` with `FinanceEntryResponse`

```powershell
curl.exe -X PUT "http://localhost:8080/api/v1/entries/1" ^
  -H "Content-Type: application/json" ^
  -d "{ \"amount\": 30.00, \"category\": \"Food\", \"type\": \"EXPENSE\", \"date\": \"2026-01-28\", \"description\": \"Lunch updated\" }"
```

### 5) Delete entry

- **Endpoint**: `DELETE /api/v1/entries/{id}`
- **Response**: `204 No Content`

```powershell
curl.exe -X DELETE "http://localhost:8080/api/v1/entries/1"
```

### 6) Overall summary (optional date range)

- **Endpoint**: `GET /api/v1/summary`
- **Query params** (optional): `startDate`, `endDate` (format `yyyy-MM-dd`)
- **Response**: `200 OK` with `SummaryResponse`
  - `totalIncome`
  - `totalExpenses`
  - `balance` = `totalIncome - totalExpenses`

```powershell
curl.exe "http://localhost:8080/api/v1/summary"
```

```powershell
curl.exe "http://localhost:8080/api/v1/summary?startDate=2026-01-01&endDate=2026-01-31"
```

### 7) Monthly summary for a year

- **Endpoint**: `GET /api/v1/summary/monthly?year=YYYY`
- **Response**: `200 OK` with `List<PeriodSummaryItem>` (month 1–12)

```powershell
curl.exe "http://localhost:8080/api/v1/summary/monthly?year=2026"
```

### 8) Yearly summary across all years

- **Endpoint**: `GET /api/v1/summary/yearly`
- **Response**: `200 OK` with `List<PeriodSummaryItem>` (month is `0` for yearly)

```powershell
curl.exe "http://localhost:8080/api/v1/summary/yearly"
```

### 9) Export all entries as CSV

- **Endpoint**: `GET /api/v1/entries/export`
- **Response**: `200 OK`, `Content-Type: text/csv`, download filename `finance-entries.csv`

```powershell
curl.exe -L "http://localhost:8080/api/v1/entries/export" -o finance-entries.csv
```

## Standard error response shape

On validation errors, missing resources, etc., the API returns:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "amount: Amount must be at least 0.01.; date: Date cannot be in the future.",
  "path": "/api/v1/entries",
  "timestamp": "2026-01-28T12:34:56"
}
```

## Complete logic explanation (basic → advanced), file-by-file

### 1) `FinanceTrackerApplication.java`

- **Basic**: This is the Spring Boot entry point. Running it starts the embedded server (default Tomcat).
- **Intermediate**: `@SpringBootApplication` enables component scanning for `com.example.finance.*`, auto-configures Spring MVC, JPA, validation, etc.
- **Advanced**: `@OpenAPIDefinition` provides metadata for the generated OpenAPI spec (Swagger UI uses it).

### 2) `controller/FinanceEntryController.java`

- **Basic**: Exposes REST endpoints under `/api/v1`.
- **Intermediate**:
  - Uses `@RestController` to return JSON directly.
  - Uses `@Valid` to enforce validation rules defined in `FinanceEntryRequest`.
  - Delegates all work to `FinanceEntryService` (controllers stay thin).
- **Advanced**:
  - Optional filters (`type`, `category`, `startDate`, `endDate`) are accepted as query parameters.
  - CSV export sets headers (`Content-Disposition`) so the browser downloads a file.
  - Swagger annotations (`@Operation`, `@ApiResponse`, etc.) describe endpoints for API docs.

### 3) `service/FinanceEntryService.java`

- **Basic**: Contains the business logic for CRUD + summaries + export.
- **Intermediate**:
  - Annotated with `@Service` (Spring bean) and `@Transactional`.
  - `createEntry` and `updateEntry` map request DTO → entity, call `repository.save(...)`, return response DTO.
  - `getEntryById` throws `ResourceNotFoundException` if the ID doesn’t exist (returned as 404 by the global handler).
- **Advanced** (logic details):
  - **Filtering** (`getEntries`): loads all entries (`findAll`) then filters in-memory with Java streams:
    - type equality
    - category equalsIgnoreCase
    - date within start/end (inclusive)
  - **Overall summary** (`getOverallSummary`): iterates entries and sums `BigDecimal` totals by `EntryType`, then computes `balance`.
  - **Monthly summary** (`getMonthlySummary`): groups by `YearMonth` for the requested year, then sums totals per month and sorts by month.
  - **Yearly summary** (`getYearlySummary`): groups by year, sums totals per year, and uses `month=0` as a convention for yearly summaries.
  - **CSV export** (`exportEntriesAsCsv`):
    - outputs header row `id,amount,category,type,date,description`
    - wraps description in quotes and escapes quotes (`"` → `""`)
  - **Error strategy**: catches generic persistence/export errors and rethrows `IllegalArgumentException` with a user-friendly message (becomes HTTP 400).

### 4) `repository/FinanceEntryRepository.java`

- **Basic**: A Spring Data JPA repository for CRUD operations on `FinanceEntry`.
- **Intermediate**: Extends `JpaRepository<FinanceEntry, Long>`, which auto-provides `save`, `findAll`, `findById`, `delete`, etc.
- **Advanced**:
  - Declares derived queries:
    - `findByType(EntryType type)`
    - `findByCategoryIgnoreCase(String category)`
    - `findByDateBetween(LocalDate startDate, LocalDate endDate)`
  - Note: current service implementation uses `findAll()` + in-memory filtering; these query methods exist and could be used later to push filtering down into the database.

### 5) `model/FinanceEntry.java`

- **Basic**: JPA entity mapped to table `finance_entries`.
- **Intermediate**:
  - `@Id` + `@GeneratedValue` creates auto-increment IDs.
  - Columns enforce constraints:
    - `amount` is non-null with decimal precision/scale
    - `category` length 100
    - `type` stored as string (`INCOME`/`EXPENSE`)
    - `description` optional, length 500
- **Advanced**:
  - Uses `BigDecimal` for money (avoids floating point rounding issues).
  - `LocalDate` is used to store date without timezone complexity.

### 6) `model/EntryType.java`

- **Basic**: Enum for the entry type.
- **Intermediate**: Values must match request JSON exactly: `INCOME` or `EXPENSE`.
- **Advanced**: Stored in DB as text due to `@Enumerated(EnumType.STRING)` in `FinanceEntry`.

### 7) `dto/*` (Data Transfer Objects)

These are the API shapes (what clients send/receive), separate from the database entity.

- **`FinanceEntryRequest.java`**
  - **Basic**: Used for create/update input.
  - **Intermediate**: Validation annotations enforce safe input:
    - `@DecimalMin`, `@Digits` for amount
    - `@NotBlank`, `@Size` for category
    - `@PastOrPresent` for date
  - **Advanced**: When validation fails, Spring throws `MethodArgumentNotValidException` which is converted into `ApiError` by the global handler.

- **`FinanceEntryResponse.java`**
  - **Basic**: What the API returns for entries (includes `id`).

- **`SummaryResponse.java`**
  - **Basic**: Overall totals and `balance`.
  - **Advanced**: `balance` is computed as `totalIncome.subtract(totalExpenses)` in the constructor.

- **`PeriodSummaryItem.java`**
  - **Basic**: Reusable summary row for a month or year.
  - **Advanced**: For yearly summaries, `month=0` is used as a convention; `balance` is computed the same way.

### 8) `exception/*` (error handling)

- **`ResourceNotFoundException.java`**
  - **Basic**: Signals “entry not found”.
  - **Advanced**: Caught by `GlobalExceptionHandler` and mapped to HTTP 404.

- **`ApiError.java`**
  - **Basic**: Standard JSON error response.
  - **Advanced**:
    - captures `status`, `error`, `message`, `path`, and `timestamp`
    - timestamp format controlled via `@JsonFormat`

- **`GlobalExceptionHandler.java`**
  - **Basic**: One place to convert exceptions into consistent API responses.
  - **Intermediate**:
    - validation errors → 400 with aggregated field messages
    - not found → 404
    - illegal argument → 400
    - unexpected errors → 500 with generic message (does not leak internals)
  - **Advanced**: Extends `ResponseEntityExceptionHandler` to override Spring’s default validation error response.

### 9) `resources/application.properties`

- **Basic**: Sets port and database config.
- **Intermediate**:
  - Uses **H2 in-memory** DB: data resets when the app stops.
  - `spring.jpa.hibernate.ddl-auto=update` auto-creates/updates tables.
  - SQL logging is enabled (`show-sql`, `format_sql`).
- **Advanced**:
  - Enables the H2 console at `/h2-console` for debugging.
  - Swagger UI is available once `springdoc-openapi` dependency is present.

## Notes / limitations (current implementation)

- Filters and summaries currently do `repository.findAll()` then compute results in memory. This is fine for small datasets; for large datasets you’d typically push filtering/aggregation to the DB with query methods or JPQL.

#   p e r s o n a l - f i n a n c e - t r a c k e r  
 