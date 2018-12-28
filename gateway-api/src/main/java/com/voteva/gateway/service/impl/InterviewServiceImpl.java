package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.InterviewInfoConverter;
import com.voteva.gateway.exception.util.GatewayService;
import com.voteva.gateway.grpc.client.GRpcInterviewServiceClient;
import com.voteva.gateway.service.InterviewService;
import com.voteva.gateway.util.Logged;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddInterviewQuestionRequest;
import com.voteva.gateway.web.to.out.InterviewQuestionInfo;
import com.voteva.interview.grpc.model.v1.GGetAllQuestionsRequest;
import com.voteva.interview.grpc.model.v1.GGetAllQuestionsResponse;
import com.voteva.interview.grpc.model.v1.GGetCategoriesRequest;
import com.voteva.interview.grpc.model.v1.GGetQuestionRequest;
import com.voteva.interview.grpc.model.v1.GGetQuestionsByCategoryRequest;
import com.voteva.interview.grpc.model.v1.GGetQuestionsByCategoryResponse;
import com.voteva.interview.grpc.model.v1.GRemoveQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.voteva.gateway.exception.model.Service.INTERVIEW;

@GatewayService(serviceName = INTERVIEW)
public class InterviewServiceImpl implements InterviewService {

    private final GRpcInterviewServiceClient rpcInterviewServiceClient;

    @Autowired
    public InterviewServiceImpl(GRpcInterviewServiceClient rpcInterviewServiceClient) {
        this.rpcInterviewServiceClient = rpcInterviewServiceClient;
    }

    @Logged
    @Override
    public List<String> getQuestionCategories() {
        return rpcInterviewServiceClient.getCategories(
                GGetCategoriesRequest.newBuilder().build())
                .getCategoriesList();
    }

    @Logged
    @Override
    public PagedResult<InterviewQuestionInfo> getQuestions(String category, int page, int size) {
        return Optional.ofNullable(category)
                .map(c -> getQuestionsByCategoryInternal(c, page, size))
                .orElseGet(() -> getQuestionsInternal(page, size));
    }

    @Logged
    @Override
    public InterviewQuestionInfo getQuestionInfo(UUID questionUid) {
        return InterviewInfoConverter.convert(
                rpcInterviewServiceClient.getQuestion(
                        GGetQuestionRequest.newBuilder()
                                .setQuestionUid(CommonConverter.convert(questionUid))
                                .build())
                        .getQuestion());
    }

    @Logged
    @Override
    public UUID addQuestion(AddInterviewQuestionRequest request) {
        return CommonConverter.convert(
                rpcInterviewServiceClient.addQuestion(
                        InterviewInfoConverter.convert(request))
                        .getQuestionUid());
    }

    @Logged
    @Override
    public void deleteQuestion(UUID questionUid) {
        rpcInterviewServiceClient.removeQuestion(
                GRemoveQuestionRequest.newBuilder()
                        .setQuestionUid(CommonConverter.convert(questionUid))
                        .build());
    }

    private PagedResult<InterviewQuestionInfo> getQuestionsByCategoryInternal(String category, int page, int size) {
        GGetQuestionsByCategoryResponse response = rpcInterviewServiceClient.getQuestionsByCategory(
                GGetQuestionsByCategoryRequest.newBuilder()
                        .setCategory(category)
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return InterviewInfoConverter.convert(response.getQuestionsList(), response.getPage());
    }

    private PagedResult<InterviewQuestionInfo> getQuestionsInternal(int page, int size) {
        GGetAllQuestionsResponse response = rpcInterviewServiceClient.getAllQuestions(
                GGetAllQuestionsRequest.newBuilder()
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return InterviewInfoConverter.convert(response.getQuestionsList(), response.getPage());
    }
}
