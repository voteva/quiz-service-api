package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.UsersInfoConverter;
import com.voteva.gateway.annotation.GatewayService;
import com.voteva.gateway.grpc.client.GRpcQuizServiceClient;
import com.voteva.gateway.grpc.client.GRpcUsersServiceClient;
import com.voteva.gateway.service.CommonAuthService;
import com.voteva.gateway.annotation.Logged;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.out.AddUserInfo;
import com.voteva.gateway.web.to.out.UserInfo;
import com.voteva.quiz.grpc.model.v1.GBlockUserRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersResponse;
import com.voteva.quiz.grpc.model.v1.GGetUserRequest;
import com.voteva.quiz.grpc.model.v1.GRemoveAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GUnblockUserRequest;
import com.voteva.users.grpc.model.v1.GAddUserAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.voteva.gateway.exception.model.Service.COMMON_AUTH;

@GatewayService(serviceName = COMMON_AUTH)
public class CommonAuthServiceImpl implements CommonAuthService {

    private final GRpcUsersServiceClient rpcUsersServiceClient;
    private final GRpcQuizServiceClient rpcQuizServiceClient;

    @Autowired
    public CommonAuthServiceImpl(
            GRpcUsersServiceClient rpcUsersServiceClient,
            GRpcQuizServiceClient rpcQuizServiceClient) {
        this.rpcUsersServiceClient = rpcUsersServiceClient;
        this.rpcQuizServiceClient = rpcQuizServiceClient;
    }

    @Logged
    @Override
    public PagedResult<UserInfo> getUsers(int page, int size) {
        GGetAllUsersResponse users = rpcQuizServiceClient.getAllUsers(
                GGetAllUsersRequest.newBuilder()
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return UsersInfoConverter.convert(users.getUsersList(), users.getPage());
    }

    @Logged
    @Override
    public UserInfo getUserByUid(UUID userUid) {
        return UsersInfoConverter.convert(
                rpcQuizServiceClient.getUser(
                        GGetUserRequest.newBuilder()
                                .setUserUid(CommonConverter.convert(userUid))
                                .build())
                        .getUserInfo());
    }

    @Logged
    @Override
    public AddUserInfo addUser(AddUserRequest request) {
        UUID userUid = CommonConverter.convert(
                rpcUsersServiceClient.addUser(
                        GAddUserAuthRequest.newBuilder()
                                .setEmail(request.getEmail())
                                .setPassword(request.getPassword())
                                .build())
                        .getUserUid());

        return new AddUserInfo(
                userUid,
                rpcQuizServiceClient.addUser(
                        UsersInfoConverter.convert(request, userUid))
                        .getUserInfo().getCreatedDtime());
    }

    @Logged
    @Override
    public void setAdminGrants(UUID userUid) {
        rpcQuizServiceClient.setAdminGrants(
                GSetAdminGrantsRequest.newBuilder()
                        .setUserUid(CommonConverter.convert(userUid))
                        .build());
    }

    @Logged
    @Override
    public void removeAdminGrants(UUID userUid) {
        rpcQuizServiceClient.removeAdminGrants(
                GRemoveAdminGrantsRequest.newBuilder()
                        .setUserUid(CommonConverter.convert(userUid))
                        .build());
    }

    @Logged
    @Override
    public void blockUser(UUID userUid) {
        rpcQuizServiceClient.blockUser(
                GBlockUserRequest.newBuilder()
                        .setUserUid(CommonConverter.convert(userUid))
                        .build());
    }

    @Logged
    @Override
    public void unblockUser(UUID userUid) {
        rpcQuizServiceClient.unblockUser(
                GUnblockUserRequest.newBuilder()
                        .setUserUid(CommonConverter.convert(userUid))
                        .build());
    }
}
