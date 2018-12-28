package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.QuizInfoConverter;
import com.voteva.gateway.exception.util.GatewayService;
import com.voteva.gateway.grpc.client.GRpcQuizServiceClient;
import com.voteva.gateway.security.model.User;
import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.service.TestsService;
import com.voteva.gateway.util.Logged;
import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.QuizInfo;
import com.voteva.gateway.web.to.out.TestInfo;
import com.voteva.quiz.grpc.model.v1.GAssignTestRequest;
import com.voteva.quiz.grpc.model.v1.GGetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.voteva.gateway.exception.model.Service.QUIZ;

@GatewayService(serviceName = QUIZ)
public class QuizServiceImpl implements QuizService {

    private final TestsService testsService;
    private final GRpcQuizServiceClient rpcQuizServiceClient;

    @Autowired
    public QuizServiceImpl(
            TestsService testsService,
            GRpcQuizServiceClient rpcQuizServiceClient) {
        this.testsService = testsService;
        this.rpcQuizServiceClient = rpcQuizServiceClient;
    }

    @Logged
    @Override
    public void assignTest(AssignTestRequest request, User user) {
        rpcQuizServiceClient.assignTest(
                GAssignTestRequest.newBuilder()
                        .setUserUid(CommonConverter.convert(user.getUuid()))
                        .setTestUid(CommonConverter.convert(request.getTestUid()))
                        .build());
    }

    @Logged
    @Override
    public List<QuizInfo> getTestResults(User user) {
        return rpcQuizServiceClient.getTestResults(
                GGetTestResultsRequest.newBuilder()
                        .setUserUid(CommonConverter.convert(user.getUuid()))
                        .build())
                .getTestResultsList().stream()
                .map(QuizInfoConverter::convert)
                .collect(Collectors.toList());
    }

    @Logged
    @Override
    public QuizInfo setTestResults(TestResultsRequest request, User user) {
        TestInfo testInfo = testsService.getTestInfo(request.getTestUid());

        int rightAnswersCount = 0;

        for (int qIndex = 0; qIndex < testInfo.getQuestions().size(); qIndex++) {
            if (request.getAnswers().get(String.valueOf(qIndex)) ==
                    testInfo.getQuestions().get(qIndex).getRightAnswer()) rightAnswersCount++;
        }

        double result = (double) rightAnswersCount / testInfo.getQuestions().size();
        int percentCompleted = (int) (result * 100);

        return setTestResultsInternal(
                user.getUuid(),
                request.getTestUid(),
                percentCompleted);
    }

    private QuizInfo setTestResultsInternal(UUID userUid, UUID testUid, int percent) {
        return QuizInfoConverter.convert(
                rpcQuizServiceClient.setTestResults(
                        GSetTestResultsRequest.newBuilder()
                                .setUserUid(CommonConverter.convert(userUid))
                                .setTestUid(CommonConverter.convert(testUid))
                                .setPercent(percent)
                                .build())
                        .getTestResultInfo());
    }
}
