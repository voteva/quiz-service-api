package com.voteva.gateway.service.impl;

import com.voteva.gateway.annotation.GatewayService;
import com.voteva.gateway.annotation.Logged;
import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.TestInfoConverter;
import com.voteva.gateway.grpc.client.GRpcQuizServiceClient;
import com.voteva.gateway.grpc.client.GRpcTestsServiceClient;
import com.voteva.gateway.redis.MessagePublisher;
import com.voteva.gateway.redis.model.Task;
import com.voteva.gateway.redis.model.Topic;
import com.voteva.gateway.security.InternalAuthService;
import com.voteva.gateway.service.TestsService;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddTestRequest;
import com.voteva.gateway.web.to.out.TestInfo;
import com.voteva.quiz.grpc.model.v1.GDeleteResultsRequest;
import com.voteva.tests.grpc.model.v1.GAddTestRequest;
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
import java.util.stream.Collectors;

import static com.voteva.gateway.exception.model.Service.TESTS;

@GatewayService(serviceName = TESTS)
public class TestsServiceImpl implements TestsService {

    private final InternalAuthService internalAuthService;
    private final GRpcTestsServiceClient rpcTestsServiceClient;
    private final GRpcQuizServiceClient rpcQuizServiceClient;
    private final MessagePublisher messagePublisher;

    @Autowired
    public TestsServiceImpl(
            InternalAuthService internalAuthService,
            GRpcTestsServiceClient rpcTestsServiceClient,
            GRpcQuizServiceClient rpcQuizServiceClient,
            MessagePublisher messagePublisher) {

        this.internalAuthService = internalAuthService;
        this.rpcTestsServiceClient = rpcTestsServiceClient;
        this.rpcQuizServiceClient = rpcQuizServiceClient;
        this.messagePublisher = messagePublisher;
    }

    @Logged
    @Override
    public List<String> getTestCategories() {
        return rpcTestsServiceClient.getTestCategories(
                GGetTestCategoriesRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .build())
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
                                .setAuthentication(internalAuthService.getGAuthentication())
                                .setTestUid(CommonConverter.convert(testUid))
                                .build())
                        .getTestInfo());
    }

    @Logged
    @Override
    public UUID addTest(AddTestRequest request) {
        return CommonConverter.convert(
                rpcTestsServiceClient.addTest(
                        GAddTestRequest.newBuilder()
                                .setAuthentication(internalAuthService.getGAuthentication())
                                .setTestName(request.getTestName())
                                .setTestCategory(request.getTestCategory())
                                .addAllQuestions(request.getQuestions().stream()
                                        .map(TestInfoConverter::convert)
                                        .collect(Collectors.toList()))
                                .build())
                        .getTestUid());
    }

    @Logged
    @Override
    public void deleteTest(UUID testUid) {
        rpcTestsServiceClient.removeTest(
                GRemoveTestRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setTestUid(CommonConverter.convert(testUid))
                        .build());

        deleteTestResults(testUid);
    }

    private PagedResult<TestInfo> getTestsByCategoryInternal(String category, int page, int size) {
        GGetTestsByCategoryResponse tests = rpcTestsServiceClient.getTestsByCategory(
                GGetTestsByCategoryRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setCategory(category)
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return TestInfoConverter.convert(tests.getTestsList(), tests.getPage());
    }

    private PagedResult<TestInfo> getTestsInternal(int page, int size) {
        GGetAllTestsResponse tests = rpcTestsServiceClient.getAllTests(
                GGetAllTestsRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return TestInfoConverter.convert(tests.getTestsList(), tests.getPage());
    }

    private void deleteTestResults(UUID testUid) {
        try {
            rpcQuizServiceClient.deleteResultsForTest(
                    GDeleteResultsRequest.newBuilder()
                            .setAuthentication(internalAuthService.getGAuthentication())
                            .setTestUid(CommonConverter.convert(testUid))
                            .build());
        } catch (Exception e) {
            messagePublisher.publish(
                    Topic.TESTS_DELETE.getName(),
                    new Task().setContent(testUid.toString()));
        }
    }
}
