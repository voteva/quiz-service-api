package com.voteva.quiz.exception;

public class NotFoundUserException extends RuntimeException {

    private static final long serialVersionUID = -1489037555133678910L;

    public NotFoundUserException() {
        super();
    }

    public NotFoundUserException(String message) {
        super(message);
    }
}
