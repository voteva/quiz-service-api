package com.voteva.gateway.security.model;

public class Authentication {

    private String token;
    private Principal principal;

    public Authentication(
            String token,
            Principal principal) {

        this.token = token;
        this.principal = principal;
    }

    public String getToken() {
        return token;
    }

    public Principal getPrincipal() {
        return principal;
    }
}
