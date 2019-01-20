package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class QuizInfo {

    private UUID testUid;
    private String testName;
    private String testCategory;
    private int percentCompleted;

    public QuizInfo setTestUid(UUID testUid) {
        this.testUid = testUid;
        return this;
    }

    @JsonGetter("testUid")
    public UUID getTestUid() {
        return testUid;
    }

    public QuizInfo setTestName(String testName) {
        this.testName = testName;
        return this;
    }

    @JsonGetter("testName")
    public String getTestName() {
        return testName;
    }

    public QuizInfo setTestCategory(String testCategory) {
        this.testCategory = testCategory;
        return this;
    }

    @JsonGetter("testCategory")
    public String getTestCategory() {
        return testCategory;
    }

    public QuizInfo setPercentCompleted(int percentCompleted) {
        this.percentCompleted = percentCompleted;
        return this;
    }

    @JsonGetter("percentCompleted")
    public int getPercentCompleted() {
        return percentCompleted;
    }
}
