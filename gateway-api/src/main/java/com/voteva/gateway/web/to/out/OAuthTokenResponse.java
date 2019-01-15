package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class OAuthTokenResponse {

    private String accessToken;
    private String tokenType;
    private Instant expiresIn;
    private String refreshToken;

    @JsonCreator
    public OAuthTokenResponse(
            @JsonProperty("accessToken") String accessToken,
            @JsonProperty("tokenType") String tokenType,
            @JsonProperty("expiresIn") Instant expiresIn,
            @JsonProperty("refreshToken") String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Instant getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
