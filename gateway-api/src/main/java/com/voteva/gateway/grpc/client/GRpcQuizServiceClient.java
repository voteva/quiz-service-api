package com.voteva.gateway.grpc.client;

import com.voteva.common.grpc.model.Empty;
import com.voteva.quiz.grpc.model.v1.GAddUserRequest;
import com.voteva.quiz.grpc.model.v1.GAddUserResponse;
import com.voteva.quiz.grpc.model.v1.GAssignTestRequest;
import com.voteva.quiz.grpc.model.v1.GBlockUserRequest;
import com.voteva.quiz.grpc.model.v1.GDeleteResultsRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersResponse;
import com.voteva.quiz.grpc.model.v1.GGetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GGetTestResultsResponse;
import com.voteva.quiz.grpc.model.v1.GGetUserRequest;
import com.voteva.quiz.grpc.model.v1.GGetUserResponse;
import com.voteva.quiz.grpc.model.v1.GRemoveAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsResponse;
import com.voteva.quiz.grpc.model.v1.GUnblockUserRequest;
import com.voteva.quiz.grpc.model.v1.GUpdateUserRequest;
import com.voteva.quiz.grpc.service.v1.QuizServiceV1Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcQuizServiceClient {

    private QuizServiceV1Grpc.QuizServiceV1BlockingStub quizServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.QuizServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.QuizServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        quizServiceV1BlockingStub = QuizServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public Empty assignTest(GAssignTestRequest request) {
        return quizServiceV1BlockingStub.assignTest(request);
    }

    public GGetTestResultsResponse getTestResults(GGetTestResultsRequest request){
        return quizServiceV1BlockingStub.getTestResults(request);
    }

    public GSetTestResultsResponse setTestResults(GSetTestResultsRequest request) {
        return quizServiceV1BlockingStub.setTestResults(request);
    }

    public Empty deleteResultsForTest(GDeleteResultsRequest request) {
        return quizServiceV1BlockingStub.deleteResultsForTest(request);
    }

    public GGetAllUsersResponse getAllUsers(GGetAllUsersRequest request) {
        return quizServiceV1BlockingStub.getAllUsers(request);
    }

    public GGetUserResponse getUser(GGetUserRequest request) {
        return quizServiceV1BlockingStub.getUser(request);
    }

    public GAddUserResponse addUser(GAddUserRequest request) {
        return quizServiceV1BlockingStub.addUser(request);
    }

    public Empty updateUser(GUpdateUserRequest request) {
        return quizServiceV1BlockingStub.updateUser(request);
    }

    public Empty setAdminGrants(GSetAdminGrantsRequest request) {
        return quizServiceV1BlockingStub.setAdminGrants(request);
    }

    public Empty removeAdminGrants(GRemoveAdminGrantsRequest request) {
        return quizServiceV1BlockingStub.removeAdminGrants(request);
    }

    public Empty blockUser(GBlockUserRequest request) {
        return quizServiceV1BlockingStub.blockUser(request);
    }

    public Empty unblockUser(GUnblockUserRequest request) {
        return quizServiceV1BlockingStub.unblockUser(request);
    }
}
