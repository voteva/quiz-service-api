package com.voteva.interview.converter;

import com.voteva.common.grpc.model.GPage;
import com.voteva.common.grpc.model.GPageable;
import com.voteva.common.grpc.model.GUuid;
import com.voteva.interview.grpc.model.v1.GAddQuestionRequest;
import com.voteva.interview.grpc.model.v1.GInterviewQuestionInfo;
import com.voteva.interview.model.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

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

    public static GInterviewQuestionInfo convert(QuestionEntity entity) {
        return GInterviewQuestionInfo.newBuilder()
                .setQuestionUid(convert(entity.getQuestionUid()))
                .setQuestionName(entity.getQuestionName())
                .setCategory(entity.getCategory())
                .setQuestionText(entity.getQuestionText())
                .setQuestionAnswer(entity.getQuestionAnswer())
                .build();
    }

    public static QuestionEntity convert(GAddQuestionRequest request) {
        return new QuestionEntity()
                .setQuestionName(request.getQuestionName())
                .setCategory(request.getQuestionCategory())
                .setQuestionText(request.getQuestionText())
                .setQuestionAnswer(request.getQuestionAnswer());
    }
}
