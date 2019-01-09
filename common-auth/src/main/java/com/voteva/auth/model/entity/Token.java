package com.voteva.auth.model.entity;

import java.sql.Timestamp;

public class Token {

    private String token;
    private Timestamp validFromMillis;
    private Timestamp validTillMillis;

    public Token(
            String token,
            Timestamp validFromMillis,
            Timestamp validTillMillis) {
        this.token = token;
        this.validFromMillis = validFromMillis;
        this.validTillMillis = validTillMillis;
    }

    public String getToken() {
        return token;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    public Timestamp getValidFromMillis() {
        return validFromMillis;
    }

    public Token setValidFromMillis(Timestamp validFromMillis) {
        this.validFromMillis = validFromMillis;
        return this;
    }

    public Timestamp getValidTillMillis() {
        return validTillMillis;
    }

    public Token setValidTillMillis(Timestamp validTillMillis) {
        this.validTillMillis = validTillMillis;
        return this;
    }

    @Override
    public String toString() {
        return "{token=" + (token == null ? "null" : "***") +
                ", validFromMillis=" + validFromMillis +
                ", validTillMillis=" + validTillMillis +
                '}';
    }
}
