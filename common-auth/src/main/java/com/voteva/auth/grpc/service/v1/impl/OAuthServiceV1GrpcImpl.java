package com.voteva.auth.grpc.service.v1.impl;

import com.voteva.auth.converter.ModelConverter;
import com.voteva.auth.grpc.model.v1.GOAuthAuthorizeRequest;
import com.voteva.auth.grpc.model.v1.GOAuthAuthorizeResponse;
import com.voteva.auth.grpc.model.v1.GOAuthRefreshTokenRequest;
import com.voteva.auth.grpc.model.v1.GOAuthRefreshTokenResponse;
import com.voteva.auth.grpc.model.v1.GOAuthTokenRequest;
import com.voteva.auth.grpc.model.v1.GOAuthTokenResponse;
import com.voteva.auth.grpc.service.v1.OAuthServiceV1Grpc;
import com.voteva.auth.model.entity.OAuthToken;
import com.voteva.auth.security.Grants;
import com.voteva.auth.service.AccessService;
import com.voteva.auth.service.OAuthService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class OAuthServiceV1GrpcImpl extends OAuthServiceV1Grpc.OAuthServiceV1ImplBase {

    private final AccessService accessService;
    private final OAuthService oAuthService;

    @Autowired
    public OAuthServiceV1GrpcImpl(
            AccessService accessService,
            OAuthService oAuthService) {

        this.accessService = accessService;
        this.oAuthService = oAuthService;
    }

    @Override
    public void authorize(
            GOAuthAuthorizeRequest request,
            StreamObserver<GOAuthAuthorizeResponse> responseObserver) {

        accessService.checkAccess(
                ModelConverter.toToken(request.getAuthentication()),
                Grants.OAUTH_GRANT.getValue());

        String authorizationCode = oAuthService.authorize(request.getClientId(), request.getUserAccessToken());

        GOAuthAuthorizeResponse response = GOAuthAuthorizeResponse.newBuilder()
                .setAuthorizationCode(authorizationCode)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getToken(
            GOAuthTokenRequest request,
            StreamObserver<GOAuthTokenResponse> responseObserver) {

        accessService.checkAccess(
                ModelConverter.toToken(request.getAuthentication()),
                Grants.OAUTH_GRANT.getValue());

        OAuthToken token = oAuthService.getToken(
                request.getClientId(),
                request.getClientSecret(),
                request.getAuthorizationCode());

        GOAuthTokenResponse response = GOAuthTokenResponse.newBuilder()
                .setAccessToken(token.getAccessToken())
                .setTokenType(token.getTokenType())
                .setExpiresIn(token.getExpiresIn().toEpochMilli())
                .setRefreshToken(token.getRefreshToken())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void refreshToken(
            GOAuthRefreshTokenRequest request,
            StreamObserver<GOAuthRefreshTokenResponse> responseObserver) {

        accessService.checkAccess(
                ModelConverter.toToken(request.getAuthentication()),
                Grants.OAUTH_GRANT.getValue());

        OAuthToken token = oAuthService.refreshToken(
                request.getClientId(),
                request.getClientSecret(),
                request.getRefreshToken());

        GOAuthRefreshTokenResponse response = GOAuthRefreshTokenResponse.newBuilder()
                .setAccessToken(token.getAccessToken())
                .setTokenType(token.getTokenType())
                .setExpiresIn(token.getExpiresIn().toEpochMilli())
                .setRefreshToken(token.getRefreshToken())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
