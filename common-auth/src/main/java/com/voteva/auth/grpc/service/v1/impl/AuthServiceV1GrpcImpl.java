package com.voteva.auth.grpc.service.v1.impl;

import com.voteva.auth.converter.ModelConverter;
import com.voteva.auth.grpc.model.v1.GAuthenticateAnyRequest;
import com.voteva.auth.grpc.model.v1.GAuthenticateAnyResponse;
import com.voteva.auth.grpc.model.v1.GGenerateTokenRequest;
import com.voteva.auth.grpc.model.v1.GGenerateTokenResponse;
import com.voteva.auth.grpc.model.v1.GGetAuthenticationRequest;
import com.voteva.auth.grpc.model.v1.GGetAuthenticationResponse;
import com.voteva.auth.grpc.model.v1.GRevokeAuthenticationRequest;
import com.voteva.auth.grpc.service.v1.AuthServiceV1Grpc;
import com.voteva.auth.model.entity.Authentication;
import com.voteva.auth.service.AuthenticationService;
import com.voteva.auth.service.TokenService;
import com.voteva.common.grpc.model.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class AuthServiceV1GrpcImpl extends AuthServiceV1Grpc.AuthServiceV1ImplBase {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @Autowired
    public AuthServiceV1GrpcImpl(
            AuthenticationService authenticationService,
            TokenService tokenService) {

        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    @Override
    public void authenticateAny(
            GAuthenticateAnyRequest request,
            StreamObserver<GAuthenticateAnyResponse> responseObserver) {

        Authentication authentication = authenticationService.authenticateAny(
                ModelConverter.convert(request.getToken()),
                ModelConverter.convert(request.getPrincipalKey()),
                ModelConverter.convert(request.getFingerPrint()));

        GAuthenticateAnyResponse response = GAuthenticateAnyResponse.newBuilder()
                .setAuthentication(ModelConverter.convert(authentication))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAuthentication(
            GGetAuthenticationRequest request,
            StreamObserver<GGetAuthenticationResponse> responseObserver) {

        Authentication authentication = authenticationService.getAuthentication(request.getToken());

        GGetAuthenticationResponse response = GGetAuthenticationResponse.newBuilder()
                .setAuthentication(ModelConverter.convert(authentication))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void revokeAuthentication(
            GRevokeAuthenticationRequest request,
            StreamObserver<Empty> responseObserver) {

        authenticationService.revokeAuthentication(request.getToken());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void generateToken(
            GGenerateTokenRequest request,
            StreamObserver<GGenerateTokenResponse> responseObserver) {

        GGenerateTokenResponse response = GGenerateTokenResponse.newBuilder()
                .setToken(ModelConverter.convert(tokenService.generateToken()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
