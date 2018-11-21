package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.InterviewInfoConverter;
import com.voteva.gateway.exception.util.GRpcExceptionUtil;
import com.voteva.gateway.grpc.client.GRpcInterviewServiceClient;
import com.voteva.gateway.service.InterviewService;
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
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InterviewServiceImpl implements InterviewService {

    private static final Logger logger = LoggerFactory.getLogger(InterviewServiceImpl.class);

    private final GRpcInterviewServiceClient rpcInterviewServiceClient;

    @Autowired
    public InterviewServiceImpl(GRpcInterviewServiceClient rpcInterviewServiceClient) {
        this.rpcInterviewServiceClient = rpcInterviewServiceClient;
    }

    @Override
    public List<String> getQuestionCategories() {
        try {
            return rpcInterviewServiceClient.getCategories(
                    GGetCategoriesRequest.newBuilder().build())
                    .getCategoriesList();

        } catch (StatusRuntimeException e) {
            logger.error("Failed to get question categories");
            throw GRpcExceptionUtil.convertByInterview(e);
        }
    }

    @Override
    public PagedResult<InterviewQuestionInfo> getQuestions(String category, int page, int size) {
        try {
            return Optional.ofNullable(category)
                    .map(c -> getQuestionsByCategoryInternal(c, page, size))
                    .orElseGet(() -> getQuestionsInternal(page, size));

        } catch (StatusRuntimeException e) {
            logger.error("Failed to get all question by category: {} for page: {} and page size: {}", category, page, size);
            throw GRpcExceptionUtil.convertByInterview(e);
        }
    }

    @Override
    public InterviewQuestionInfo getQuestionInfo(UUID questionUid) {
        try {
            return InterviewInfoConverter.convert(
                    rpcInterviewServiceClient.getQuestion(
                            GGetQuestionRequest.newBuilder()
                                    .setQuestionUid(CommonConverter.convert(questionUid))
                                    .build())
                            .getQuestion());

        } catch (StatusRuntimeException e) {
            logger.error("Failed to get question info by uid: {}", questionUid);
            throw GRpcExceptionUtil.convertByInterview(e);
        }
    }

    @Override
    public UUID addQuestion(AddInterviewQuestionRequest request) {
        try {
            return CommonConverter.convert(
                    rpcInterviewServiceClient.addQuestion(
                            InterviewInfoConverter.convert(request))
                            .getQuestionUid());

        } catch (StatusRuntimeException e) {
            logger.error("Failed to add question with name: {}", request.getQuestionName());
            throw GRpcExceptionUtil.convertByInterview(e);
        }
    }

    @Override
    public void deleteQuestion(UUID questionUid) {
        try {
            rpcInterviewServiceClient.removeQuestion(
                    GRemoveQuestionRequest.newBuilder()
                            .setQuestionUid(CommonConverter.convert(questionUid))
                            .build());

        } catch (StatusRuntimeException e) {
            logger.error("Failed to delete question with uid: {}", questionUid);
            throw GRpcExceptionUtil.convertByInterview(e);
        }
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
