package com.voteva.gateway.service.impl;

import com.voteva.auth.grpc.model.v1.GAddPrincipalRequest;
import com.voteva.auth.grpc.model.v1.GDeletePrincipalRequest;
import com.voteva.auth.grpc.model.v1.GPrincipalKey;
import com.voteva.gateway.annotation.GatewayService;
import com.voteva.gateway.annotation.Logged;
import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.QuizInfoConverter;
import com.voteva.gateway.converter.UsersInfoConverter;
import com.voteva.gateway.grpc.client.GRpcPrincipalServiceClient;
import com.voteva.gateway.grpc.client.GRpcQuizServiceClient;
import com.voteva.gateway.security.InternalAuthService;
import com.voteva.gateway.security.model.Principal;
import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.service.TestsService;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.AddUserInfo;
import com.voteva.gateway.web.to.out.QuizInfo;
import com.voteva.gateway.web.to.out.TestInfo;
import com.voteva.gateway.web.to.out.UserFullInfo;
import com.voteva.quiz.grpc.model.v1.GAddUserRequest;
import com.voteva.quiz.grpc.model.v1.GAddUserResponse;
import com.voteva.quiz.grpc.model.v1.GAssignTestRequest;
import com.voteva.quiz.grpc.model.v1.GBlockUserRequest;
import com.voteva.quiz.grpc.model.v1.GDeleteResultsRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersResponse;
import com.voteva.quiz.grpc.model.v1.GGetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GGetUserRequest;
import com.voteva.quiz.grpc.model.v1.GRemoveAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GUnblockUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.voteva.gateway.exception.model.Service.QUIZ;

