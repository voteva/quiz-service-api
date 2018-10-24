package com.voteva.users.converter;

import com.voteva.users.grpc.model.v1.GAddUserRequest;
import com.voteva.users.grpc.model.v1.GObjUserInfo;
import com.voteva.users.grpc.model.v1.GUpdateUserRequest;
import com.voteva.users.model.entity.ObjUserEntity;

import java.util.UUID;

public class ModelConverter {

    public static GObjUserInfo convert(ObjUserEntity objUserEntity) {
        return GObjUserInfo.newBuilder()
                .setEmail(objUserEntity.getUserEmail())
                .setUuid(String.valueOf(objUserEntity.getUserUid()))
                .setFullName(objUserEntity.getFullName())
                .setCreatedDtime(objUserEntity.getUserCreatedDtime().getTime())
                .build();
    }

    public static ObjUserEntity convert(GAddUserRequest addUserRequest) {
        return new ObjUserEntity()
                .setUserEmail(addUserRequest.getEmail())
                .setFullName(addUserRequest.getFullName());
    }

    public static ObjUserEntity convert(GUpdateUserRequest updateUserRequest) {
        return new ObjUserEntity()
                .setUserUid(UUID.fromString(updateUserRequest.getUuid()))
                .setUserEmail(updateUserRequest.getEmail())
                .setFullName(updateUserRequest.getFullName());
    }
}
