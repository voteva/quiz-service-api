package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AddTestRequest {

    @NotBlank
    @ApiModelProperty(example = "Test Name")
    private String testName;

    @NotBlank
    @ApiModelProperty(example = "Test Category")
    private String testCategory;

    @NotNull
    private List<AddTestQuestionRequest> questions;

    @JsonCreator
    public AddTestRequest(
            @JsonProperty("testName") String testName,
            @JsonProperty("testCategory") String testCategory,
            @JsonProperty("questions") List<AddTestQuestionRequest> questions) {
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

    public List<AddTestQuestionRequest> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
