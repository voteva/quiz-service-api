package com.voteva.gateway.converter;

import com.voteva.gateway.web.to.common.TestInfo;
import com.voteva.gateway.web.to.in.AddTestRequest;
import com.voteva.tests.grpc.model.v1.GAddTestRequest;
import com.voteva.tests.grpc.model.v1.GTestInfo;

import java.util.UUID;

public class TestInfoConverter {

    public static TestInfo convert(GTestInfo gTestInfo) {
        return new TestInfo()
                .setTestUid(UUID.fromString(gTestInfo.getTestUid()))
                .setTestName(gTestInfo.getTestName());
    }

    public static GAddTestRequest convert(AddTestRequest request) {
        return GAddTestRequest.newBuilder()
                .setTestName(request.getTestName())
                .build();
    }
}
