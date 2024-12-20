package com.Library_Management_System.exception;

public class NoBookBorrowedException extends RuntimeException {
    public NoBookBorrowedException(String message) {
        super(message);
    }
}
