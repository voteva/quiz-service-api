package com.voteva.gateway.converter;

import com.voteva.common.grpc.model.GPage;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.out.TestQuestionInfo;
import com.voteva.gateway.web.to.out.TestInfo;
import com.voteva.gateway.web.to.in.AddTestQuestionRequest;
import com.voteva.gateway.web.to.in.AddTestRequest;
import com.voteva.tests.grpc.model.v1.GAddTestRequest;
import com.voteva.tests.grpc.model.v1.GQuestion;
import com.voteva.tests.grpc.model.v1.GTestInfo;

import java.util.List;
import java.util.stream.Collectors;

public class TestInfoConverter {

    public static PagedResult<TestInfo> convert(List<GTestInfo> testInfoList, GPage page) {
        return new PagedResult<>(
                page.getTotalElements(),
                testInfoList.stream()
                        .map(TestInfoConverter::convert)
                        .collect(Collectors.toList()));
    }

    public static TestInfo convert(GTestInfo testInfo) {
        return new TestInfo(
                CommonConverter.convert(testInfo.getTestUid()),
                testInfo.getTestName(),
                testInfo.getTestCategory(),
                testInfo.getQuestionsList().stream()
                        .map(TestInfoConverter::convert)
                        .collect(Collectors.toList()));
    }

    public static GAddTestRequest convert(AddTestRequest request) {
        return GAddTestRequest.newBuilder()
                .setTestName(request.getTestName())
                .setTestCategory(request.getTestCategory())
                .addAllQuestions(request.getQuestions().stream()
                        .map(TestInfoConverter::convert)
                        .collect(Collectors.toList()))
                .build();
    }

    public static GQuestion convert(AddTestQuestionRequest questionRequest) {
        return GQuestion.newBuilder()
                .setText(questionRequest.getQuestionText())
                .setRightAnswer(questionRequest.getRightAnswer())
                .addAllAnswerChoices(questionRequest.getAnswerChoices())
                .build();
    }

    private static TestQuestionInfo convert(GQuestion question) {
        return new TestQuestionInfo(
                question.getText(),
                question.getAnswerChoicesList(),
                question.getRightAnswer());
    }
}
