package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class UserFullInfo {

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

    public UserFullInfo setUserUid(UUID userUid) {
        this.userUid = userUid;
        return this;
    }

    @JsonGetter("firstName")
    public String getFirstName() {
        return firstName;
    }

    public UserFullInfo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonGetter("lastName")
    public String getLastName() {
        return lastName;
    }

    public UserFullInfo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonGetter("createdDateTime")
    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public UserFullInfo setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    @JsonGetter("isBlocked")
    public boolean isBlocked() {
        return isBlocked;
    }

    public UserFullInfo setBlocked(boolean blocked) {
        isBlocked = blocked;
        return this;
    }

    @JsonGetter("isAdmin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public UserFullInfo setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }
}
