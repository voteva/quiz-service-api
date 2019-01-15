package com.voteva.tests.grpc.client;

import com.voteva.auth.grpc.model.v1.GCheckAccessRequest;
import com.voteva.auth.grpc.service.v1.AccessServiceV1Grpc;
import com.voteva.common.grpc.model.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcAccessServiceClient {

    private AccessServiceV1Grpc.AccessServiceV1BlockingStub accessServiceV1BlockingStub;

    @Value("#{new String('${tests-api.grpc.client.AccessServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${tests-api.grpc.client.AccessServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        accessServiceV1BlockingStub = AccessServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public Empty checkAccess(GCheckAccessRequest request) {
        return accessServiceV1BlockingStub.checkAccess(request);
    }
}
