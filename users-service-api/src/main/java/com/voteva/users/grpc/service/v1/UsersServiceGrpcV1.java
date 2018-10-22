package com.voteva.users.grpc.service.v1;

import com.voteva.common.grpc.model.Empty;
import com.voteva.users.exception.NotFoundUserException;
import com.voteva.users.grpc.model.v1.ObjUserInfo;
import com.voteva.users.grpc.model.v1.UserEmailRequest;
import com.voteva.users.grpc.model.v1.UserInfoRequest;
import com.voteva.users.grpc.model.v1.UserInfoResponse;
import com.voteva.users.grpc.model.v1.UserUidRequest;
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
    public void getUserInfoByUid(UserUidRequest request, StreamObserver<UserInfoResponse> responseObserver) {
        try {
            ObjUserInfo userInfo = usersService.getUserByUid(UUID.fromString(request.getUuid()));

            UserInfoResponse response = UserInfoResponse.newBuilder()
                    .setObjUserInfo(userInfo)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void getUserInfoByEmail(UserEmailRequest request, StreamObserver<UserInfoResponse> responseObserver) {
        try {
            ObjUserInfo userInfo = usersService.getUserByEmail(request.getEmail());

            UserInfoResponse response = UserInfoResponse.newBuilder()
                    .setObjUserInfo(userInfo)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void addUser(UserInfoRequest request, StreamObserver<UserInfoResponse> responseObserver) {
        try {
            ObjUserInfo userInfo = usersService.addUser(request.getObjUserInfo());

            UserInfoResponse response = UserInfoResponse.newBuilder()
                    .setObjUserInfo(userInfo)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void updateUser(UserInfoRequest request, StreamObserver<UserInfoResponse> responseObserver) {
        try {
            ObjUserInfo userInfo = usersService.updateUser(request.getObjUserInfo());

            UserInfoResponse response = UserInfoResponse.newBuilder()
                    .setObjUserInfo(userInfo)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void removeUser(UserUidRequest request, StreamObserver<Empty> responseObserver) {
        try {
            usersService.removeUser(UUID.fromString(request.getUuid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void blockUser(UserUidRequest request, StreamObserver<Empty> responseObserver) {
        try {
            usersService.blockUser(UUID.fromString(request.getUuid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void unblockUser(UserUidRequest request, StreamObserver<Empty> responseObserver) {
        try {
            usersService.unblockUser(UUID.fromString(request.getUuid()));

            responseObserver.onNext(Empty.newBuilder().build());
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
