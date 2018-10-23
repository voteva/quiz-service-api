package com.voteva.gateway.grpc.client;

import com.voteva.tests.grpc.service.v1.TestsServiceV1Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcTestsServiceClient {

    private TestsServiceV1Grpc.TestsServiceV1BlockingStub testsServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.TestsServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.TestsServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        testsServiceV1BlockingStub = TestsServiceV1Grpc.newBlockingStub(managedChannel);
    }

}
