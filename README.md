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

- For the frontend:
Run `npm install` and then `ng serve`. 

- For the backend:
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

## Screenshots

![Image](https://github.com/user-attachments/assets/127ed4c7-f30b-4f79-949c-9e98d8808079)

![Image](https://github.com/user-attachments/assets/32f8c1d9-60f8-4816-ae65-7917147e2396)

![Image](https://github.com/user-attachments/assets/4e25ca5b-34da-40c3-a891-fafbe73473c4)

![Image](https://github.com/user-attachments/assets/a169b11b-4825-4e9c-b980-ecdc62f81388)

![Image](https://github.com/user-attachments/assets/2c149173-4174-4203-ad0f-47c48a72ef87)

![Image](https://github.com/user-attachments/assets/a7065e5d-0f27-447f-9db9-82d5e23f9211)

![Image](https://github.com/user-attachments/assets/63695e75-9e97-4d0b-ba47-072d57c2fd33)

![Image](https://github.com/user-attachments/assets/dc3a5f59-eddb-4563-b87b-f30a69b7c99e)

![Image](https://github.com/user-attachments/assets/c66c51f8-9ecb-48c2-a7bc-c5e3f6816458)

![Image](https://github.com/user-attachments/assets/5eb09e30-386d-496e-9397-b0faa78f7079)
