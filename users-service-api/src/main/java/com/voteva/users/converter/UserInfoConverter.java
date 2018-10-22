package com.voteva.users.converter;

import com.voteva.users.grpc.model.v1.ObjUserInfo;
import com.voteva.users.model.entity.ObjUserEntity;

public class UserInfoConverter {

    public static ObjUserEntity convert(ObjUserInfo objUserInfo) {
        ObjUserEntity objUserEntity = new ObjUserEntity()
                .setUserEmail(objUserInfo.getEmail())
                .setBlocked(objUserInfo.getIsBlocked())
                .setAdmin(objUserInfo.getIsAdmin());

        return objUserEntity;
    }

    public static ObjUserInfo convert(ObjUserEntity objUserEntity) {
        return ObjUserInfo.newBuilder()
                .setEmail(objUserEntity.getUserEmail())
                .setUuid(String.valueOf(objUserEntity.getUserUid()))
                .setIsBlocked(objUserEntity.isBlocked())
                .setIsAdmin(objUserEntity.isAdmin())
                .build();
    }
}
