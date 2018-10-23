package com.voteva.quiz.converter;

import com.voteva.quiz.grpc.model.v1.GUser2TestResponse;
import com.voteva.quiz.grpc.model.v1.GUserResponse;
import com.voteva.quiz.model.entity.ObjUserEntity;
import com.voteva.quiz.model.entity.RelUser2TestEntity;

public class GRpcConverter {

    public static GUserResponse convert(ObjUserEntity userEntity) {
        return GUserResponse.newBuilder()
                .setUserUid(String.valueOf(userEntity.getUserUid()))
                .setIsAdmin(userEntity.isAdmin())
                .setIsBlocked(userEntity.isBlocked())
                .build();
    }

    public static GUser2TestResponse convert(RelUser2TestEntity user2TestEntity) {
        return GUser2TestResponse.newBuilder()
                .setUserUid(String.valueOf(user2TestEntity.getUser2TestId().getUserUid()))
                .setTestUid(String.valueOf(user2TestEntity.getUser2TestId().getTestUid()))
                .setAttemptsAllowed(user2TestEntity.getAttemptsAllowed())
                .setPercent(user2TestEntity.getPercent())
                .build();
    }
}
