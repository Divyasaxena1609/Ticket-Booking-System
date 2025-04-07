# Ticket Booking System (Spring Boot)

This is a RESTful web application built using **Spring Boot** that allows users to register, login, browse events, book event tickets, view booking history, and cancel bookings. Admins can manage events and view all bookings.

---

## Features

### Authentication
- Register a new user
- Login with email and password
- Check user by username

### Events (Admin Only)
- Add new event
- Update event details
- Delete an event
- View all events

### Bookings
- Book tickets for an event
- Cancel part or full booking
- View user booking history
- View all bookings for an event (Admin only)

---

## Tech Stack

- **Backend**: Java, Spring Boot
- **Database**: MySQL or H2 (your choice)
- **Security**: Spring Security (role-based access control)
- **Build Tool**: Maven
- **IDE Support**: IntelliJ IDEA, Eclipse, VS Code

---

## Ports

- Backend - localhost:8080

---

## API Endpoints
### Ticket Booking System

## User Authentication
| Method | Endpoint         | Description                 |
|--------|------------------|---------------------------  |
| GET    | /api/register/{username}| Get user by username |
| POST   | /api/login       | Login User                  |
| POST   | /api/postuser    | Register User               |

## Bookings
| Method | Endpoint         | Description                 |
|--------|------------------|---------------------------  |
| POST   | /api/book/booking| Book Tickets                |
| PUT    | /api/book/cancel | Cancel Booking              |
| GET    | /api/book/user/{userId} | View user's bookings |
| GET    | /api/book/admin/{eventId}| View bookings for an event (Admin)|

## Events(ADMIN Only)
| Method | Endpoint         | Description                 |
|--------|------------------|---------------------------  |
| GET    | /api/events/list | Get all events              |
| POST   | /api/events/addevent| Add new event            |
| PUT    | /api/events/updateevent/{eventId} | Update event |
| DELETE | /api/events/deleteevent/{eventId} | Delete event |

---

## Project Structure
```
com.ticket.booking 
├── Controller/ 
│ ├── AuthController.java 
│ ├── BookingController.java 
| └── EventController.java 
├── Models/ 
│ ├── AuthModel.java 
│ ├── BookingModel.java 
│ └── EventModel.java 
├── Service/ 
│ ├── AuthService.java 
│ ├── BookingService.java 
│ └── EventService.java 
├── CustomResponse/ 
│ └── ResponseApi.java 
├── Repository/ 
│ └── AuthRepository.java
│ └── BookingRepository.java
│ └── EventRepository.java
├── BookingApplication.java 
├── SecurityConfig.java 
└── resources/ 
└── application.properties
```

## Testing the API

You can test the API using tools like:

