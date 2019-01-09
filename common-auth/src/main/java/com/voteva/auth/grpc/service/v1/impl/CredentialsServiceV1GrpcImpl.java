package com.voteva.auth.grpc.service.v1.impl;

import com.voteva.auth.converter.ModelConverter;
import com.voteva.auth.grpc.model.v1.GGetPrincipalKeyRequest;
import com.voteva.auth.grpc.model.v1.GGetPrincipalKeyResponse;
import com.voteva.auth.grpc.service.v1.CredentialsServiceV1Grpc;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.service.CredentialsService;
import io.grpc.Status;
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
        try {
            PrincipalKey principalKey = credentialsService.getPrincipalKey(
                    request.getSubsystem(),
                    ModelConverter.convert(request.getCredentials()));

            GGetPrincipalKeyResponse response = GGetPrincipalKeyResponse.newBuilder()
                    .setPrincipalKey(ModelConverter.convert(principalKey))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    private void onError(StreamObserver<?> responseObserver, Exception e) {
        Status status;

        if (e instanceof IllegalArgumentException) {
            status = Status.INVALID_ARGUMENT;
        } else {
            status = Status.INTERNAL;
        }

        responseObserver.onError(status
                .withDescription(e.getMessage())
                .asRuntimeException());
    }
}
