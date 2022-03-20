# Account-Microservice

## Introduction 
Account Microservice: The Account microservice enables users to register on the FinTech App and on complete 
registration the user gets a generated 10 digits account number and receives a JsonWebToken for authentication and
accessing resources.

## The Accounting services
* Users details
* Users account details
* Authentication
* Utility operations for the transaction service
* Account comfirmation via email

## How to Run
Before running the Account Service please make sure to first Run the Notification Service and Service Registry

### Endpoints
Registration Endpoints : http://localhost:9091/api/v1/account/user/register     [Post]

Update User details Endpoint : http://localhost:9091/api/v1/account/user/3b24e85d-c980-40aa-b674-9928e07ac093     [PUT]

Get User details Endpoint : http://localhost:9091/api/v1/account/account/3823774807     [GET]

Account Login : http://localhost:9091/api/v1/account/login    [POST]

Transfer funds : http://localhost:9091/api/v1/account/transaction/transfer-fund    [POST]

Deposit funds : http://localhost:9091/api/v1/account/transaction/deposit-fund       [POST]

Withdraw funds : http://localhost:9092/api/v1/transaction/withdraw-funds        [POST]

### H2 Database
H2 database url : http://localhost:9091/api/v1/account/h2-console/

username: sa

password: sa


the full postman collection will be shared
