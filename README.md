
# Uber Backend Project

## Low-Level Design
![LLD](/assets/LLD.png)

## Overview
This project is an Uber backend system implementation using Spring Boot, MySQL, and various Spring modules. The APIs are well-documented using Swagger and Postman.

## Key Features
- **Spring Framework Core Features:**
  - Spring IOC Container
  - Beans
  - AutoConfigurations
- **Dependency Injection:** Used extensively for managing dependencies.
- **Spring MVC Concepts:** Implemented to structure the application.
- **Spring Boot Internals:** Utilized Spring Boot for rapid application development.
- **Lombok:** Simplified Java code with Lombok annotations.
- **Database:**
  - MySQL used for database management.
- **Spring Bean Data Validation:** Implemented validation mechanisms.
- **Exception Handling:** Managed exceptions within the application.
- **Spring Boot REST APIs:** Built RESTful APIs for the system.
- **Spring Data JPA:**
  - Used for database interactions.
  - Custom queries implemented for complex operations.
- **Spring Boot Actuator:** Monitored application health and metrics.
- **Swagger & Postman Documentation:** API documentation provided through Swagger and Postman.
- **Spring Dev Tools:** Utilized for improving the development process.
- **Logging & Auditing:** Implemented to keep track of activities and changes using log4j.
- **Spring Security:**
  - Secured APIs with role-based access control.
  - Implemented password encoding with Bcrypt and session management.
- **Email Notifications:**
  - OTP and ride acceptance notifications sent through emails.
  - Currently for email service I am using JavaMailSender.  


### Auth APIs
| Endpoint                   | HTTP Method | Purpose                                          |
|----------------------------|-------------|--------------------------------------------------|
| /auth/signup               | POST        | Signup a new user                                |
| /auth/onboardDriver/{userId} | POST        | Onboard a new driver (Admin role)               |
| /auth/login                | POST        | Login a user and provide tokens                  |
| /auth/refresh              | POST        | Refresh the authentication token                 |

### Driver APIs
| Endpoint                   | HTTP Method | Purpose                                          |
|----------------------------|-------------|--------------------------------------------------|
| /driver/getMyProfile       | GET         | Get driver's profile                             |
| /driver/getMyRides         | GET         | Get driver's ride history                        |
| /driver/acceptRide/{rideRequestId} | POST   | Accept a ride request                           |
| /driver/startRide/{rideId} | POST        | Start a ride                                     |
| /driver/endRide/{rideId}   | POST        | End a ride                                       |
| /driver/cancelRide/{rideId}| POST        | Cancel a ride                                    |
| /driver/rateRider/{rideId} | POST        | Rate a rider                                     |

### Rider APIs
| Endpoint                   | HTTP Method | Purpose                                          |
|----------------------------|-------------|--------------------------------------------------|
| /rider/getMyProfile        | GET         | Get rider's profile                              |
| /rider/getMyRides          | GET         | Get rider's ride history                         |
| /rider/requestRide         | POST        | Request a new ride                               |
| /rider/cancelRide/{rideId} | POST        | Cancel a ride                                    |
| /rider/rateDriver/{rideId} | POST        | Rate a driver                                    |




## Contact
For any inquiries, please reach out to pandeyprashantganesh525@gmail.com.
