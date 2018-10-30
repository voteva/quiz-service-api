package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class UserInfo {

    private UUID userUid;
    private String firstName;
    private String lastName;
    private long createdDateTime;
    private boolean isBlocked;
    private boolean isAdmin;

    @JsonGetter("userUid")
    public UUID getUserUid() {
        return userUid;
    }

    public UserInfo setUserUid(UUID userUid) {
        this.userUid = userUid;
        return this;
    }

    @JsonGetter("firstName")
    public String getFirstName() {
        return firstName;
    }

    public UserInfo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonGetter("lastName")
    public String getLastName() {
        return lastName;
    }

    public UserInfo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonGetter("createdDateTime")
    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public UserInfo setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    @JsonGetter("isBlocked")
    public boolean isBlocked() {
        return isBlocked;
    }

    public UserInfo setBlocked(boolean blocked) {
        isBlocked = blocked;
        return this;
    }

    @JsonGetter("isAdmin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public UserInfo setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }
}
