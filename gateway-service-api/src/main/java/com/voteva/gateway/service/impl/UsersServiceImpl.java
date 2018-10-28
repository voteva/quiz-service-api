package com.voteva.gateway.service.impl;

import com.voteva.gateway.grpc.client.GRpcUsersServiceClient;
import com.voteva.gateway.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final GRpcUsersServiceClient grpcUsersServiceClient;

    @Autowired
    public UsersServiceImpl(GRpcUsersServiceClient gRpcUsersServiceClient) {
        this.grpcUsersServiceClient = gRpcUsersServiceClient;
    }

    /*@Override
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
    }*/
}
