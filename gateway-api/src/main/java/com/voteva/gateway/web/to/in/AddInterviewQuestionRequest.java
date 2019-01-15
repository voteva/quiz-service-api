package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

public class AddInterviewQuestionRequest {

    @NotBlank
    @ApiModelProperty(example = "Question Name")
    private String questionName;

    @NotBlank
    @ApiModelProperty(example = "Question Text")
    private String questionText;

    @NotBlank
    @ApiModelProperty(example = "Question Answer")
    private String questionAnswer;

    @NotBlank
    @ApiModelProperty(example = "Question Category")
    private String category;

    @JsonCreator
    public AddInterviewQuestionRequest(
            @JsonProperty("questionName") String questionName,
            @JsonProperty("questionText") String questionText,
            @JsonProperty("questionAnswer") String questionAnswer,
            @JsonProperty("category") String category) {
        this.questionName = questionName;
        this.questionText = questionText;
        this.questionAnswer = questionAnswer;
        this.category = category;
    }

    public String getQuestionName() {
        return questionName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
