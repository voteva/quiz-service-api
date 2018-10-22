package com.voteva.users.exception;

public class NotFoundUserException extends RuntimeException {

    private static final long serialVersionUID = -1525157555138206160L;

    public NotFoundUserException() {
        super();
    }

    public NotFoundUserException(String message) {
        super(message);
    }
}
