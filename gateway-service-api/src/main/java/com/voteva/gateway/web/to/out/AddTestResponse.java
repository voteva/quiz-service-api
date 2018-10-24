package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class AddTestResponse {

    private UUID testUid;

    public AddTestResponse(UUID testUid) {
        this.testUid = testUid;
    }

    @JsonGetter("test_uid")
    public UUID getTestUid() {
        return testUid;
    }
}
