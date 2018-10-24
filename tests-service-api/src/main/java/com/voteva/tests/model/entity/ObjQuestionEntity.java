package com.voteva.tests.model.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class ObjQuestionEntity {

    @Field(value = "questionText")
    private String questionText;

    @Field(value = "answerChoices")
    private List<String> answerChoices;

    @Field(value = "rightAnswer")
    private int rightAnswer;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(List<String> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
