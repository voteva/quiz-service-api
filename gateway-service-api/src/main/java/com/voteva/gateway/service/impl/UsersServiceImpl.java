package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.UsersInfoConverter;
import com.voteva.gateway.grpc.client.GRpcUsersServiceClient;
import com.voteva.gateway.service.UsersService;
import com.voteva.gateway.util.GRpcExceptionUtils;
import com.voteva.gateway.web.to.common.UserInfo;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.UpdateUserRequest;
import com.voteva.users.grpc.model.v1.GAddUserRequest;
import com.voteva.users.grpc.model.v1.GUpdateUserRequest;
import com.voteva.users.grpc.model.v1.GUserEmailRequest;
import com.voteva.users.grpc.model.v1.GUserUidRequest;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final GRpcUsersServiceClient grpcUsersServiceClient;

    @Autowired
    public UsersServiceImpl(GRpcUsersServiceClient gRpcUsersServiceClient) {
        this.grpcUsersServiceClient = gRpcUsersServiceClient;
    }

    @Override
    public UserInfo getUserByUid(UUID userUid) {
        try {
            return UsersInfoConverter.convert(
                    grpcUsersServiceClient.getUserInfoByUid(
                            GUserUidRequest.newBuilder()
                                    .setUuid(String.valueOf(userUid))
                                    .build())
                            .getObjUserInfo());
        } catch (StatusRuntimeException e) {
            logger.error("Failed to get user info by uid={}", userUid);
            throw GRpcExceptionUtils.convert(e);
        }
    }

    @Override
    public UserInfo getUserByEmail(String email) {
        try {
            return UsersInfoConverter.convert(
                    grpcUsersServiceClient.getUserInfoByEmail(
                            GUserEmailRequest.newBuilder()
                                    .setEmail(email)
                                    .build())
                            .getObjUserInfo());
        } catch (StatusRuntimeException e) {
            logger.error("Failed to get user info by email={}", email);
            throw GRpcExceptionUtils.convert(e);
        }
    }

    @Override
    public List<UserInfo> getUsers(SpringDataWebProperties.Pageable pageable) {
        return null;
    }

    @Override
    public UserInfo addUser(AddUserRequest addUserRequest) {
        return UsersInfoConverter.convert(
                grpcUsersServiceClient.addUser(
                        GAddUserRequest.newBuilder()
                                .setEmail(addUserRequest.getEmail())
                                .setFullName(addUserRequest.getFullName())
                                .build())
                        .getObjUserInfo());
    }

    @Override
    public UserInfo updateUser(UpdateUserRequest updateUserRequest) {
        return UsersInfoConverter.convert(
                grpcUsersServiceClient.updateUser(
                        GUpdateUserRequest.newBuilder()
                                .setUuid(String.valueOf(updateUserRequest.getUserUid()))
                                .setEmail(updateUserRequest.getEmail())
                                .setFullName(updateUserRequest.getFullName())
                                .build())
                        .getObjUserInfo());
    }

    @Override
    public void blockUser(UUID userUid) {
        /*grpcUsersServiceClient.blockUser(
                GUserUidRequest.newBuilder()
                        .setUuid(String.valueOf(userUid))
                        .build());*/
    }

    @Override
    public void unblockUser(UUID userUid) {
        /*grpcUsersServiceClient.unblockUser(
                GUserUidRequest.newBuilder()
                        .setUuid(String.valueOf(userUid))
                        .build());*/
    }
}
