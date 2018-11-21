package com.voteva.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class AddInterviewQuestionInfo {

    private UUID questionUid;

    public AddInterviewQuestionInfo(UUID questionUid) {
        this.questionUid = questionUid;
    }

    @JsonGetter("questionUid")
    public UUID getQuestionUid() {
        return questionUid;
    }
}
