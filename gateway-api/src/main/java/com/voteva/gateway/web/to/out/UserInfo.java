package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class UserInfo {

    private UUID userUid;

    public UserInfo(UUID userUid) {
        this.userUid = userUid;
    }

    @JsonGetter("userUid")
    public UUID getUserUid() {
        return userUid;
    }
}
