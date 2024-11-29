# Banking Application

A robust Java Springboot banking application that provides secure banking operations and account management functionality.

## Description

This banking application is designed to provide a secure and efficient platform for managing banking operations. 
It allows users to perform common banking tasks such as account creation, money transfers, balance inquiries, and transaction history viewing.

## Features

- Secure user authentication and authorization
- Account management (create, view, update)
- Transaction processing
- Balance inquiries
- Transaction history
- Secure data storage

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6.x or higher
- MySQL 8.0 or higher

## Installation & Setup

1. Clone the repository:
```bash
git clone [https://github.com/arthuradinder/BankingApp]

2. Navigate to the project directory:
   cd banking-app
```
## Configure the database

- Create a MySQL database
- Update the database configuration in:
``src/main/resources/application.properties``

## Build the project

``mvn clean install
``

## Running the Application

- Start the application using Maven:

``mvn spring-boot:run
``
- The application will be available at:
``http://localhost:8080``




