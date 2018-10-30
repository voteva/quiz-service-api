package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

public class QuestionInfo {

    private String questionText;
    private List<String> answerChoices;

    public QuestionInfo(String questionText,
                        List<String> answerChoices) {
        this.questionText = questionText;
        this.answerChoices = answerChoices;
    }

    @JsonGetter("questionText")
    public String getQuestionText() {
        return questionText;
    }

    @JsonGetter("answerChoices")
    public List<String> getAnswerChoices() {
        return answerChoices;
    }
}
