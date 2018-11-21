package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;
import java.util.UUID;

public class TestInfo {

    private UUID testUid;
    private String testName;
    private String testCategory;
    private List<TestQuestionInfo> questions;

    public TestInfo(
            UUID testUid,
            String testName,
            String testCategory,
            List<TestQuestionInfo> questions) {
        this.testUid = testUid;
        this.testName = testName;
        this.testCategory = testCategory;
        this.questions = questions;
    }

    @JsonGetter("testUid")
    public UUID getTestUid() {
        return testUid;
    }

    @JsonGetter("testName")
    public String getTestName() {
        return testName;
    }

    @JsonGetter("testCategory")
    public String getTestCategory() {
        return testCategory;
    }

    @JsonGetter("questions")
    public List<TestQuestionInfo> getQuestions() {
        return questions;
    }
}
