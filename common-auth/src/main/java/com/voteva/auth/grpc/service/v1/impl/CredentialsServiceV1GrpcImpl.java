package com.voteva.auth.grpc.service.v1.impl;

import com.voteva.auth.converter.ModelConverter;
import com.voteva.auth.grpc.model.v1.GGetPrincipalKeyRequest;
import com.voteva.auth.grpc.model.v1.GGetPrincipalKeyResponse;
import com.voteva.auth.grpc.service.v1.CredentialsServiceV1Grpc;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.service.CredentialsService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class CredentialsServiceV1GrpcImpl extends CredentialsServiceV1Grpc.CredentialsServiceV1ImplBase {

    private final CredentialsService credentialsService;

    @Autowired
    public CredentialsServiceV1GrpcImpl(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @Override
    public void getPrincipalKey(
            GGetPrincipalKeyRequest request,
            StreamObserver<GGetPrincipalKeyResponse> responseObserver) {

        PrincipalKey principalKey = credentialsService.getPrincipalKey(
                request.getCredentials().getLogin().getValue(),
                request.getCredentials().getSecret().getValue());

        GGetPrincipalKeyResponse response = GGetPrincipalKeyResponse.newBuilder()
                .setPrincipalKey(ModelConverter.convert(principalKey))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
