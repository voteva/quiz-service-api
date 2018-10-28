package com.voteva.gateway.web.to.common;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class QuestionInfo {

    @ApiModelProperty(example = "Question Text")
    private String questionText;

    @ApiModelProperty(example = "[\"ans1\", \"ans2\", \"ans3\"]")
    private List<String> answerChoices;

    @ApiModelProperty(example = "1")
    private int rightAnswer;

    public QuestionInfo(@JsonProperty("question_text") String questionText,
                        @JsonProperty("answer_choices") List<String> answerChoices,
                        @JsonProperty("right_answer") int rightAnswer) {
        this.questionText = questionText;
        this.answerChoices = answerChoices;
        this.rightAnswer = rightAnswer;
    }

    @JsonGetter("question_text")
    public String getQuestionText() {
        return questionText;
    }

    @JsonGetter("answer_choices")
    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    @JsonGetter("right_answer")
    public int getRightAnswer() {
        return rightAnswer;
    }

}
