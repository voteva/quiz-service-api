package com.voteva.gateway.exception.rest;

import com.voteva.gateway.exception.GatewayApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = GatewayApiException.class)
    public ResponseEntity<Object> handleGatewayException(Exception ex, WebRequest request) {
        GatewayApiException gatewayApiException = (GatewayApiException) ex;

        return super.handleExceptionInternal(
                ex,
                new RestExceptionBody(
                        gatewayApiException.getHttpStatus(),
                        gatewayApiException.getMessage(),
                        gatewayApiException.getService()),
                new HttpHeaders(),
                gatewayApiException.getHttpStatus(),
                request);
    }
}