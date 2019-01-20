package com.voteva.auth.grpc.service.v1.impl;

import com.voteva.auth.converter.ModelConverter;
import com.voteva.auth.grpc.model.v1.GAddCredentialsRequest;
import com.voteva.auth.grpc.model.v1.GGetPrincipalKeyRequest;
import com.voteva.auth.grpc.model.v1.GGetPrincipalKeyResponse;
import com.voteva.auth.grpc.service.v1.CredentialsServiceV1Grpc;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.security.Grants;
import com.voteva.auth.service.AccessService;
import com.voteva.auth.service.CredentialsService;
import com.voteva.common.grpc.model.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class CredentialsServiceV1GrpcImpl extends CredentialsServiceV1Grpc.CredentialsServiceV1ImplBase {

    private final AccessService accessService;
    private final CredentialsService credentialsService;

    @Autowired
    public CredentialsServiceV1GrpcImpl(
            AccessService accessService,
            CredentialsService credentialsService) {

        this.accessService = accessService;
        this.credentialsService = credentialsService;
    }

    @Override
    public void getPrincipalKey(
            GGetPrincipalKeyRequest request,
            StreamObserver<GGetPrincipalKeyResponse> responseObserver) {

        accessService.checkAccess(
                ModelConverter.toToken(request.getAuthentication()),
                Grants.AUTHORIZATION_GRANT.getValue());

        PrincipalKey principalKey = credentialsService.getPrincipalKey(
                request.getCredentials().getLogin().getValue(),
                request.getCredentials().getSecret().getValue());

        GGetPrincipalKeyResponse response = GGetPrincipalKeyResponse.newBuilder()
                .setPrincipalKey(ModelConverter.convert(principalKey))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addCredentials(
            GAddCredentialsRequest request,
            StreamObserver<Empty> responseObserver) {

        accessService.checkAccess(
                ModelConverter.toToken(request.getAuthentication()),
                Grants.AUTHORIZATION_GRANT.getValue());

        credentialsService.addCredentials(
                ModelConverter.convert(request.getPrincipalKey()),
                request.getCredentials().getLogin().getValue(),
                request.getCredentials().getSecret().getValue());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
