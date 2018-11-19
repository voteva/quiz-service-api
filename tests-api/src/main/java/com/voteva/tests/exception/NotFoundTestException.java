package com.voteva.tests.exception;

public class NotFoundTestException extends RuntimeException {

    private static final long serialVersionUID = -8762314905138204789L;

    public NotFoundTestException() {
        super();
    }

    public NotFoundTestException(String message) {
        super(message);
    }
}
