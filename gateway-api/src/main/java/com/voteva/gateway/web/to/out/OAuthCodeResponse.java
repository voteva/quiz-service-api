package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthCodeResponse {

    private String authorizationCode;

    @JsonCreator
    public OAuthCodeResponse(@JsonProperty("authorizationCode") String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    @JsonGetter("authorizationCode")
    public String getAuthorizationCode() {
        return authorizationCode;
    }
}
