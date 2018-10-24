package com.voteva.gateway.grpc.client;

import com.voteva.quiz.grpc.model.v1.GUser2TestAssignRequest;
import com.voteva.quiz.grpc.model.v1.GUser2TestResponse;
import com.voteva.quiz.grpc.model.v1.GUser2TestResultRequest;
import com.voteva.quiz.grpc.model.v1.GUserResponse;
import com.voteva.quiz.grpc.model.v1.GUserUidRequest;
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

    public GUserResponse addUser(GUserUidRequest request) {
        return quizServiceV1BlockingStub.addUser(request);
    }

    public GUser2TestResponse assignTest(GUser2TestAssignRequest request) {
        return quizServiceV1BlockingStub.assignTest(request);
    }

    public GUser2TestResponse setTestResults(GUser2TestResultRequest request) {
        return quizServiceV1BlockingStub.setTestResults(request);
    }

    public void setAdminGrants(GUserUidRequest request) {
        quizServiceV1BlockingStub.setAdminGrants(request);
    }

    public void removeAdminGrants(GUserUidRequest request) {
        quizServiceV1BlockingStub.removeAdminGrants(request);
    }

    public void blockUser(GUserUidRequest request) {
        quizServiceV1BlockingStub.blockUser(request);
    }

    public void unblockUser(GUserUidRequest request) {
        quizServiceV1BlockingStub.unblockUser(request);
    }
}
