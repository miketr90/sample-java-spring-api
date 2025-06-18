# Spring Boot Order API 

This is a RESTful API built with Spring Boot that allows authenticated users to place orders.

## Features

- User registration 
- Create and view orders
- Exception handling with appropriate HTTP status codes
- Data validation
- In-memory H2 database

## API Endpoints

### User Endpoints

- **POST /api/users/register** - Register a new user
  - Request body:
    ```json
    {
      "username": "johndoe",
      "password": "password123",
      "email": "john@example.com",
      "fullName": "John Doe"
    }
    ```

- **GET /api/users/me** - Get the current user's information

### Order Endpoints

- **POST /api/orders** - Create a new order
  - Request body:
    ```json
    {
      "itemName": "Product Name",
      "description": "Product Description",
      "quantity": 2,
      "price": 29.99
    }
    ```

- **GET /api/orders** - Get all orders for the current user 

- **GET /api/orders/{id}** - Get a specific order by ID 

## Running the Application

1. Make sure you have Java 17 or later installed
2. Clone the repository
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The API will be available at http://localhost:8080

## Testing the API

You can use tools like Postman or curl to test the API.

### Example: Register a User

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "password123",
    "email": "john@example.com",
    "fullName": "John Doe"
  }'
```

### Example: Create an Order

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic am9obmRvZTpwYXNzd29yZDEyMw==" \
  -d '{
    "itemName": "Product Name",
    "description": "Product Description",
    "quantity": 2,
    "price": 29.99
  }'
```

## Security

- Passwords are stored in the database using BCrypt encoding
- Endpoints are secured to ensure users can only access their own data
