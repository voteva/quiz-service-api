package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class QuizInfo {

    private UUID testUid;
    private int percentCompleted;

    public QuizInfo(
            UUID testUid,
            int percentCompleted) {
        this.testUid = testUid;
        this.percentCompleted = percentCompleted;
    }

    @JsonGetter("testUid")
    public UUID getTestUid() {
        return testUid;
    }

    @JsonGetter("percentCompleted")
    public int getPercentCompleted() {
        return percentCompleted;
    }
}
