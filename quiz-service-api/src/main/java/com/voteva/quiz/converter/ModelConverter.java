package com.voteva.quiz.converter;

import com.voteva.common.grpc.model.GPage;
import com.voteva.common.grpc.model.GPageable;
import com.voteva.common.grpc.model.GUuid;
import com.voteva.quiz.grpc.model.v1.GUser2TestInfo;
import com.voteva.quiz.grpc.model.v1.GUserInfo;
import com.voteva.quiz.model.entity.ObjUserEntity;
import com.voteva.quiz.model.entity.RelUser2TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class ModelConverter {

    public static GPage convert(Page page) {
        return GPage.newBuilder()
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .build();
    }

    public static Pageable convert(GPageable pageable) {
        return PageRequest.of(pageable.getPage(), pageable.getSize());
    }

    public static GUuid convert(UUID uuid) {
        return GUuid.newBuilder()
                .setUuid(String.valueOf(uuid))
                .build();
    }

    public static UUID convert(GUuid guuid) {
        return UUID.fromString(guuid.getUuid());
    }

    public static GUserInfo convert(ObjUserEntity userEntity) {
        return GUserInfo.newBuilder()
                .setUserUid(convert(userEntity.getUserUid()))
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setIsAdmin(userEntity.isAdmin())
                .setIsBlocked(userEntity.isBlocked())
                .setCreatedDtime(userEntity.getUserCreatedDtime().getTime())
                .build();
    }

    public static ObjUserEntity convert(GUserInfo userInfo) {
        return new ObjUserEntity()
                .setUserUid(convert(userInfo.getUserUid()))
                .setFirstName(userInfo.getFirstName())
                .setLastName(userInfo.getLastName())
                .setAdmin(userInfo.getIsAdmin())
                .setBlocked(userInfo.getIsBlocked());
    }

    public static GUser2TestInfo convert(RelUser2TestEntity entity) {
        return GUser2TestInfo.newBuilder()
                .setUserUid(convert(entity.getUser2TestId().getUserUid()))
                .setTestUid(convert(entity.getUser2TestId().getTestUid()))
                .setPercent(entity.getPercent())
                .setAttemptsAllowed(entity.getAttemptsAllowed())
                .build();
    }
}
