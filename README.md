# Car sharing service
## Description
The Car Rental Management System is an online platform designed to streamline the operations of a car sharing service. 
This system replaces the outdated manual process of managing car rentals, payments, and tracking with a modern, automated solution. 
It aims to enhance the user experience and provide efficient management tools for the service administrators.

## Key features
1. User Registration and Authentication: Users can create accounts and log in securely to access the car rental service. 
This ensures that only authorized individuals can rent cars and make transactions.

2. Car Inventory: The system maintains a comprehensive inventory of available cars, including their models, availability status, location, and rental history. 
Users can search and view details of specific cars, such as features, images, and pricing.

3. Online Booking: Users can check the availability of cars in real-time and make reservations conveniently through the platform. 
The system ensures that only available cars can be booked, reducing the chance of conflicts.

4. Rental Duration and Pricing: The system enables users to select the desired rental duration and calculates the corresponding price automatically.
It can handle different pricing structures, such as hourly, daily, or weekly rates, and apply any applicable discounts or promotions.

5. Payment Integration: Users can make secure online payments using various methods, including credit cards, debit cards, and digital wallets. 
The system ensures the privacy and security of user payment information and provides transaction records for reference.

6. Notifications and Reminders: The system sends notifications to users regarding their upcoming reservations, rental durations, and payment reminders. 
This helps users stay informed and ensures a smooth rental experience.

## Technologies
- Java 17
- Spring Boot
- Spirng Boot Dev Tools
- Spring Web
- Spring Security
- Spring Data JPA
- Swagger
- Lombok
- Mapstruct
- Liquibase
- MySQL 
- Docker
- Stripe

## How to run
To run the service, you need to:
```bash
docker build https://github.com/IvanZuravlev228/Car_Sharing_Service.git -t car_sharing_service
```
run the service:
```bash
docker-compose up
```
or run the service in the background:
```bash
docker-compose up -d
```