package com.voteva.users.grpc.service.impl.v1;

import com.voteva.users.converter.ModelConverter;
import com.voteva.users.exception.NotFoundUserException;
import com.voteva.users.grpc.model.v1.GAddUserRequest;
import com.voteva.users.grpc.model.v1.GObjUserInfo;
import com.voteva.users.grpc.model.v1.GUpdateUserRequest;
import com.voteva.users.grpc.model.v1.GUserEmailRequest;
import com.voteva.users.grpc.model.v1.GUserInfoResponse;
import com.voteva.users.grpc.model.v1.GUserUidRequest;
import com.voteva.users.grpc.service.v1.UsersServiceV1Grpc;
import com.voteva.users.service.UsersService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GRpcService
public class UsersServiceGrpcV1 extends UsersServiceV1Grpc.UsersServiceV1ImplBase {

    private final UsersService usersService;

    @Autowired
    public UsersServiceGrpcV1(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void getUserInfoByUid(GUserUidRequest request, StreamObserver<GUserInfoResponse> responseObserver) {
        try {
            GObjUserInfo userInfo = ModelConverter.convert(
                    usersService.getUserByUid(UUID.fromString(request.getUuid())));

            GUserInfoResponse response = GUserInfoResponse.newBuilder()
                    .setObjUserInfo(userInfo)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void getUserInfoByEmail(GUserEmailRequest request, StreamObserver<GUserInfoResponse> responseObserver) {
        try {
            GObjUserInfo userInfo = ModelConverter.convert(usersService.getUserByEmail(request.getEmail()));

            GUserInfoResponse response = GUserInfoResponse.newBuilder()
                    .setObjUserInfo(userInfo)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void addUser(GAddUserRequest request, StreamObserver<GUserInfoResponse> responseObserver) {
        try {
            GObjUserInfo userInfo = ModelConverter.convert(
                    usersService.addUser(ModelConverter.convert(request)));

            GUserInfoResponse response = GUserInfoResponse.newBuilder()
                    .setObjUserInfo(userInfo)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void updateUser(GUpdateUserRequest request, StreamObserver<GUserInfoResponse> responseObserver) {
        try {
            GObjUserInfo userInfo = ModelConverter.convert(
                    usersService.updateUser(ModelConverter.convert(request)));

            GUserInfoResponse response = GUserInfoResponse.newBuilder()
                    .setObjUserInfo(userInfo)
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
        } else if (e instanceof NotFoundUserException) {
            status = Status.NOT_FOUND;
        } else {
            status = Status.INTERNAL;
        }

        responseObserver.onError(status
                .withDescription(e.getMessage())
                .asRuntimeException());
    }
}
