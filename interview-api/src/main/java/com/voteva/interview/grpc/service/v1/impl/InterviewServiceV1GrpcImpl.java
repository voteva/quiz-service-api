package com.voteva.interview.grpc.service.v1.impl;

import com.voteva.common.grpc.model.Empty;
import com.voteva.interview.converter.ModelConverter;
import com.voteva.interview.grpc.model.v1.GAddQuestionRequest;
import com.voteva.interview.grpc.model.v1.GAddQuestionResponse;
import com.voteva.interview.grpc.model.v1.GGetAllQuestionsRequest;
import com.voteva.interview.grpc.model.v1.GGetAllQuestionsResponse;
import com.voteva.interview.grpc.model.v1.GGetCategoriesRequest;
import com.voteva.interview.grpc.model.v1.GGetCategoriesResponse;
import com.voteva.interview.grpc.model.v1.GGetQuestionRequest;
import com.voteva.interview.grpc.model.v1.GGetQuestionResponse;
import com.voteva.interview.grpc.model.v1.GGetQuestionsByCategoryRequest;
import com.voteva.interview.grpc.model.v1.GGetQuestionsByCategoryResponse;
import com.voteva.interview.grpc.model.v1.GRemoveQuestionRequest;
import com.voteva.interview.grpc.service.v1.InterviewServiceV1Grpc;
import com.voteva.interview.model.entity.QuestionEntity;
import com.voteva.interview.service.InterviewService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@GRpcService
public class InterviewServiceV1GrpcImpl extends InterviewServiceV1Grpc.InterviewServiceV1ImplBase {

    private final InterviewService interviewService;

    @Autowired
    public InterviewServiceV1GrpcImpl(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @Override
    public void getCategories(
            GGetCategoriesRequest request,
            StreamObserver<GGetCategoriesResponse> responseObserver) {

        List<String> categories = interviewService.getCategories();

        responseObserver.onNext(GGetCategoriesResponse.newBuilder()
                .addAllCategories(categories)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllQuestions(
            GGetAllQuestionsRequest request,
            StreamObserver<GGetAllQuestionsResponse> responseObserver) {

        Page<QuestionEntity> questions = interviewService.getAllQuestions(
                ModelConverter.convert(request.getPageable()));

        responseObserver.onNext(GGetAllQuestionsResponse.newBuilder()
                .setPage(ModelConverter.convert(questions))
                .addAllQuestions(questions.getContent()
                        .stream()
                        .map(ModelConverter::convert)
                        .collect(Collectors.toList()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getQuestionsByCategory(
            GGetQuestionsByCategoryRequest request,
            StreamObserver<GGetQuestionsByCategoryResponse> responseObserver) {

        Page<QuestionEntity> questionsByCategory = interviewService.getQuestionsByCategory(
                request.getCategory(),
                ModelConverter.convert(request.getPageable()));

        responseObserver.onNext(GGetQuestionsByCategoryResponse.newBuilder()
                .setPage(ModelConverter.convert(questionsByCategory))
                .addAllQuestions(questionsByCategory.getContent()
                        .stream()
                        .map(ModelConverter::convert)
                        .collect(Collectors.toList()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getQuestion(
            GGetQuestionRequest request,
            StreamObserver<GGetQuestionResponse> responseObserver) {

        QuestionEntity question = interviewService.getQuestion(ModelConverter.convert(request.getQuestionUid()));

        responseObserver.onNext(GGetQuestionResponse.newBuilder()
                .setQuestion(ModelConverter.convert(question))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void addQuestion(
            GAddQuestionRequest request,
            StreamObserver<GAddQuestionResponse> responseObserver) {

        UUID questionUid = interviewService.addQuestion(ModelConverter.convert(request));

        responseObserver.onNext(GAddQuestionResponse.newBuilder()
                .setQuestionUid(ModelConverter.convert(questionUid))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeQuestion(
            GRemoveQuestionRequest request,
            StreamObserver<Empty> responseObserver) {

        interviewService.removeQuestion(ModelConverter.convert(request.getQuestionUid()));

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
