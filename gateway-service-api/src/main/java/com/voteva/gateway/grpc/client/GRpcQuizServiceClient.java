package com.voteva.gateway.grpc.client;

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

}