- [Postman](https://www.postman.com/)

## How to Run the Project

- Clone the Repository
```
git clone https://github.com/your-username/ticket-booking-system.git
cd ticket-booking-system
```
- Create MySQL Database

``` MYSQL
CREATE DATABASE ticket_booking_db;
```
- Configure application.properties

Update src/main/resources/application.properties:
```
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ticket_booking_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect

# Server
server.port=8080
```
Build and Run the Application
```
mvn spring-boot:run
```
The application will start at:
- http://localhost:8080
---
## Sample Output

## Authentication & User APIs

 - POST /api/postuser – Register User

- Request:

```json
{
  "username": "JaneDoe",
  "email": "janedoe@example.com",
  "phone": "9876543210",
  "password": "Secure@123",
  "role": "USER"
}
```
 - Response:

```json
{
  "userId": 1,
  "username": "JaneDoe",
  "email": "janedoe@example.com",
  "phone": "9876543210",
  "role": "USER"
}
```
- POST /api/login – Login
- Request:

```json
{
  "email": "janedoe@example.com",
  "password": "Secure@123"
}
```
- Response:

```json
{
  "message": "Login successful",
  "userId": 1,
  "username": "JaneDoe",
  "role": "USER"
}
```
- GET /api/register/{username} – Get User by Username
- Response:
  
```json
{
  "userId": 1,
  "username": "JaneDoe",
  "email": "janedoe@example.com",
  "phone": "9876543210",
  "role": "USER"
}
```
## Booking APIs
- POST /api/book/booking – Book Tickets
- Request:

```json
{
  "userId": 1,
  "eventId": 101,
  "date": "2025-04-30",
  "numberOfTickets": 2
}
```
- Response:

```json
{
  "bookingId": 2001,
  "user": {
    "userId": 1,
    "username": "JaneDoe"
  },
  "event": {
    "eventId": 101,
    "eventName": "Jazz Fest"
  },
  "date": "2025-04-30",
  "numberOfTickets": 2,
  "canceledTickets": 0,
  "paidAmount": 1199.98,
  "refundableAmount": 0.0,
  "status": "BOOKED"
}
```
- PUT /api/book/cancel – Cancel Booking
-  Request:

```json
{
  "bookingId": 2001,
  "cancelCount": 1
}
```
- Response:

```json
{
  "bookingId": 2001,
  "canceledTickets": 1,
  "refundableAmount": 599.99,
  "status": "PARTIALLY_CANCELLED"
}
```
- GET /api/book/user/{userId} – View User’s Bookings
- Response:

```json
[
  {
    "bookingId": 2001,
    "event": {
      "eventName": "Jazz Fest"
    },
    "numberOfTickets": 2,
    "canceledTickets": 1,
    "paidAmount": 1199.98,
    "status": "PARTIALLY_CANCELLED"
  }
]
```
- GET /api/book/admin/{eventId} – View Event Bookings (Admin)
- Response:

```json
[
  {
    "bookingId": 2001,
    "user": {
      "username": "JaneDoe"
    },
    "numberOfTickets": 2,
    "canceledTickets": 1,
    "paidAmount": 1199.98,
    "status": "PARTIALLY_CANCELLED"
  },
  {
    "bookingId": 2002,
    "user": {
      "username": "JohnSmith"
    },
    "numberOfTickets": 3,
    "canceledTickets": 0,
    "paidAmount": 1799.97,
    "status": "BOOKED"
  }
]
```
## Event Management APIs (Admin Only)
- GET /api/events/list – Get All Events
- Response:

```json
[
  {
    "eventId": 101,
    "eventName": "Jazz Fest",
    "artistName": "Norah Jones",
    "eventDate": "2025-04-30",
    "location": "Delhi",
    "totalTickets": 100,
    "availableTickets": 95,
    "pricePerTicket": 599.99
  },
  {
    "eventId": 102,
    "eventName": "Rock Night",
    "artistName": "The Killers",
    "eventDate": "2025-05-10",
    "location": "Mumbai",
    "totalTickets": 200,
    "availableTickets": 200,
    "pricePerTicket": 899.99
  }
]
```
- POST /api/events/addevent – Add New Event
- Request:
```json

{
  "eventName": "Rock Night",
  "artistName": "The Killers",
  "eventDate": "2025-05-10",
  "location": "Mumbai",
  "totalTickets": 200,
  "availableTickets": 200,
  "pricePerTicket": 899.99
}
```
- Response:

```json
{
  "eventId": 102,
  "eventName": "Rock Night",
  "artistName": "The Killers",
  "eventDate": "2025-05-10",
  "location": "Mumbai",
  "totalTickets": 200,
  "availableTickets": 200,
  "pricePerTicket": 899.99
}
```
- PUT /api/events/updateevent/{eventId} – Update Event
- Request:

```json
{
  "eventName": "Rock Night Reloaded",
  "artistName": "The Killers",
  "eventDate": "2025-05-11",
  "location": "Pune",
  "totalTickets": 250,
  "availableTickets": 250,
  "pricePerTicket": 999.99
}
```
- Response:

```json
{
  "eventId": 102,
  "eventName": "Rock Night Reloaded",
  "artistName": "The Killers",
  "eventDate": "2025-05-11",
  "location": "Pune",
  "totalTickets": 250,
  "availableTickets": 250,
  "pricePerTicket": 999.99
}
```
- DELETE /api/events/deleteevent/{eventId} – Delete Event
- Response:

```json
{
  "message": "Event with ID 102 has been deleted successfully"
}
```


