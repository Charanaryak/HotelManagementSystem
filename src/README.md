# Hotel Management System — Java + JDBC + MySQL

A console-based hotel management app that demonstrates **Core Java**, **JDBC**, and **DBMS** fundamentals using a clean **DAO pattern**.  
Manage **Customers**, **Rooms**, **Bookings**, and **Payments** with proper **foreign keys** and **transactions**.

## ✨ Features
- Customers: add, list
- Rooms: add, list, list-available, update status
- Bookings: create (atomic — inserts + sets room Booked), complete/cancel (frees room)
- Payments: add payment for a booking, list by booking
- JOIN view: show booking with customer & room details

## 🧱 Tech Stack
- **Language:** Java (JDK 23)
- **DBMS:** MySQL 8
- **Connectivity:** JDBC (MySQL Connector/J)
- **Design:** DAO pattern, OOP (entities + DAOs)
- **IDE:** IntelliJ IDEA

## 🗄️ Database Schema

Tables:

- `customers(customer_id PK, name, email UNIQUE, phone, address, created_at)`
- `rooms(room_id PK, room_number UNIQUE, type, price, status ENUM('Available','Booked'))`
- `bookings(booking_id PK, customer_id FK→customers, room_id FK→rooms, check_in, check_out, status ENUM('Active','Completed','Cancelled'), created_at)`
- `payments(payment_id PK, booking_id FK→bookings, amount, payment_date, method ENUM('Cash','Card','UPI'))`

Key relations:
- **1 Customer ⇢ many Bookings**
- **1 Room ⇢ many Bookings**
- **1 Booking ⇢ many Payments**

## 📦 Setup

1. **Create schema & tables**
   ```sql
   DROP DATABASE IF EXISTS hotel_management;
   CREATE DATABASE hotel_management;
   USE hotel_management;

   CREATE TABLE customers (
     customer_id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(100) UNIQUE,
     phone VARCHAR(15),
     address VARCHAR(255),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );

   CREATE TABLE rooms (
     room_id INT AUTO_INCREMENT PRIMARY KEY,
     room_number VARCHAR(10) NOT NULL UNIQUE,
     type VARCHAR(50) NOT NULL,
     price DECIMAL(10,2) NOT NULL,
     status ENUM('Available','Booked') DEFAULT 'Available'
   );

   CREATE TABLE bookings (
     booking_id INT AUTO_INCREMENT PRIMARY KEY,
     customer_id INT NOT NULL,
     room_id INT NOT NULL,
     check_in DATE NOT NULL,
     check_out DATE NOT NULL,
     status ENUM('Active','Completed','Cancelled') DEFAULT 'Active',
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
     FOREIGN KEY (room_id) REFERENCES rooms(room_id)
   );

   CREATE TABLE payments (
     payment_id INT AUTO_INCREMENT PRIMARY KEY,
     booking_id INT NOT NULL,
     amount DECIMAL(10,2) NOT NULL,
     payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     method ENUM('Cash','Card','UPI') DEFAULT 'Cash',
     FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
   );
