package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.UsersInfoConverter;
import com.voteva.gateway.grpc.UsersServiceClient;
import com.voteva.gateway.service.UsersService;
import com.voteva.gateway.web.to.common.UserInfo;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.UpdateUserRequest;
import com.voteva.users.grpc.model.v1.ObjUserInfo;
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

    private final UsersServiceClient usersServiceClient;

    @Autowired
    public UsersServiceImpl(UsersServiceClient usersServiceClient) {
        this.usersServiceClient = usersServiceClient;
    }

    @Override
    public UserInfo getUserByUuid(UUID userUid) {
        return UsersInfoConverter.convert(usersServiceClient.getUserInfo(userUid));
    }

    @Override
    public UserInfo getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserInfo> getUsers(SpringDataWebProperties.Pageable pageable) {
        return null;
    }

    @Override
    public UserInfo addUser(AddUserRequest addUserRequest) {
        return UsersInfoConverter.convert(usersServiceClient.addUser(addUserRequest));
    }

    @Override
    public UserInfo updateUser(UpdateUserRequest updateUserRequest) {
        return null;
    }

    @Override
    public void blockUser(UUID userUid) {

    }

    @Override
    public void unblockUser(UUID userUid) {

    }
}
