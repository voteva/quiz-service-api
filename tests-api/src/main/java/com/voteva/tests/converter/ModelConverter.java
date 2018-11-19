package com.voteva.tests.converter;

import com.voteva.common.grpc.model.GPage;
import com.voteva.common.grpc.model.GPageable;
import com.voteva.common.grpc.model.GUuid;
import com.voteva.tests.grpc.model.v1.GAddTestRequest;
import com.voteva.tests.grpc.model.v1.GQuestion;
import com.voteva.tests.grpc.model.v1.GTestInfo;
import com.voteva.tests.model.ref.QuestionRef;
import com.voteva.tests.model.entity.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
import java.util.stream.Collectors;

public class ModelConverter {

    public static GPage convert(Page page) {
        return GPage.newBuilder()
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .build();
    }

    public static Pageable convert(GPageable pageable) {
        return PageRequest.of(pageable.getPage(), pageable.getSize());
    }

    public static GUuid convert(UUID uuid) {
        return GUuid.newBuilder()
                .setUuid(String.valueOf(uuid))
                .build();
    }

    public static UUID convert(GUuid guuid) {
        return UUID.fromString(guuid.getUuid());
    }

    public static GQuestion convert(QuestionRef questionRef) {
        return GQuestion.newBuilder()
                .setText(questionRef.getQuestionText())
                .setRightAnswer(questionRef.getRightAnswer())
                .addAllAnswerChoices(questionRef.getAnswerChoices())
                .build();
    }

    public static QuestionRef convert(GQuestion question) {
        return new QuestionRef()
                .setQuestionText(question.getText())
                .setRightAnswer(question.getRightAnswer())
                .setAnswerChoices(question.getAnswerChoicesList());
    }

    public static GTestInfo convert(TestEntity testEntity) {
        return GTestInfo.newBuilder()
                .setTestUid(convert(testEntity.getTestUid()))
                .setTestName(testEntity.getTestName())
                .setTestCategory(testEntity.getTestCategory())
                .addAllQuestions(testEntity.getQuestions()
                        .stream()
                        .map(ModelConverter::convert)
                        .collect(Collectors.toList()))
                .build();
    }

    public static TestEntity convert(GTestInfo testInfo) {
        return new TestEntity()
                .setTestUid(convert(testInfo.getTestUid()))
                .setTestName(testInfo.getTestName())
                .setTestCategory(testInfo.getTestCategory())
                .setQuestions(testInfo.getQuestionsList()
                        .stream()
                        .map(ModelConverter::convert)
                        .collect(Collectors.toList()));
    }

    public static TestEntity convert(GAddTestRequest request) {
        return new TestEntity()
                .setTestName(request.getTestName())
                .setTestCategory(request.getTestCategory())
                .setQuestions(request.getQuestionsList()
                        .stream()
                        .map(ModelConverter::convert)
                        .collect(Collectors.toList()));
    }
}
