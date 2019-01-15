package com.voteva.auth.model.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "oauth_codes")
public class OAuthCode {

    @Id
    private ObjectId _id;

    @Field(value = "code")
    private String code = UUID.randomUUID().toString();

    @Field(value = "clientId")
    private String clientId;

    @Field(value = "userAccessToken")
    private String userAccessToken;

    @Field(value = "expiresIn")
    private Instant expiresIn;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public OAuthCode setCode(String code) {
        this.code = code;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OAuthCode setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getUserAccessToken() {
        return userAccessToken;
    }

    public OAuthCode setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
        return this;
    }

    public Instant getExpiresIn() {
        return expiresIn;
    }

    public OAuthCode setExpiresIn(Instant expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
