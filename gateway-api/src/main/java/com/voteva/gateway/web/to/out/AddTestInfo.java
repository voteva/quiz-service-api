package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class AddTestInfo {

    private UUID testUid;

    public AddTestInfo(UUID testUid) {
        this.testUid = testUid;
    }

    @JsonGetter("testUid")
    public UUID getTestUid() {
        return testUid;
    }
}
