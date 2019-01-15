package com.voteva.auth.model.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "oauth_refresh_tokens")
public class OAuthRefreshToken {

    @Id
    private ObjectId _id;

    @Field(value = "clientId")
    private String clientId;

    @Field(value = "principalId")
    private String principalId;

    @Field(value = "refreshToken")
    private String refreshToken;

    @Field(value = "expiresIn")
    private Instant expiresIn;

    public String getClientId() {
        return clientId;
    }

    public OAuthRefreshToken setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public OAuthRefreshToken setPrincipalId(String principalId) {
        this.principalId = principalId;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public OAuthRefreshToken setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Instant getExpiresIn() {
        return expiresIn;
    }

    public OAuthRefreshToken setExpiresIn(Instant expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
