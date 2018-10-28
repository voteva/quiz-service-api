package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.voteva.gateway.web.to.common.QuestionInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AddTestRequest {

    @NotBlank
    private String testName;
    @NotBlank
    private String testCategory;
    @NotNull
    private List<QuestionInfo> questions;

    @JsonCreator
    public AddTestRequest(@JsonProperty("test_name") String testName,
                          @JsonProperty("test_category") String testCategory,
                          @JsonProperty("questions")  List<QuestionInfo> questions) {
        this.testName = testName;
        this.testCategory = testCategory;
        this.questions = questions;
    }

    public String getTestName() {
        return testName;
    }

    public String getTestCategory() {
        return testCategory;
    }

    public List<QuestionInfo> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
