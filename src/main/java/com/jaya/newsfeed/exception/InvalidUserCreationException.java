package com.jaya.newsfeed.exception;

public class InvalidUserCreationException extends RuntimeException {

    public InvalidUserCreationException() {
    }

    public InvalidUserCreationException(String message) {
        super(message);
    }

    public InvalidUserCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserCreationException(Throwable cause) {
        super(cause);
    }

    protected InvalidUserCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
