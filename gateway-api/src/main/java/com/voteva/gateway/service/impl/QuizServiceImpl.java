package com.voteva.gateway.service.impl;

import com.voteva.gateway.annotation.GatewayService;
import com.voteva.gateway.annotation.Logged;
import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.QuizInfoConverter;
import com.voteva.gateway.converter.UsersInfoConverter;
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
import com.voteva.quiz.grpc.model.v1.GAssignTestRequest;
import com.voteva.quiz.grpc.model.v1.GBlockUserRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersResponse;
import com.voteva.quiz.grpc.model.v1.GGetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GGetUserRequest;
import com.voteva.quiz.grpc.model.v1.GRemoveAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GUnblockUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.voteva.gateway.exception.model.Service.QUIZ;

@GatewayService(serviceName = QUIZ)
public class QuizServiceImpl implements QuizService {

    private final InternalAuthService internalAuthService;
    private final TestsService testsService;
    private final GRpcQuizServiceClient rpcQuizServiceClient;

    @Autowired
    public QuizServiceImpl(
            InternalAuthService internalAuthService,
            TestsService testsService,
            GRpcQuizServiceClient rpcQuizServiceClient) {

        this.internalAuthService = internalAuthService;
        this.testsService = testsService;
        this.rpcQuizServiceClient = rpcQuizServiceClient;
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
        throw new NotImplementedException();
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
}
