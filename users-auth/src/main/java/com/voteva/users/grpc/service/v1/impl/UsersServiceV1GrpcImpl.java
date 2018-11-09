package com.voteva.users.grpc.service.v1.impl;

import com.voteva.users.converter.ModelConverter;
import com.voteva.users.grpc.model.v1.GAddUserAuthRequest;
import com.voteva.users.grpc.model.v1.GAddUserAuthResponse;
import com.voteva.users.grpc.service.v1.UsersServiceV1Grpc;
import com.voteva.users.service.UsersService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GRpcService
public class UsersServiceV1GrpcImpl extends UsersServiceV1Grpc.UsersServiceV1ImplBase {

    private final UsersService usersService;

    @Autowired
    public UsersServiceV1GrpcImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void addUser(GAddUserAuthRequest request,
                        StreamObserver<GAddUserAuthResponse> responseObserver) {
        try {
            UUID userUid = usersService.addUser(request.getEmail(), request.getPassword());

            GAddUserAuthResponse response = GAddUserAuthResponse.newBuilder()
                    .setUserUid(ModelConverter.convert(userUid))
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
