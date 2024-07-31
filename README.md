Here’s an improved version of your Markdown documentation with better structure, clarity, and formatting:

---

# Project Documentation

This project is developed using Java and Spring Boot.
This records consists of a login page to authenticate with user and password and a second page that shows all transactions per user and a form to submit a new transaction. Also it is possible to filter transactions by type. Transactions are shown by date descending.
Transaction creation is validated. User balance is always visible. Once user logout it will delete balance from localStorage.

## Development Server

To start the development server, run:

```sh
mvnw spring-boot:run
```

The server will be available at [http://localhost:8081](http://localhost:8081).

## API Endpoints

### Records
- **GET** `/api/v0/records`  
  Retrieves a list of records.

- **POST** `/api/v0/records`  
  Creates a new record.

- **DELETE** `/api/v0/records`  
  Deletes a record. Requires an `id` parameter.

### Operations
- **GET** `/api/v0/operations`  
  Retrieves a list of operations.

### Authentication
- **POST** `/api/v0/login`  
  Logs in a user. Requires credentials (username and password).

## Sample User Records

The following user records are pre-inserted into the database for testing:

### User 1
- **Username:** elemudela@gmail.com
- **Password:** 1234
- **Balance:** 100

### User 2
- **Username:** test@gmail.com
- **Password:** 1234
- **Balance:** 10

---
