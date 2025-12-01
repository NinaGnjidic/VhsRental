# Project Setup

Follow these steps to build and run the project locally.

## Prerequisites

Make sure the following are installed before starting the application:

* PostgreSQL 18.1
* Java 17.0
* Apache Maven 3.6.3
* npm 11.6.3
* Docker 20.10.11

## 1. Build the Backend

Navigate to the backend directory and build the project using Maven:

```bash
cd ./backend/
mvn clean package
cd ..
```

## 2. Set Up the Frontend

Navigate to the frontend directory and install dependencies using npm:

```bash
cd ./frontend/
npm install
cd ..
```

## 3. Start the Application

Use Docker Compose to build and run the application:

```bash
docker compose up --build
```

The application should now be running and accessible locally at [http://localhost:5173/](http://localhost:5173/).
