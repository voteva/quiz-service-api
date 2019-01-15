package com.voteva.auth.security;

public enum Grants {

    AUTHORIZATION_GRANT("authorization"),
    OAUTH_GRANT("oauth");

    private String value;

    Grants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
