<h1 align=center>Car sharing service</h1>
<h2 align=center><img src="https://cdn-icons-png.flaticon.com/128/3321/3321713.png" width=40px> Description <img src="https://cdn-icons-png.flaticon.com/128/3321/3321713.png" width=40px></h2>
The Car Rental Management System is an online platform designed to streamline the operations of a car sharing service. 
This system replaces the outdated manual process of managing car rentals, payments, and tracking with a modern, automated solution. 
It aims to enhance the user experience and provide efficient management tools for the service administrators.

<h2 align=center><img src="https://cdn-icons-png.flaticon.com/128/5092/5092674.png" width=40px>Key features<img src="https://cdn-icons-png.flaticon.com/128/5092/5092674.png" width=40px></h2>
<p>1. User Registration and Authentication: Users can create accounts and log in securely to access the car rental service. 
This ensures that only authorized individuals can rent cars and make transactions.</p>

<p>2. Car Inventory: The system maintains a comprehensive inventory of available cars, including their models, availability status, location, and rental history. </p>

<p>3. Online Booking: Users can check the availability of cars in real-time and make reservations conveniently through the platform. 
The system ensures that only available cars can be booked, reducing the chance of conflicts.</p>

<p>4. Rental Duration and Pricing: The system enables users to select the desired rental duration and calculates the corresponding price automatically.</p>

<p>5. Payment Integration: Users can make secure online payments using various methods, including credit cards, debit cards, and digital wallets. 
The system ensures the privacy and security of user payment information and provides transaction records for reference.</p>

<p>6. Notifications and Reminders: The system sends notifications to users regarding their upcoming reservations, rental durations, and payment reminders. 
This helps users stay informed and ensures a smooth rental experience.</p>

<h2 align=center><img src="https://cdn-icons-png.flaticon.com/128/4365/4365271.png" width=30px>Technologies <img src="https://cdn-icons-png.flaticon.com/128/4365/4365271.png" width=30px></h2>
<code>Java 17</code> |
<code>Spring Boot</code> |
<code>Spirng Boot Dev Tools</code> |
<code>Spring Web</code> |
<code>Spring Security</code> |
<code>Spring Data JPA</code> |
<code>Swagger</code> |
<code>Lombok</code> |
<code>Mapstruct</code> |
<code>Liquibase</code> |
<code>MySQL</code> |
<code>Docker</code> |
<code>Stripe</code>

<h2 align=center>How to run</h2>
<p>You can use this <a href="https://universal-flare-864500.postman.co/workspace/Car_Sharing~95d5d3c4-3278-4b4d-bd34-25ca5ce30615/collection/27144104-ce4c9396-d44a-4ab6-8303-dcc047c88cdf?action=share&creator=27144104">collection</a> to send a request, also you can watch this <a href="https://youtu.be/LME65IyeTEg">video</a> about how to use this application</p>
To run the service, you need to:<br>
<p>1. Init DB. Use this command: <code>CREATE DATABSE `car-sharing`</code></p>
<p>2. Clone this repo</p>
<p>3. Run the code and use request collection</p>

<strong>Or you car use docker. Make sure you have docker installed on your computer</strong>

<p><code>docker build https://github.com/IvanZuravlev228/Car_Sharing_Service.git -t car_sharing_service</code></p>
run the service:
<p><code>docker-compose up</code></p>
or run the service in the background:
<p><code>docker-compose up -d</code></p>
