package com.voteva.tests.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class RefQuestionEntity {

    @Field(value = "questionText")
    private String questionText;

    @Field(value = "answerChoices")
    private List<String> answerChoices;

    @Field(value = "rightAnswer")
    private int rightAnswer;

    public String getQuestionText() {
        return questionText;
    }

    public RefQuestionEntity setQuestionText(String questionText) {
        this.questionText = questionText;
        return this;
    }

    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    public RefQuestionEntity setAnswerChoices(List<String> answerChoices) {
        this.answerChoices = answerChoices;
        return this;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public RefQuestionEntity setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
