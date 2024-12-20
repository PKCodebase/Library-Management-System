package com.Library_Management_System.exception;

public class PhoneAlreadyExistException extends RuntimeException {
    public PhoneAlreadyExistException(String message) {
        super(message);
    }
}
