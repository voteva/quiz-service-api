package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class InterviewQuestionInfo {

    private UUID questionUid;
    private String questionName;
    private String questionText;
    private String questionAnswer;
    private String category;

    public InterviewQuestionInfo(
            UUID questionUid,
            String questionName,
            String questionText,
            String questionAnswer,
            String category) {
        this.questionUid = questionUid;
        this.questionName = questionName;
        this.questionText = questionText;
        this.questionAnswer = questionAnswer;
        this.category = category;
    }

    @JsonGetter("questionUid")
    public UUID getQuestionUid() {
        return questionUid;
    }

    @JsonGetter("questionName")
    public String getQuestionName() {
        return questionName;
    }

    @JsonGetter("questionText")
    public String getQuestionText() {
        return questionText;
    }

    @JsonGetter("questionAnswer")
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    @JsonGetter("category")
    public String getCategory() {
        return category;
    }
}
