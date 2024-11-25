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

- Node.js
- npm
- Java JDK (version 22.0.2)
- Gradle (version 8.10.2)

## Installation

### Backend

1. Clone the repository:
   ```bash
   git clone https://github.com/lezhdz98/FlightSearch.git
   ```
2. Navigate to the backend directory:
   ```bash
   cd your-repository/backend
   ```
3. Build and run the project:
   ```bash
   ./gradlew bootRun
   ```

### Frontend

1. Navigate to the frontend directory:
   ```bash
   cd your-repository/frontend
   ```
2. Install the dependencies:
   ```bash
   npm install
   ```
3. Run the application:
   ```bash
   npm start
   ```

## Testing

### Backend

To run the backend unit tests, use the following command:

```bash
./gradlew test
```

### Frontend

To run the frontend unit tests, use the following command:

```bash
npm test
```

