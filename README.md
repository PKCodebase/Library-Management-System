# Library-Management-System

The **Library Management System** is a Spring Boot-based application designed to manage users, books, and borrowings in a library. It provides APIs for user registration and authentication, managing books, borrowing books, and retrieving borrowing history. It also integrates with JWT-based security for authentication and Spring Data JPA for persistence using MySQL as the database.

## Features

- **User Management**: Register, login, and manage users.
- **Book Management**: Add, update, delete, and search for books by title, author, or category.
- **Borrowing Management**: Borrow books, return books, and track borrowing records by user and book.
- **JWT Authentication**: Secure the system with JWT tokens.
- **Swagger UI**: API documentation for ease of testing and usage.

## Technologies Used

- **Spring Boot**: A framework for building Java-based applications.
- **Spring Security**: For securing the APIs using JWT.
- **Spring Data JPA**: For database interaction using Hibernate.
- **MySQL**: A relational database to store user, book, and borrowing data.
- **JWT (JSON Web Tokens)**: For secure authentication.
- **Swagger UI**: For easy API documentation and testing.
- **Lombok**: To reduce boilerplate code.

API Endpoints:

##Signup/Login
Authentication

POST: /login/signup/register: Register a new user.
Request body: {"email": "user@example.com", "password": "password"}

POST /login/signup/login: Login and get a JWT token.
Request parameters: email, password

##Book Management:

POST /books: Add a new book.

Request body: {"title": "Book Title", "author": "Author Name", "category": "Category"}
GET /books: Get all books.

PUT /books/{id}: Update a book by ID.

DELETE /books/{id}: Delete a book by ID.

GET /books/search: Search books by category.

GET /books/{id}: Get a book by ID.

GET /books/search/title: Search books by title.

GET /books/search/author: Search books by author.

GET /books/search/titleAndAuthor: Search books by title and author.


##Borrowing Management:

POST /borrowings/borrow: Borrow a book.

Request parameters: bookId, userId
PUT /borrowings/update: Return a borrowed book.

Request parameter: borrowingId
GET /borrowings/user/{userId}: Get borrowings by user.

GET /borrowings/book/{bookId}: Get borrowings by book.


##User Management:

GET /users/all: Get all users.

GET /users/{userId}: Get user by ID.

PUT /users/{userId}: Update user by ID.

GET /users/email: Get user by email.

DELETE /users/{userId}: Delete user by ID.

GET /users/phone :Get User By Phonenumber.
