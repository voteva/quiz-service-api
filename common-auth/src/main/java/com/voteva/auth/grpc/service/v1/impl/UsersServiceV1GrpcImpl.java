package com.voteva.auth.grpc.service.v1.impl;

import com.voteva.auth.converter.ModelConverter;
import com.voteva.auth.service.UsersService;
import com.voteva.users.grpc.model.v1.GAddUserAuthRequest;
import com.voteva.users.grpc.model.v1.GAddUserAuthResponse;
import com.voteva.users.grpc.service.v1.UsersServiceV1Grpc;
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
    public void addUser(
            GAddUserAuthRequest request,
            StreamObserver<GAddUserAuthResponse> responseObserver) {

        UUID userUid = usersService.addUser(request.getEmail(), request.getPassword());

        GAddUserAuthResponse response = GAddUserAuthResponse.newBuilder()
                .setUserUid(ModelConverter.convert(userUid))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
