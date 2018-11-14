package com.voteva.gateway.exception.rest;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.voteva.gateway.exception.model.Service;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class RestExceptionBody implements Serializable {

    private static final long serialVersionUID = -785634905138278900L;

    private HttpStatus status;
    private String message;
    private Service service;

    RestExceptionBody(HttpStatus status, String message, Service service) {
        this.status = status;
        this.message = message;
        this.service = service;
    }

    @JsonGetter("status")
    public HttpStatus getStatus() {
        return status;
    }

    @JsonGetter("message")
    public String getMessage() {
        return message;
    }

    @JsonGetter("service")
    public Service getService() {
        return service;
    }
}
