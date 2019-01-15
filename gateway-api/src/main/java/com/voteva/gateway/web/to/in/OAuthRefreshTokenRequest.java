package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthRefreshTokenRequest {

    private String clientId;
    private String clientSecret;
    private String refreshToken;

    @JsonCreator
    public OAuthRefreshTokenRequest(
            @JsonProperty("clientId") String clientId,
            @JsonProperty("clientSecret") String clientSecret,
            @JsonProperty("refreshToken") String refreshToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
    }

    @JsonGetter("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonGetter("clientSecret")
    public String getClientSecret() {
        return clientSecret;
    }

    @JsonGetter("refreshToken")
    public String getRefreshToken() {
        return refreshToken;
    }
}
