package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

public class TestResultsRequest {

    @NotNull
    private UUID testUid;
    @NotNull
    private Map<String, Integer> answers;

    @JsonCreator
    public TestResultsRequest(
            @JsonProperty("testUid") UUID testUid,
            @JsonProperty("answers") Map<String, Integer> answers) {
        this.testUid = testUid;
        this.answers = answers;
    }

    public UUID getTestUid() {
        return testUid;
    }

    public Map<String, Integer> getAnswers() {
        return answers;
    }
}
