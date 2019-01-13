package com.voteva.auth.model.entity;

public class Authentication {

    private AuthToken token;
    private PrincipalKey principalKey;

    public Authentication(
            AuthToken token,
            PrincipalKey principalKey) {
        this.token = token;
        this.principalKey = principalKey;
    }

    public AuthToken getToken() {
        return token;
    }

    public PrincipalKey getPrincipalKey() {
        return principalKey;
    }
}
