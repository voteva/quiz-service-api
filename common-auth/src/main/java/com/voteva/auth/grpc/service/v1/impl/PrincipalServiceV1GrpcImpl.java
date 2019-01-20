package com.voteva.auth.grpc.service.v1.impl;

import com.voteva.auth.converter.ModelConverter;
import com.voteva.auth.grpc.model.v1.GAddPrincipalRequest;
import com.voteva.auth.grpc.model.v1.GAddPrincipalResponse;
import com.voteva.auth.grpc.model.v1.GDeletePrincipalRequest;
import com.voteva.auth.grpc.service.v1.PrincipalServiceV1Grpc;
import com.voteva.auth.model.entity.PrincipalKey;
import com.voteva.auth.service.PrincipalService;
import com.voteva.common.grpc.model.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class PrincipalServiceV1GrpcImpl extends PrincipalServiceV1Grpc.PrincipalServiceV1ImplBase {

    private final PrincipalService principalService;

    @Autowired
    public PrincipalServiceV1GrpcImpl(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @Override
    public void addPrincipal(
            GAddPrincipalRequest request,
            StreamObserver<GAddPrincipalResponse> responseObserver) {

        PrincipalKey principal = principalService.addPrincipal(request.getPrincipalExtId());

        GAddPrincipalResponse response = GAddPrincipalResponse.newBuilder()
                .setPrincipalKey(ModelConverter.convert(principal))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deletePrincipal(
            GDeletePrincipalRequest request,
            StreamObserver<Empty> responseObserver) {

        principalService.deletePrincipal(request.getPrincipalExtId());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
