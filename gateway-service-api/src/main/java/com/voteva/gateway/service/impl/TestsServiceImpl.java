package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.TestInfoConverter;
import com.voteva.gateway.grpc.client.GRpcTestsServiceClient;
import com.voteva.gateway.service.TestsService;
import com.voteva.gateway.util.GRpcExceptionUtils;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.common.TestInfo;
import com.voteva.gateway.web.to.in.AddTestRequest;
import com.voteva.tests.grpc.model.v1.GGetTestRequest;
import com.voteva.tests.grpc.model.v1.GRemoveTestRequest;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TestsServiceImpl implements TestsService {

    private static final Logger logger = LoggerFactory.getLogger(TestsServiceImpl.class);

    private final GRpcTestsServiceClient grpcTestsServiceClient;

    @Autowired
    public TestsServiceImpl(GRpcTestsServiceClient grpcTestsServiceClient) {
        this.grpcTestsServiceClient = grpcTestsServiceClient;
    }

    @Override
    public PagedResult<TestInfo> getTests(UUID categoryUid, int page, int size) {
        return null;
    }

    @Override
    public TestInfo getTestInfo(UUID testUid) {
        try {
            return TestInfoConverter.convert(
                    grpcTestsServiceClient.getTest(
                            GGetTestRequest.newBuilder()
                                    .setUuid(String.valueOf(testUid))
                                    .build())
                            .getTestInfo());
        } catch (StatusRuntimeException e) {
            logger.error("Failed to get test info by uid={}", testUid);
            throw GRpcExceptionUtils.convert(e);
        }
    }

    @Override
    public UUID addTest(AddTestRequest request) {
        try {
            return UUID.fromString(grpcTestsServiceClient.addTest(
                    TestInfoConverter.convert(request))
                    .getUuid());
        } catch (StatusRuntimeException e) {
            logger.error("Failed to add test with name={}", request.getTestName());
            throw GRpcExceptionUtils.convert(e);
        }
    }

    @Override
    public void deleteTest(UUID testUid) {
        try {
            grpcTestsServiceClient.removeTest(
                    GRemoveTestRequest.newBuilder()
                            .setUuid(String.valueOf(testUid))
                            .build());
        } catch (StatusRuntimeException e) {
            logger.error("Failed to remove test with uid={}", testUid);
            throw GRpcExceptionUtils.convert(e);
        }
    }
}
