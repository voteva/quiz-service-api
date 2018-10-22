package com.voteva.gateway.converter;

import com.voteva.gateway.web.to.common.UserInfo;
import com.voteva.users.grpc.model.v1.ObjUserInfo;

import java.util.UUID;

public class UsersInfoConverter {

    public static UserInfo convert(ObjUserInfo objUserInfo) {
        return new UserInfo(
                UUID.fromString(objUserInfo.getUuid()),
                objUserInfo.getEmail(),
                "full_name",
                123456,
                objUserInfo.getIsBlocked(),
                objUserInfo.getIsAdmin()
        );
    }

    public static ObjUserInfo convert(UserInfo userInfo) {
        return ObjUserInfo.newBuilder()
                .build();
    }
}
