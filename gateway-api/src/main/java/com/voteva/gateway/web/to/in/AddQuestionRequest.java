package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AddQuestionRequest {

    @NotBlank
    @ApiModelProperty(example = "Question Text")
    private String questionText;

    @NotNull
    @ApiModelProperty(example = "[\"ans1\", \"ans2\", \"ans3\"]")
    private List<String> answerChoices;

    @Min(0)
    @ApiModelProperty(example = "0")
    private int rightAnswer;

    public AddQuestionRequest(
            @JsonProperty("questionText") String questionText,
            @JsonProperty("answerChoices") List<String> answerChoices,
            @JsonProperty("rightAnswer") int rightAnswer) {
        this.questionText = questionText;
        this.answerChoices = answerChoices;
        this.rightAnswer = rightAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
