package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.TestInfoConverter;
import com.voteva.gateway.grpc.client.GRpcTestsServiceClient;
import com.voteva.gateway.service.TestsService;
import com.voteva.gateway.util.GRpcExceptionUtils;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.out.TestInfo;
import com.voteva.gateway.web.to.in.AddTestRequest;
import com.voteva.tests.grpc.model.v1.GGetAllTestsRequest;
import com.voteva.tests.grpc.model.v1.GGetAllTestsResponse;
import com.voteva.tests.grpc.model.v1.GGetTestCategoriesRequest;
import com.voteva.tests.grpc.model.v1.GGetTestRequest;
import com.voteva.tests.grpc.model.v1.GGetTestsByCategoryRequest;
import com.voteva.tests.grpc.model.v1.GGetTestsByCategoryResponse;
import com.voteva.tests.grpc.model.v1.GRemoveTestRequest;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestsServiceImpl implements TestsService {

    private static final Logger logger = LoggerFactory.getLogger(TestsServiceImpl.class);

    private final GRpcTestsServiceClient rpcTestsServiceClient;

    @Autowired
    public TestsServiceImpl(GRpcTestsServiceClient rpcTestsServiceClient) {
        this.rpcTestsServiceClient = rpcTestsServiceClient;
    }

    @Override
    public List<String> getTestCategories() {
        try {
            return rpcTestsServiceClient.getTestCategories(
                    GGetTestCategoriesRequest.newBuilder().build())
                    .getCategoriesList();

        } catch (StatusRuntimeException e) {
            logger.error("Failed to get test categories");

            throw GRpcExceptionUtils.convert(e);
        }
    }

    @Override
    public PagedResult<TestInfo> getTests(String category, int page, int size) {
        try {
            return Optional.ofNullable(category)
                    .map(c -> getTestsByCategoryInternal(c, page, size))
                    .orElseGet(() -> getTestsInternal(page, size));

        } catch (StatusRuntimeException e) {
            logger.error("Failed to get all tests by category={} for page={} and page size={}",
                    category, page, size);

            throw GRpcExceptionUtils.convert(e);
        }
    }

    @Override
    public TestInfo getTestInfo(UUID testUid) {
        try {
            return TestInfoConverter.convert(
                    rpcTestsServiceClient.getTest(
                            GGetTestRequest.newBuilder()
                                    .setTestUid(CommonConverter.convert(testUid))
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
            return CommonConverter.convert(
                    rpcTestsServiceClient.addTest(
                            TestInfoConverter.convert(request))
                            .getTestUid());

        } catch (StatusRuntimeException e) {
            logger.error("Failed to add test with name={}", request.getTestName());

            throw GRpcExceptionUtils.convert(e);
        }
    }

    @Override
    public void deleteTest(UUID testUid) {
        try {
            rpcTestsServiceClient.removeTest(
                    GRemoveTestRequest.newBuilder()
                            .setTestUid(CommonConverter.convert(testUid))
                            .build());

        } catch (StatusRuntimeException e) {
            logger.error("Failed to delete test with uid={}", testUid);

            throw GRpcExceptionUtils.convert(e);
        }
    }

    private PagedResult<TestInfo> getTestsByCategoryInternal(String category, int page, int size) {
        GGetTestsByCategoryResponse tests = rpcTestsServiceClient.getTestsByCategory(
                GGetTestsByCategoryRequest.newBuilder()
                        .setCategory(category)
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return TestInfoConverter.convert(tests.getTestsList(), tests.getPage());
    }

    private PagedResult<TestInfo> getTestsInternal(int page, int size) {
        GGetAllTestsResponse tests = rpcTestsServiceClient.getAllTests(
                GGetAllTestsRequest.newBuilder()
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return TestInfoConverter.convert(tests.getTestsList(), tests.getPage());
    }
}
