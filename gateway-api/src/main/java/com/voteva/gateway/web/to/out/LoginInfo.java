package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

public class LoginInfo {

    private String token;

    public LoginInfo(String token) {
        this.token = token;
    }

    @JsonGetter("token")
    public String getToken() {
        return token;
    }
}
