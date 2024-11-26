# Flight Search Application

## Description

This is a fullstack flight search application that connects to the Amadeus API to look for flights. The application has three pages:

1. **Main Page:** A form to search for flights.
2. **Results Page:** Displays information about the flights based on the search criteria.
3. **Details Page:** Shows detailed information about a selected flight from the results page.

The project uses Java with Spring Boot for the backend and React with TypeScript for the frontend. It includes unit tests for both the backend and the frontend.

## Technologies

- **Backend:** Java, Spring Boot, Gradle
- **Frontend:** React, TypeScript
- **Testing:** JUnit (backend), Jest (frontend)

## Requirements

- Docker
- Docker Compose

## Installation

### Using Docker

1. Clone the repository:
   ```bash
   git clone https://github.com/lezhdz98/FlightSearch.git
   ```
2. Navigate to the project directory:
   ```bash
   cd FlightSearch
   ```

### Backend

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```
3. Build the Docker image:
   ```bash
   docker build -t my-backend .
   ```

### Frontend

1. Navigate to the frontend directory:
   ```bash
   cd ../frontend
   ```
2. Build the Docker image:
   ```bash
   docker build -t my-frontend .
   ```

### Running the Application

1. Navigate back to the root directory of the project:
   ```bash
   cd ..
   ```
2. Build and run the containers using Docker Compose:
   ```bash
   docker-compose up --build
   ```
3. Open your web browser and go to http://localhost:8080/ to see the web application.

## Testing

### Backend

To run the backend unit tests, use the following commands:

```bash
cd backend
```

```bash
./gradlew test
```

### Frontend

The test are build in the Dockerfile when you create the image.
