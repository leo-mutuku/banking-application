# Banking Application Documentation

## Overview

This repository contains the implementation of a banking application, with various services such as account management, user registration, and bill payment. The following diagrams illustrate the application's architecture, data flow during customer registration, and the process of bill payment.

---

## 1. Application Architecture Diagram

The **Application Architecture Diagram** shows the high-level structure of the banking system. This architecture consists of the following major components:

- **Frontend Layer**: A web-based or mobile interface used by users to interact with the banking system.
- **API Gateway**: The entry point for all requests, which routes to the appropriate backend services.
- **Backend Services**: Includes various services such as:
    - **Account Service**: Handles account-related operations such as balance inquiries, withdrawals, and account registration.
    - **User Service**: Handles customer/user-related operations like user registration, login, and profile management.
    - **OTP Service**: Provides OTP generation and validation for secure operations.
    - **Bill Payment Service**: Facilitates bill payment functionality.
- **Database**: Persistent storage for user data, account details, transaction records, etc.
- **Third-Party Integration**: Includes integrations with external systems such as payment gateways, email providers, and SMS services.

The diagram is located in the [assets/banking application architecture diagram.drawio.png](assets/banking%20application%20architecture%20diagram.drawio.png) file.

---

## 2. Customer Registration Data Flow Diagram

The **Customer Registration Data Flow Diagram** illustrates the step-by-step flow of data during the customer registration process. The process typically follows these stages:

1. **Customer Input**: The user provides their registration details (e.g., name, email, password, etc.) via the frontend interface.
2. **API Gateway**: The frontend sends a request to the API Gateway, which forwards the request to the `User Service`.
3. **Validation**: The `User Service` validates the input data (e.g., checks if the email is already in use).
4. **OTP Generation**: If the input data is valid, the `OTP Service` generates a one-time password (OTP) and sends it to the user.
5. **OTP Validation**: The user enters the OTP, which is validated by the `OTP Service`. If valid, the registration proceeds.
6. **Account Creation**: The `User Service` creates a user record in the database.
7. **Confirmation**: The user receives a confirmation of successful registration.

The diagram is located in the [assets/customer_registration_dataflow.png](assets/customer_registration_dataflow.png) file.

---

## 3. Bill Payment Sequential Diagram

The **Bill Payment Sequential Diagram** shows the interaction between various components of the system during the bill payment process. Here's a breakdown of the process:

1. **User Initiates Payment**: The user enters bill payment details through the frontend interface (e.g., biller, amount, payment method).
2. **API Gateway**: The request is sent to the API Gateway, which forwards it to the `Bill Payment Service`.
3. **Payment Validation**: The `Bill Payment Service` validates the payment details (e.g., account balance, biller information).
4. **Payment Processing**: If valid, the `Bill Payment Service` processes the payment request by debiting the user's account.
5. **Confirmation**: After successful payment, a confirmation is sent to the user through the frontend interface.
6. **Transaction Record**: A record of the payment is stored in the database for future reference.

The diagram is located in the [assets/bill_payment_sequential.png](assets/bill_payment_
