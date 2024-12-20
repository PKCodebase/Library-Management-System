package com.Library_Management_System.exception;

public class BookAlreadyReturnException extends RuntimeException {
    public BookAlreadyReturnException(String message) {
        super(message);
    }
}
