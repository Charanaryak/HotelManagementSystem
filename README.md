# Hotel Management System (Java + MySQL)

A console-based Hotel Management System written in Java that demonstrates core Java concepts (OOP), JDBC database connectivity, and a small but real-world CRUD flow: customers → rooms → bookings → payments.  
This repo is designed to be compact and interview-friendly: easy to explain, easy to run, and demonstrates the most important Java+DB topics.

---

## Live / Demo
This is a local console app — no web UI. Run in your IDE (IntelliJ) or via `java` from terminal.

---

## Features
- Add / list / update customers
- Add / list rooms, show availability
- Create bookings (link customer + room)
- Complete bookings (mark paid/completion)
- Record payments (with basic foreign-key constraints)
- Works with MySQL via JDBC and uses prepared statements (prevents SQL injection)

---

## Tech stack
- Java (OpenJDK 17/23)
- MySQL Community Server (8.x)
- JDBC (MySQL Connector/J)
- IntelliJ IDEA (development)
- Plain Java classes — no frameworks so you can explain every line in interviews

---

## Project structure (important files)
