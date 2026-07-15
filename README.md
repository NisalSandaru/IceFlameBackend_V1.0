# IceFlame 🔥

A RESTful e-commerce backend built with **Spring Boot**. IceFlame provides the
API layer for an online shopping platform — managing users, products, carts,
wishlists, addresses, orders, checkout, and reports. It sends push
notifications to users' devices via **Firebase Cloud Messaging (FCM)** and can
generate **PDF invoices/reports** with iText7.

> Base path for all endpoints: `/api/v1.0`

---

## ✨ Features

- **Authentication & Authorization** — sign up / sign in with Spring Security
  (role-based access: `USER`, `ADMIN`).
- **User & Address management** — profiles plus shipping/billing addresses.
- **Product catalog** — products with categories, images, and stock.
- **Cart & Wishlist** — add/update/remove items; move between wishlist and cart.
- **Checkout & Orders** — place orders, track status & payment, view order
  history.
- **Push notifications** — order-placed alerts sent to registered devices via FCM.
- **Reports & Dashboard** — aggregated statistics and downloadable PDF reports.
- **Global exception handling** — consistent error responses across the API.

---

## 🛠 Tech Stack

| Layer        | Technology                                   |
|--------------|----------------------------------------------|
| Language     | Java 17                                      |
| Framework    | Spring Boot 4.0.3                            |
| Persistence  | Spring Data JPA (Hibernate)                  |
| Database     | MySQL                                        |
| Security     | Spring Security                              |
| Validation   | Spring Validation (Jakarta Bean Validation)  |
| Push         | Firebase Admin SDK (FCM)                     |
| PDF          | iText7                                      |
| Build tool   | Maven (includes `mvnw` wrapper)             |

---

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.8+** (or use the bundled `mvnw` wrapper)
- **MySQL 8+** server running locally
- A **Firebase** project (for push notifications)

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd IceFlame
```

### 2. Set up the database

Create a MySQL database named `IceFlame`:

```sql
CREATE DATABASE IceFlame;
```

### 3. Configure `application.properties`

Open `src/main/resources/application.properties` and update the datasource
credentials and FCM settings for your environment:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/IceFlame
spring.datasource.username=<your-db-username>
spring.datasource.password=<your-db-password>

server.port=8080
server.servlet.context-path=/api/v1.0
spring.jpa.hibernate.ddl-auto=update
```

> ⚠️ **Do not commit real credentials.** The committed `application.properties`
> contains a local dev password — replace it and keep secrets out of version
> control (use environment variables or a secrets manager).

### 4. Configure Firebase

Place your `firebase-service-account.json` in
`src/main/resources/`. This file is read by `FirebaseConfig` to initialize the
FCM client used by `FcmService`.

> 🔒 Keep the Firebase service-account key **out of version control**.

### 5. Build & run

Using the Maven wrapper (recommended):

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Or with a system Maven:

```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at:

```
http://localhost:8080/api/v1.0
```

---

## 📡 API Overview

All routes are prefixed with `/api/v1.0`.

| Module     | Example endpoints                              |
|------------|------------------------------------------------|
| Auth       | `/auth/register`, `/auth/signin`               |
| Users      | `/users/**`                                    |
| Addresses  | `/addresses/**`                                |
| Categories | `/categories/**`                               |
| Products   | `/products/**`                                 |
| Cart       | `/cart/**`                                     |
| Wishlist   | `/wishlist/**`                                 |
| Orders     | `/orders/**`, `/checkout`                      |
| Devices    | `/devices/**` (FCM token registration)         |
| Reports    | `/reports/**` (PDF & dashboard stats)          |
| Dashboard  | `/dashboard/**`                                |

> Tip: start the app and inspect the controllers under
> `src/main/java/com/nisal/iceflame/controller/` for the full route list.

---

## 📁 Project Structure

```
src/main/java/com/nisal/iceflame/
├── config/        # Firebase, Security, Web configuration
├── controller/    # REST endpoints (one per module)
├── dto/           # Request/response data transfer objects
├── enums/         # Role, OrderStatus, PaymentStatus, PaymentMethod, ...
├── exceptions/    # Domain exceptions + global handler
├── mapper/        # Entity <-> DTO mappers
├── model/         # JPA entities
├── repository/    # Spring Data JPA repositories
├── service/       # Business logic (incl. FcmService, ReportService)
└── IceFlameApplication.java
```

---

## 🧪 Tests

```bash
./mvnw test
```

---

## 📄 License

This project is provided as-is for learning purposes.

---

Made with ☕ and Spring Boot.
