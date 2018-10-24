package com.voteva.gateway.exception;

public class GatewayApiException extends RuntimeException {

    private static final long serialVersionUID = -925314905138204789L;

    public GatewayApiException() {
        super();
    }

    public GatewayApiException(String message) {
        super(message);
    }
}