@GatewayService(serviceName = QUIZ)
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    private final InternalAuthService internalAuthService;
    private final TestsService testsService;
    private final GRpcQuizServiceClient rpcQuizServiceClient;
    private final GRpcPrincipalServiceClient rpcPrincipalServiceClient;

    @Autowired
    public QuizServiceImpl(
            InternalAuthService internalAuthService,
            TestsService testsService,
            GRpcQuizServiceClient rpcQuizServiceClient,
            GRpcPrincipalServiceClient rpcPrincipalServiceClient) {

        this.internalAuthService = internalAuthService;
        this.testsService = testsService;
        this.rpcQuizServiceClient = rpcQuizServiceClient;
        this.rpcPrincipalServiceClient = rpcPrincipalServiceClient;
    }

    @Logged
    @Override
    public void assignTest(AssignTestRequest request, Principal principal) {
        rpcQuizServiceClient.assignTest(
                GAssignTestRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setUserUid(CommonConverter.convert(principal.getExtId()))
                        .setTestUid(CommonConverter.convert(request.getTestUid()))
                        .build());
    }

    @Logged
    @Override
    public List<QuizInfo> getTestResults(Principal principal) {
        return rpcQuizServiceClient.getTestResults(
                GGetTestResultsRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setUserUid(CommonConverter.convert(principal.getExtId()))
                        .build())
                .getTestResultsList().stream()
                .map(QuizInfoConverter::convert)
                .map(this::enrichQuizInfo)
                .collect(Collectors.toList());
    }

    @Logged
    @Override
    public QuizInfo setTestResults(TestResultsRequest request, Principal principal) {
        TestInfo testInfo = testsService.getTestInfo(request.getTestUid());

        int rightAnswersCount = 0;

        for (int qIndex = 0; qIndex < testInfo.getQuestions().size(); qIndex++) {
            if (request.getAnswers().get(String.valueOf(qIndex)) ==
                    testInfo.getQuestions().get(qIndex).getRightAnswer()) rightAnswersCount++;
        }

        double result = (double) rightAnswersCount / testInfo.getQuestions().size();
        int percentCompleted = (int) (result * 100);

        return setTestResultsInternal(
                principal.getExtId(),
                request.getTestUid(),
                percentCompleted);
    }

    @Logged
    @Override
    public void deleteTestResults(UUID testUid) {
        rpcQuizServiceClient.deleteResultsForTest(
                GDeleteResultsRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setTestUid(CommonConverter.convert(testUid))
                        .build());
    }

    @Logged
    @Override
    public PagedResult<UserFullInfo> getUsers(int page, int size) {
        GGetAllUsersResponse users = rpcQuizServiceClient.getAllUsers(
                GGetAllUsersRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return UsersInfoConverter.convert(users.getUsersList(), users.getPage());
    }

    @Logged
    @Override
    public UserFullInfo getUserByUid(UUID userUid) {
        return UsersInfoConverter.convert(
                rpcQuizServiceClient.getUser(
                        GGetUserRequest.newBuilder()
                                .setAuthentication(internalAuthService.getGAuthentication())
                                .setUserUid(CommonConverter.convert(userUid))
                                .build())
                        .getUserInfo());
    }

    @Logged
    @Override
    public AddUserInfo addUser(AddUserRequest request) {
        GPrincipalKey principal = rpcPrincipalServiceClient.addPrincipal(
                GAddPrincipalRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setPrincipalExtId(UUID.randomUUID().toString())
                        .build())
                .getPrincipalKey();

        try {
            GAddUserResponse user = rpcQuizServiceClient.addUser(
                    GAddUserRequest.newBuilder()
                            .setAuthentication(internalAuthService.getGAuthentication())
                            .setUserUid(CommonConverter.convert(UUID.fromString(principal.getExtId())))
                            .setFirstName(request.getFirstName())
                            .setLastName(request.getLastName())
                            .build());

            return UsersInfoConverter.convert(user);

        } catch (Exception e) {
            logger.warn("Failed to add user: {}" + principal.getExtId());

            rpcPrincipalServiceClient.deletePrincipal(
                    GDeletePrincipalRequest.newBuilder()
                            .setAuthentication(internalAuthService.getGAuthentication())
                            .setPrincipalExtId(principal.getExtId())
                            .build()
            );

            throw e;
        }
    }

    @Logged
    @Override
    public void setAdminGrants(UUID userUid) {
        rpcQuizServiceClient.setAdminGrants(
                GSetAdminGrantsRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setUserUid(CommonConverter.convert(userUid))
                        .build());
    }

    @Logged
    @Override
    public void removeAdminGrants(UUID userUid) {
        rpcQuizServiceClient.removeAdminGrants(
                GRemoveAdminGrantsRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setUserUid(CommonConverter.convert(userUid))
                        .build());
    }

    @Logged
    @Override
    public void blockUser(UUID userUid) {
        rpcQuizServiceClient.blockUser(
                GBlockUserRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setUserUid(CommonConverter.convert(userUid))
                        .build());
    }

    @Logged
    @Override
    public void unblockUser(UUID userUid) {
        rpcQuizServiceClient.unblockUser(
                GUnblockUserRequest.newBuilder()
                        .setAuthentication(internalAuthService.getGAuthentication())
                        .setUserUid(CommonConverter.convert(userUid))
                        .build());
    }

    private QuizInfo setTestResultsInternal(UUID userUid, UUID testUid, int percent) {
        return QuizInfoConverter.convert(
                rpcQuizServiceClient.setTestResults(
                        GSetTestResultsRequest.newBuilder()
                                .setAuthentication(internalAuthService.getGAuthentication())
                                .setUserUid(CommonConverter.convert(userUid))
                                .setTestUid(CommonConverter.convert(testUid))
                                .setPercent(percent)
                                .build())
                        .getTestResultInfo());
    }

    private QuizInfo enrichQuizInfo(QuizInfo quizInfo) {
        try {
            TestInfo testInfo = testsService.getTestInfo(quizInfo.getTestUid());
            quizInfo.setTestName(testInfo.getTestName())
                    .setTestCategory(testInfo.getTestCategory());
        } catch (Exception e) {
            logger.warn("Failed to get tests info: {}", quizInfo.getTestUid(), e);
        }

        return quizInfo;
    }
}
