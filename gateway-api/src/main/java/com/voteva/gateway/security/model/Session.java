package com.voteva.gateway.security.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Session {
    private static final int SESSION_EXPIRATION_DAYS = 365;

    private int sessionId;
    private Timestamp sessionCreatedDtime = Timestamp.from(Instant.now());
    private Timestamp sessionExpirationDtime = Timestamp.from(Instant.now().plus(SESSION_EXPIRATION_DAYS, ChronoUnit.DAYS));
    private UUID sessionUid = UUID.randomUUID();
    private String accessToken;

    private User user;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Timestamp getSessionCreatedDtime() {
        return sessionCreatedDtime;
    }

    public void setSessionCreatedDtime(Timestamp sessionCreatedDtime) {
        this.sessionCreatedDtime = sessionCreatedDtime;
    }

    public Timestamp getSessionExpirationDtime() {
        return sessionExpirationDtime;
    }

    public void setSessionExpirationDtime(Timestamp sessionExpirationDtime) {
        this.sessionExpirationDtime = sessionExpirationDtime;
    }

    public UUID getSessionUid() {
        return sessionUid;
    }

    public void setSessionUid(UUID sessionUid) {
        this.sessionUid = sessionUid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
