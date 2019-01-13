package com.voteva.auth.model.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "tokens")
public class AuthToken {

    @Id
    private ObjectId _id;

    @Indexed(unique = true)
    @Field(value = "token")
    private String token;

    @Field(value = "principalId")
    private String principalId;

    @Field(value = "lastAccessMillis")
    private Instant lastAccessMillis;

    @Field(value = "validFromMillis")
    private Instant validFromMillis;

    @Field(value = "validTillMillis")
    private Instant validTillMillis;

    public String getToken() {
        return token;
    }

    public AuthToken setToken(String token) {
        this.token = token;
        return this;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public AuthToken setPrincipalId(String principalId) {
        this.principalId = principalId;
        return this;
    }

    public Instant getLastAccessMillis() {
        return lastAccessMillis;
    }

    public AuthToken setLastAccessMillis(Instant lastAccessMillis) {
        this.lastAccessMillis = lastAccessMillis;
        return this;
    }

    public Instant getValidFromMillis() {
        return validFromMillis;
    }

    public AuthToken setValidFromMillis(Instant validFromMillis) {
        this.validFromMillis = validFromMillis;
        return this;
    }

    public Instant getValidTillMillis() {
        return validTillMillis;
    }

    public AuthToken setValidTillMillis(Instant validTillMillis) {
        this.validTillMillis = validTillMillis;
        return this;
    }

    @Override
    public String toString() {
        return "{token=" + (token == null ? "null" : "***") +
                ", validFromMillis=" + validFromMillis +
                ", validTillMillis=" + validTillMillis +
                '}';
    }
}
