package com.voteva.gateway.web.to.common;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.UUID;

public class TestInfo {

    @ApiModelProperty(example = "8152e3f9-449f-44c1-9d3a-e3c6f1afd752")
    private UUID testUid;

    @ApiModelProperty(example = "Test Name")
    private String testName;

    @ApiModelProperty(example = "Test Category")
    private String testCategory;

    private List<QuestionInfo> questions;

    @JsonGetter("test_uid")
    public UUID getTestUid() {
        return testUid;
    }

    public TestInfo setTestUid(UUID testUid) {
        this.testUid = testUid;
        return this;
    }

    @JsonGetter("test_name")
    public String getTestName() {
        return testName;
    }

    public TestInfo setTestName(String testName) {
        this.testName = testName;
        return this;
    }

    @JsonGetter("test_category")
    public String getTestCategory() {
        return testCategory;
    }

    public TestInfo setTestCategory(String testCategory) {
        this.testCategory = testCategory;
        return this;
    }

    @JsonGetter("questions")
    public List<QuestionInfo> getQuestions() {
        return questions;
    }

    public TestInfo setQuestions(List<QuestionInfo> questions) {
        this.questions = questions;
        return this;
    }
}
