package com.voteva.auth.model.entity;

public class Authentication {

    private Token token;
    private PrincipalKey principalKey;

    public Authentication(
            Token token,
            PrincipalKey principalKey) {
        this.token = token;
        this.principalKey = principalKey;
    }

    public Token getToken() {
        return token;
    }

    public PrincipalKey getPrincipalKey() {
        return principalKey;
    }
}
