# 🛒 Grocery Store Web Application

A full-stack web application built with *Angular* and *Spring Boot* that enables users to browse products by sections, manage carts, and place orders. Includes separate views for Admin and Users.

---

## 🚀 Features

### 👤 User Module
- User Signup and Login
- Browse products by section
- Search for products
- Add products to cart
- Increment/Decrement quantity
- Place orders
- View order history

### 🔐 Admin Module
- Admin authentication
- Add, edit, delete sections
- Manage products (CRUD)
- View all orders

---

## 🛠 Tech Stack

| Frontend      | Backend        | Database | Others           |
|---------------|----------------|----------|------------------|
| Angular       | Spring Boot    | MySQL    | REST API, JWT    |
| TypeScript    | Java 17        |          | HTML, CSS        |

---

## ⚙ Architecture

- *Frontend*: Angular-based SPA with routing and reactive forms
- *Backend*: RESTful APIs in Spring Boot using JPA and MySQL
- *Authentication*: JWT (Planned integration)
- *Cart Handling*: Stored in DB and dynamically updated on UI
- *Modularized*: Admin and User modules are separated for scalability
