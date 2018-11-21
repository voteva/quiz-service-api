package com.voteva.gateway.grpc.client;

import com.voteva.common.grpc.model.Empty;
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
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcInterviewServiceClient {

    private InterviewServiceV1Grpc.InterviewServiceV1BlockingStub interviewServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.InterviewServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.InterviewServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        interviewServiceV1BlockingStub = InterviewServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GGetCategoriesResponse getCategories(GGetCategoriesRequest request) {
        return interviewServiceV1BlockingStub.getCategories(request);
    }

    public GGetAllQuestionsResponse getAllQuestions(GGetAllQuestionsRequest request) {
        return interviewServiceV1BlockingStub.getAllQuestions(request);
    }

    public GGetQuestionsByCategoryResponse getQuestionsByCategory(GGetQuestionsByCategoryRequest request) {
        return interviewServiceV1BlockingStub.getQuestionsByCategory(request);
    }

    public GGetQuestionResponse getQuestion(GGetQuestionRequest request) {
        return interviewServiceV1BlockingStub.getQuestion(request);
    }

    public GAddQuestionResponse addQuestion(GAddQuestionRequest request) {
        return interviewServiceV1BlockingStub.addQuestion(request);
    }

    public Empty removeQuestion(GRemoveQuestionRequest request) {
        return interviewServiceV1BlockingStub.removeQuestion(request);
    }
}
