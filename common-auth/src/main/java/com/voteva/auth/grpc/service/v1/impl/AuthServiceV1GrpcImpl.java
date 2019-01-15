package com.voteva.auth.grpc.service.v1.impl;

import com.voteva.auth.converter.ModelConverter;
import com.voteva.auth.grpc.model.v1.GAuthenticateAnyRequest;
import com.voteva.auth.grpc.model.v1.GAuthenticateAnyResponse;
import com.voteva.auth.grpc.model.v1.GAuthenticateServiceRequest;
import com.voteva.auth.grpc.model.v1.GAuthenticateServiceResponse;
import com.voteva.auth.grpc.model.v1.GGenerateTokenRequest;
import com.voteva.auth.grpc.model.v1.GGenerateTokenResponse;
import com.voteva.auth.grpc.model.v1.GGetAuthenticationRequest;
import com.voteva.auth.grpc.model.v1.GGetAuthenticationResponse;
import com.voteva.auth.grpc.model.v1.GRevokeAuthenticationRequest;
import com.voteva.auth.grpc.service.v1.AuthServiceV1Grpc;
import com.voteva.auth.model.entity.Authentication;
import com.voteva.auth.security.Grants;
import com.voteva.auth.service.AccessService;
import com.voteva.auth.service.AuthenticationService;
import com.voteva.auth.service.TokenService;
import com.voteva.common.grpc.model.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class AuthServiceV1GrpcImpl extends AuthServiceV1Grpc.AuthServiceV1ImplBase {

    private final AccessService accessService;
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @Autowired
    public AuthServiceV1GrpcImpl(
            AccessService accessService,
            AuthenticationService authenticationService,
            TokenService tokenService) {

        this.accessService = accessService;
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    @Override
    public void authenticateAny(
            GAuthenticateAnyRequest request,
            StreamObserver<GAuthenticateAnyResponse> responseObserver) {

        accessService.checkAccess(
                ModelConverter.toToken(request.getAuthentication()),
                Grants.AUTHORIZATION_GRANT.getValue());

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
    public void authenticateService(
            GAuthenticateServiceRequest request,
            StreamObserver<GAuthenticateServiceResponse> responseObserver) {

        Authentication authentication = authenticationService.authenticateService(
                request.getServiceId(),
                request.getServiceSecret());

        GAuthenticateServiceResponse response = GAuthenticateServiceResponse.newBuilder()
                .setAuthentication(ModelConverter.convert(authentication))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAuthentication(
            GGetAuthenticationRequest request,
            StreamObserver<GGetAuthenticationResponse> responseObserver) {

        accessService.checkAccess(
                ModelConverter.toToken(request.getAuthentication()),
                Grants.AUTHORIZATION_GRANT.getValue());

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

        accessService.checkAccess(
                ModelConverter.toToken(request.getAuthentication()),
                Grants.AUTHORIZATION_GRANT.getValue());

        authenticationService.revokeAuthentication(request.getToken());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void generateToken(
            GGenerateTokenRequest request,
            StreamObserver<GGenerateTokenResponse> responseObserver) {

        accessService.checkAccess(
                ModelConverter.toToken(request.getAuthentication()),
                Grants.AUTHORIZATION_GRANT.getValue());

        GGenerateTokenResponse response = GGenerateTokenResponse.newBuilder()
                .setToken(ModelConverter.convert(tokenService.generateToken()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
