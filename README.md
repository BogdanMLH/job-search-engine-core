# ☕ Job Search Engine Core (Backend)

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Framework-Spring_Boot_3-green)
![Docker](https://img.shields.io/badge/DevOps-Docker-blue)
![PostgreSQL](https://img.shields.io/badge/DB-PostgreSQL-336791)

A robust REST API designed to aggregate and process job market data. This serves as the computational core for the recruiting platform, built with **Clean Architecture** principles.

## 🏗 Architecture
The project follows the classical **Layered Architecture (MVC)** to ensure separation of concerns:
* **Controller Layer:** Handles HTTP requests and response mapping.
* **Service Layer:** Contains business logic, transaction management, and data processing.
* **Repository Layer:** Abstraction over Data Access Layer (Hibernate/JPA).
* **Exception Handling:** Global error handling via `@ControllerAdvice`.

## 🛠 Tech Stack
* **Core:** Java 17, Spring Boot 3.
* **Data:** Spring Data JPA, Hibernate, PostgreSQL.
* **Containerization:** Docker & Docker Compose.
* **Logging:** Custom Request/Response logging filters.

## 🚀 How to Run
```bash
# Clone the repository
git clone [https://github.com/BogdanMLH/job-search-engine-core.git](https://github.com/BogdanMLH/job-search-engine-core.git)

# Start the application and database containers
docker-compose up --build
