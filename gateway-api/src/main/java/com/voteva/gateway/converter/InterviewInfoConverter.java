package com.voteva.gateway.converter;

import com.voteva.common.grpc.model.GPage;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddInterviewQuestionRequest;
import com.voteva.gateway.web.to.out.InterviewQuestionInfo;
import com.voteva.interview.grpc.model.v1.GAddQuestionRequest;
import com.voteva.interview.grpc.model.v1.GInterviewQuestionInfo;

import java.util.List;
import java.util.stream.Collectors;

public class InterviewInfoConverter {

    public static PagedResult<InterviewQuestionInfo> convert(
            List<GInterviewQuestionInfo> questionInfoList, GPage page) {
        return new PagedResult<>(
                page.getTotalElements(),
                questionInfoList.stream()
                        .map(InterviewInfoConverter::convert)
                        .collect(Collectors.toList()));
    }

    public static InterviewQuestionInfo convert(GInterviewQuestionInfo questionInfo) {
        return new InterviewQuestionInfo(
                CommonConverter.convert(questionInfo.getQuestionUid()),
                questionInfo.getQuestionName(),
                questionInfo.getQuestionText(),
                questionInfo.getQuestionAnswer(),
                questionInfo.getCategory());
    }

    public static GAddQuestionRequest convert(AddInterviewQuestionRequest request) {
        return GAddQuestionRequest.newBuilder()
                .setQuestionName(request.getQuestionName())
                .setQuestionText(request.getQuestionText())
                .setQuestionAnswer(request.getQuestionAnswer())
                .setQuestionCategory(request.getCategory())
                .build();
    }
}
