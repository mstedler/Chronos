package com.espweb.chronos.domain.exceptions;

public class SignUpFailedException extends Exception {
    public SignUpFailedException() {
    }

    public SignUpFailedException(String message) {
        super(message);
    }

    public SignUpFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
