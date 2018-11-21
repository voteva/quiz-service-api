package com.voteva.gateway.service;

import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddInterviewQuestionRequest;
import com.voteva.gateway.web.to.out.InterviewQuestionInfo;

import java.util.List;
import java.util.UUID;

public interface InterviewService {

    List<String> getQuestionCategories();

    PagedResult<InterviewQuestionInfo> getQuestions(String category, int page, int size);

    InterviewQuestionInfo getQuestionInfo(UUID questionUid);

    UUID addQuestion(AddInterviewQuestionRequest request);

    void deleteQuestion(UUID questionUid);
}
