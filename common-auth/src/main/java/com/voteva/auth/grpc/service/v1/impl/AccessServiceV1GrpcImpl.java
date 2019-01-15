package com.voteva.auth.grpc.service.v1.impl;

import com.voteva.auth.grpc.model.v1.GCheckAccessRequest;
import com.voteva.auth.grpc.service.v1.AccessServiceV1Grpc;
import com.voteva.auth.service.AccessService;
import com.voteva.common.grpc.model.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class AccessServiceV1GrpcImpl extends AccessServiceV1Grpc.AccessServiceV1ImplBase {

    private final AccessService accessService;

    @Autowired
    public AccessServiceV1GrpcImpl(AccessService accessService) {
        this.accessService = accessService;
    }

    @Override
    public void checkAccess(
            GCheckAccessRequest request,
            StreamObserver<Empty> responseObserver) {

        accessService.checkAccess(request.getAuthentication().getToken().getToken(), request.getGrant());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
