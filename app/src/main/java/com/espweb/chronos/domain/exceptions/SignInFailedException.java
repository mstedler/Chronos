package com.espweb.chronos.domain.exceptions;

public class SignInFailedException extends Exception {

    public SignInFailedException() {
    }

    public SignInFailedException(String message) {
        super(message);
    }

    public SignInFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
