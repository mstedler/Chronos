package com.espweb.chronos.domain.exceptions;

public class InvalidArtefatoException extends Exception {
    public InvalidArtefatoException() {
        super();
    }

    public InvalidArtefatoException(String message) {
        super(message);
    }

    public InvalidArtefatoException(String message, Throwable cause) {
        super(message, cause);
    }
}
