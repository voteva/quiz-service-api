package com.voteva.interview.exception;

public class NotFoundQuestionException extends RuntimeException {

    private static final long serialVersionUID = -1832314473138204780L;

    public NotFoundQuestionException() {
        super();
    }

    public NotFoundQuestionException(String message) {
        super(message);
    }
}
