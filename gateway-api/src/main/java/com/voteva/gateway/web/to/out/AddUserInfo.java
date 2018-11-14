package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class AddUserInfo {

    private UUID userUid;
    private long createdDateTime;

    public AddUserInfo(UUID userUid, long createdDateTime) {
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
