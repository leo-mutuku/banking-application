package com.leo.banking.account;

public class InsufficientFundsException extends RuntimeException {

    // Constructor that accepts a message
    public InsufficientFundsException(String message) {
        super(message);
    }

    // Optional: Constructor that accepts a message and cause (for better stack trace debugging)
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
