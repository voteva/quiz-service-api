package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.TestInfoConverter;
import com.voteva.gateway.exception.util.GatewayService;
import com.voteva.gateway.grpc.client.GRpcQuizServiceClient;
import com.voteva.gateway.grpc.client.GRpcTestsServiceClient;
import com.voteva.gateway.service.TestsService;
import com.voteva.gateway.util.Logged;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddTestRequest;
import com.voteva.gateway.web.to.out.TestInfo;
import com.voteva.quiz.grpc.model.v1.GDeleteResultsRequest;
import com.voteva.tests.grpc.model.v1.GGetAllTestsRequest;
import com.voteva.tests.grpc.model.v1.GGetAllTestsResponse;
import com.voteva.tests.grpc.model.v1.GGetTestCategoriesRequest;
import com.voteva.tests.grpc.model.v1.GGetTestRequest;
import com.voteva.tests.grpc.model.v1.GGetTestsByCategoryRequest;
import com.voteva.tests.grpc.model.v1.GGetTestsByCategoryResponse;
import com.voteva.tests.grpc.model.v1.GRemoveTestRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.voteva.gateway.exception.model.Service.TESTS;

@GatewayService(serviceName = TESTS)
public class TestsServiceImpl implements TestsService {

    private final GRpcTestsServiceClient rpcTestsServiceClient;
    private final GRpcQuizServiceClient rpcQuizServiceClient;

    @Autowired
    public TestsServiceImpl(
            GRpcTestsServiceClient rpcTestsServiceClient,
            GRpcQuizServiceClient rpcQuizServiceClient) {
        this.rpcTestsServiceClient = rpcTestsServiceClient;
        this.rpcQuizServiceClient = rpcQuizServiceClient;
    }

    @Logged
    @Override
    public List<String> getTestCategories() {
        return rpcTestsServiceClient.getTestCategories(
                GGetTestCategoriesRequest.newBuilder().build())
                .getCategoriesList();
    }

    @Logged
    @Override
    public PagedResult<TestInfo> getTests(String category, int page, int size) {
        return Optional.ofNullable(category)
                .map(c -> getTestsByCategoryInternal(c, page, size))
                .orElseGet(() -> getTestsInternal(page, size));
    }

    @Logged
    @Override
    public TestInfo getTestInfo(UUID testUid) {
        return TestInfoConverter.convert(
                rpcTestsServiceClient.getTest(
                        GGetTestRequest.newBuilder()
                                .setTestUid(CommonConverter.convert(testUid))
                                .build())
                        .getTestInfo());
    }

    @Logged
    @Override
    public UUID addTest(AddTestRequest request) {
        return CommonConverter.convert(
                rpcTestsServiceClient.addTest(
                        TestInfoConverter.convert(request))
                        .getTestUid());
    }

    @Logged
    @Override
    public void deleteTest(UUID testUid) {
        // TODO
        rpcQuizServiceClient.deleteResultsForTest(
                GDeleteResultsRequest.newBuilder()
                        .setTestUid(CommonConverter.convert(testUid))
                        .build());

        rpcTestsServiceClient.removeTest(
                GRemoveTestRequest.newBuilder()
                        .setTestUid(CommonConverter.convert(testUid))
                        .build());
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
