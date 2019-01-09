package com.voteva.gateway.grpc.client;

import com.voteva.auth.grpc.model.v1.GGetPrincipalKeyRequest;
import com.voteva.auth.grpc.model.v1.GGetPrincipalKeyResponse;
import com.voteva.auth.grpc.service.v1.CredentialsServiceV1Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcCredentialsServiceClient {

    private CredentialsServiceV1Grpc.CredentialsServiceV1BlockingStub credentialsServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.CredentialsServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.CredentialsServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        credentialsServiceV1BlockingStub = CredentialsServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GGetPrincipalKeyResponse getPrincipalKey(GGetPrincipalKeyRequest request) {
        return credentialsServiceV1BlockingStub.getPrincipalKey(request);
    }
}
