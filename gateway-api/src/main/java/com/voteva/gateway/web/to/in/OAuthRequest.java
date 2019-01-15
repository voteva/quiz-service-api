package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class OAuthRequest {

    @NotEmpty
    private String clientId;

    @NotEmpty
    private String callbackUri;

    @JsonCreator
    public OAuthRequest(
            @JsonProperty("clientId") String clientId,
            @JsonProperty("callbackUri") String callbackUri) {
        this.clientId = clientId;
        this.callbackUri = callbackUri;
    }

    @JsonGetter("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonGetter("callbackUri")
    public String getCallbackUri() {
        return callbackUri;
    }
}
