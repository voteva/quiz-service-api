package com.voteva.gateway.grpc.client;

import com.voteva.auth.grpc.model.v1.GOAuthAuthorizeRequest;
import com.voteva.auth.grpc.model.v1.GOAuthAuthorizeResponse;
import com.voteva.auth.grpc.model.v1.GOAuthRefreshTokenRequest;
import com.voteva.auth.grpc.model.v1.GOAuthRefreshTokenResponse;
import com.voteva.auth.grpc.model.v1.GOAuthTokenRequest;
import com.voteva.auth.grpc.model.v1.GOAuthTokenResponse;
import com.voteva.auth.grpc.service.v1.OAuthServiceV1Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcOAuthServiceClient {

    private OAuthServiceV1Grpc.OAuthServiceV1BlockingStub oAuthServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.OAuthServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.OAuthServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        oAuthServiceV1BlockingStub = OAuthServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GOAuthAuthorizeResponse authorize(GOAuthAuthorizeRequest request) {
        return oAuthServiceV1BlockingStub.authorize(request);
    }

    public GOAuthTokenResponse getToken(GOAuthTokenRequest request) {
        return oAuthServiceV1BlockingStub.getToken(request);
    }

    public GOAuthRefreshTokenResponse refreshToken(GOAuthRefreshTokenRequest request) {
        return oAuthServiceV1BlockingStub.refreshToken(request);
    }
}
