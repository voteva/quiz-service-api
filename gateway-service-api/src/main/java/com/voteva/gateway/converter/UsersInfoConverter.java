package com.voteva.gateway.converter;

import com.voteva.gateway.web.to.common.UserInfo;
import com.voteva.users.grpc.model.v1.GObjUserInfo;

import java.util.UUID;

public class UsersInfoConverter {

    public static UserInfo convert(GObjUserInfo objUserInfo) {
        return new UserInfo()
                .setUserUid(UUID.fromString(objUserInfo.getUuid()))
                .setEmail(objUserInfo.getEmail())
                .setFullName(objUserInfo.getFullName())
                .setCreatedDatetime(objUserInfo.getCreatedDtime());
    }
}
