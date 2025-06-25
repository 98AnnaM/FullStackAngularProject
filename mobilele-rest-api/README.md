# 🚗 Mobilele

## 📋 Overview
**Mobilele** is a website where users can register, view and add offers for selling cars, 
can post comments.

## 🛠️ Technical Details

- **Framework:** Spring Boot (REST API)
- **Database:** H2 (in-memory)
- **Authentication:** JWT + Spring Security (email & password based)
- **Data Initialization:** Auto-populated at runtime
- **API Format:** JSON

## 🚀 Getting Started

### ✅ Prerequisites

- Java 17+
- Gradle

### ▶️ Running the Application

No setup required! Simply run the application as a standard Spring Boot app (e.g., via your IDE or `mvn spring-boot:run`).
Once started, the application automatically initializes:

- 🧑‍💼 **Two users**:
   - **Admin:**  
     `username: admin@example.com`  
     `password: topsecret`
   - **User:**  
     `username: user@example.com`  
     `password: topsecret`

- 🚘 **13 offers**, split between the admin and the user.

## REST Endpoints

### 🔐 Users
- `POST /users/login` – Login
- `POST /users/register` – Register a new user

### 🏷️ Brands
- `GET /brands/all` – Retrieve all car brands

### 📢 Offers
- `GET /offers/all` – Get all offers
- `GET /offers/{id}` – Get offer details by ID
- `GET /offers/search` – Search for offers by various criteria
- `POST /offers/add` – Add an offer (authenticated users only)
- `PUT /offers/edit/{id}` – Edit offer (owner or admin only)
- `DELETE /offers/{id}` – Delete offer (owner or admin only)

### 💬 Comments
- `GET /offers/{offerId}/comments` – Get all comments for an offer
- `POST /offers/{offerId}/comments` – Post comment (authenticated users only)
- `DELETE /comments/{commentId}` – Delete comment (owner or admin only)