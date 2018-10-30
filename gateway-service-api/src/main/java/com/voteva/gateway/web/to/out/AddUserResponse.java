package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class AddUserResponse {

    private UUID userUid;
    private long createdDateTime;

    public AddUserResponse(UUID userUid, long createdDateTime) {
        this.userUid = userUid;
        this.createdDateTime = createdDateTime;
    }

    @JsonGetter("userUid")
    public UUID getUserUid() {
        return userUid;
    }

    @JsonGetter("createdDateTime")
    public long getCreatedDateTime() {
        return createdDateTime;
    }
}
