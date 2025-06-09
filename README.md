# Long Weekend Finder

A Spring Boot application to find long weekends based on holidays.

## Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd long-weekend-finder
   ```

2. Build the project:
   ```bash
   ./gradlew clean build
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

## Configuration

- The application runs on port 8081 by default. You can change this in `src/main/resources/application.properties`.
- H2 console is enabled at `/h2-console`.

## Usage

- Access the application at `http://localhost:8081`.
- Use the H2 console at `http://localhost:8081/h2-console` to manage the database.

## Features

- Find long weekends based on holidays.
- Configurable server port and database settings.
