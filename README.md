# Portfolio Management API

## Overview

This project is a **RESTful API** designed for managing personal portfolios. Users can securely log in using **JWT (JSON Web Tokens)**, update their own projects, education, work experience, and skills, and view other users' portfolios without modifying them. The API supports basic **CRUD operations** for portfolio sections and ensures data security through **Spring Security**.

---

## Important Features

- **User Authentication & Authorization**
  - Users can securely log in using **JWT** tokens.
  - Portfolio access is protected based on roles; users can only modify their own data while being able to view others' portfolios in a read-only mode.

- **Portfolio Management**
  - Users can create, update, and delete their **Projects**, **Education**, **Work Experience**, and **Skills**.
  - Users can view others' portfolios but cannot modify them.

- **Data Security**
  - Uses **Spring Security** for authentication and authorization.
  - Protects endpoints using JWT tokens to ensure secure access to portfolio data.

---

## Tech Stack

- **Java** (17+)
- **Spring Boot** (3.x)
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (Database Interaction)
- **PostgreSQL** (Database)
- **Gradle** (Dependency Management & Build)
- **MapStruct** (Object Mapping)

---

## Setup and Installation

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/portfolio-management-api.git
cd portfolio-management-api
