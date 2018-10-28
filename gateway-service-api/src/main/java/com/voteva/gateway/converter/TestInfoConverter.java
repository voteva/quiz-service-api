package com.voteva.gateway.converter;

import com.voteva.gateway.web.to.common.QuestionInfo;
import com.voteva.gateway.web.to.common.TestInfo;
import com.voteva.gateway.web.to.in.AddTestRequest;
import com.voteva.tests.grpc.model.v1.GAddTestRequest;
import com.voteva.tests.grpc.model.v1.GQuestion;
import com.voteva.tests.grpc.model.v1.GTestInfo;

import java.util.stream.Collectors;

public class TestInfoConverter {

    public static TestInfo convert(GTestInfo testInfo) {
        return new TestInfo()
                .setTestUid(CommonConverter.convert(testInfo.getTestUid()))
                .setTestName(testInfo.getTestName())
                .setTestCategory(testInfo.getTestCategory())
                .setQuestions(testInfo.getQuestionsList().stream()
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

    private static GQuestion convert(QuestionInfo questionInfo) {
        return GQuestion.newBuilder()
                .setText(questionInfo.getQuestionText())
                .setRightAnswer(questionInfo.getRightAnswer())
                .addAllAnswerChoices(questionInfo.getAnswerChoices())
                .build();
    }

    private static QuestionInfo convert(GQuestion question) {
        return new QuestionInfo(
                question.getText(),
                question.getAnswerChoicesList(),
                question.getRightAnswer());
    }
}
