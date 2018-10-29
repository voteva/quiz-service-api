package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class QuizInfo {

    private UUID testUid;
    private int percentCompleted;
    private int attemptsAllowed;

    public QuizInfo(UUID testUid,
                    int percentCompleted,
                    int attemptsAllowed) {
        this.testUid = testUid;
        this.percentCompleted = percentCompleted;
        this.attemptsAllowed = attemptsAllowed;
    }

    @JsonGetter("test_uid")
    public UUID getTestUid() {
        return testUid;
    }

    @JsonGetter("percent_completed")
    public int getPercentCompleted() {
        return percentCompleted;
    }

    @JsonGetter("attempts_allowed")
    public int getAttemptsAllowed() {
        return attemptsAllowed;
    }
}