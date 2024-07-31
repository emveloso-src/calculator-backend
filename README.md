# Tasks

This project was generated with java/Springboot

## Development server

Run `mvnw spring-boot:run` for a dev server. Navigate to `http://localhost:8080/api/tasks` to verify it is working. 
The application will automatically reload if you change any of the source files.

## Endpoints

- GET /api/tasks
- GET /api/tasks/{id}
- POST /api/tasks
- UPDATE /api/tasks
- DELETE /api/tasks/{id}

## Model

Task is compound by:
- id: int
- title: String
- state: [OPEN, COMPLETED]

