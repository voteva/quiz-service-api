package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.beans.Transient;
import java.util.List;

public class QuestionInfo {

    private String questionText;
    private List<String> answerChoices;
    private int rightAnswer;

    public QuestionInfo(String questionText,
                        List<String> answerChoices,
                        int rightAnswer) {
        this.questionText = questionText;
        this.answerChoices = answerChoices;
        this.rightAnswer = rightAnswer;
    }

    @JsonGetter("questionText")
    public String getQuestionText() {
        return questionText;
    }

    @JsonGetter("answerChoices")
    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    @Transient
    public int getRightAnswer() {
        return rightAnswer;
    }
}
