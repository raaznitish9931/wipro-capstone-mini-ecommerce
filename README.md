
# Mini E-commerce Application (Microservices Architecture)

## Project Overview

This project is a Mini E-commerce Application developed using Spring Boot for the backend and Angular for the frontend. It follows a Microservices Architecture to simulate a real-world online shopping system.

The application allows users to:

* Register and login
* View products
* Add products to cart or wishlist
* Place orders

Each functionality is handled by a separate microservice, making the system modular, scalable, and easy to maintain.

---

## Architecture

The project is designed using Microservices Architecture, where each service works independently and communicates with others when required.

### Microservices Used

1. User Service – Handles user registration, login, and profile management
2. Product Service – Manages product catalog and product details
3. Order Service – Handles order placement and order management
4. Cart Service – Manages cart-related operations
5. API Gateway – Acts as a single entry point for all client requests
6. Config Server – Provides centralized configuration for all services
7. Service Registry (Eureka) – Used for service discovery

---

## Technologies Used

### Backend

* Java
* Spring Boot
* Spring Cloud (Eureka, Config Server)
* OpenFeign (for synchronous communication)
* Kafka (for asynchronous communication)
* MySQL (Database)


### Frontend

* Angular
* TypeScript
* HTML
* CSS
* Bootstrap

### Tools

* VS Code / IntelliJ
* Postman (for API testing)
* Git and GitHub

---

## Features

* User registration and login functionality
* Separate dashboards for admin and user
* Product listing and management
* Add to cart functionality
* Order placement
* Wishlist feature
* Inter-service communication using Feign
* Asynchronous processing using Kafka
* Fault handling using circuit breaker concept

---

## Communication Between Services

### Synchronous Communication

Synchronous communication is implemented using OpenFeign.

Example:

* Order Service communicates with User Service
* Order Service communicates with Product Service

### Asynchronous Communication

Asynchronous communication is implemented using Kafka.

Example:

* When an order is placed, an event is sent to Kafka
* Another service processes the event in the background

---

## Design Patterns Used

### Microservices Architecture

Each service is independent and loosely coupled.

### CQRS (Command Query Responsibility Segregation)

Separate services are used for:

* Write operations (Command)
* Read operations (Query)

### DDD (Domain Driven Design - Aggregate)

Each service manages its own business logic and data consistency.

### Circuit Breaker

Used to prevent cascading failures when a service becomes unavailable.

---

## Problem Faced

When one microservice was stopped, the entire application stopped working.

### Solution

Fault isolation and fallback mechanisms were implemented.

Now:

* Only the specific service stops working
* Other services continue to function normally
* The UI shows a proper "Service Unavailable" message where required

---

## Database Configuration

Database Name: ecommerce_db
Username: root
Password: root

---

## How to Run the Project

### Step 1: Start Backend Services

Start the services in the following order:

1. Config Server
2. Eureka Server
3. All Microservices (User, Product, Order, Cart)
4. API Gateway

---

### Step 2: Start Kafka


* Start Kafka Server

---

### Step 3: Run Frontend

Run the Angular application using:

ng serve

Open the application in the browser:

[http://localhost:4200](http://localhost:4200)

---

## API Testing (Postman)

### User APIs

POST /user/register
GET /user/{id}

### Product APIs

GET /product/all

### Order APIs

POST /order/place

---

## User Interface

The application includes:

* User Dashboard
* Admin Dashboard
* Product Page
* Cart Page

---

## UI Features

* Password show/hide functionality
* Wishlist icon
* Currency symbol (Rupee)
* Responsive design

---

## Challenges Faced

* CORS issues between frontend and backend
* Feign client configuration issues
* Kafka setup complexity
* Managing multiple microservice ports

---

## Future Improvements

* Integration of payment gateway
* Implementation of JWT authentication
* UI/UX improvements
* Containerization using Docker and deployment using Kubernetes

---

## Author

Nitish Kumar
Wipro Capstone Project

## Project Guidance

This project was successfully completed under the guidance of:
Mr. Jayanta K Das

His support and guidance helped in understanding:

Microservices Architecture
Spring Boot & Angular integration
Real-world project structure
Best practices for development

## Conclusion

This project helped in understanding:

* Microservices architecture in real-world applications
* Communication between distributed services
* Handling failures using resilience patterns

