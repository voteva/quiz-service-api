package com.voteva.tests.converter;

import com.voteva.tests.grpc.model.v1.GAddTestRequest;
import com.voteva.tests.grpc.model.v1.GTestInfo;
import com.voteva.tests.model.entity.ObjTestEntity;

public class ModelConverter {

    public static GTestInfo convert(ObjTestEntity testEntity) {
        return GTestInfo.newBuilder()
                .setTestUid(String.valueOf(testEntity.getTestUid()))
                .setTestName(testEntity.getTestName())
                .build();
    }

    public static ObjTestEntity convert(GAddTestRequest request) {
        return new ObjTestEntity()
                .setTestName(request.getTestName());
    }
}
