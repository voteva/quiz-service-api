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

    public ObjQuestionEntity setQuestionText(String questionText) {
        this.questionText = questionText;
        return this;
    }

    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    public ObjQuestionEntity setAnswerChoices(List<String> answerChoices) {
        this.answerChoices = answerChoices;
        return this;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public ObjQuestionEntity setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
        return this;
    }
}
