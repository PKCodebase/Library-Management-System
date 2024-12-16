package com.Library_Management_System.exception;

public class NoCopiesAvailableException extends RuntimeException {
    public NoCopiesAvailableException(String message) {
        super(message);
    }
}
