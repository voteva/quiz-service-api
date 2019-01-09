package com.voteva.gateway.grpc.client;

import com.voteva.auth.grpc.model.v1.GAuthenticateAnyRequest;
import com.voteva.auth.grpc.model.v1.GAuthenticateAnyResponse;
import com.voteva.auth.grpc.model.v1.GGenerateTokenRequest;
import com.voteva.auth.grpc.model.v1.GGenerateTokenResponse;
import com.voteva.auth.grpc.model.v1.GGetAuthenticationRequest;
import com.voteva.auth.grpc.model.v1.GGetAuthenticationResponse;
import com.voteva.auth.grpc.service.v1.AuthServiceV1Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcAuthServiceClient {

    private AuthServiceV1Grpc.AuthServiceV1BlockingStub authServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.AuthServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.AuthServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        authServiceV1BlockingStub = AuthServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GGenerateTokenResponse generateToken(GGenerateTokenRequest request) {
        return authServiceV1BlockingStub.generateToken(request);
    }

    public GGetAuthenticationResponse getAuthentication(GGetAuthenticationRequest request) {
        return authServiceV1BlockingStub.getAuthentication(request);
    }

    public GAuthenticateAnyResponse authenticateAny(GAuthenticateAnyRequest request) {
        return authServiceV1BlockingStub.authenticateAny(request);
    }
}
