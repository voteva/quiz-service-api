package com.voteva.gateway.service.impl;

import com.voteva.gateway.converter.CommonConverter;
import com.voteva.gateway.converter.QuizInfoConverter;
import com.voteva.gateway.exception.util.GRpcExceptionUtil;
import com.voteva.gateway.grpc.client.GRpcQuizServiceClient;
import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.service.TestsService;
import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.QuizInfo;
import com.voteva.gateway.web.to.out.TestInfo;
import com.voteva.quiz.grpc.model.v1.GAssignTestRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsRequest;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    private final TestsService testsService;
    private final GRpcQuizServiceClient rpcQuizServiceClient;

    @Autowired
    public QuizServiceImpl(TestsService testsService,
                           GRpcQuizServiceClient rpcQuizServiceClient) {
        this.testsService = testsService;
        this.rpcQuizServiceClient = rpcQuizServiceClient;
    }

    @Override
    public void assignTest(AssignTestRequest request) {
        try {
            rpcQuizServiceClient.assignTest(
                    GAssignTestRequest.newBuilder()
                            .setUserUid(CommonConverter.convert(request.getUserUid()))
                            .setTestUid(CommonConverter.convert(request.getTestUid()))
                            .setAttemptsAllowed(request.getAttemptsAllowed())
                            .build());

        } catch (StatusRuntimeException e) {
            logger.warn("Failed to assign test for request: {}", request);
            throw GRpcExceptionUtil.convertByQuiz(e);
        }
    }

    @Override
    public QuizInfo setTestResults(TestResultsRequest request) {
        TestInfo testInfo = testsService.getTestInfo(request.getTestUid());

        int rightAnswersCount = 0;

        for (int qIndex = 0; qIndex < testInfo.getQuestions().size(); qIndex++) {
            if (request.getAnswers().get(String.valueOf(qIndex)) ==
                    testInfo.getQuestions().get(qIndex).getRightAnswer()) rightAnswersCount++;
        }

        int percentCompleted = (rightAnswersCount / testInfo.getQuestions().size()) * 100;

        return setTestResultsInternal(
                request.getUserUid(),
                request.getTestUid(),
                percentCompleted);
    }

    private QuizInfo setTestResultsInternal(UUID userUid, UUID testUid, int percent) {
        try {
            return QuizInfoConverter.convert(
                    rpcQuizServiceClient.setTestResults(
                            GSetTestResultsRequest.newBuilder()
                                    .setUserUid(CommonConverter.convert(userUid))
                                    .setTestUid(CommonConverter.convert(testUid))
                                    .setPercent(percent)
                                    .build())
                            .getTestResultsInfo());

        } catch (StatusRuntimeException e) {
            logger.warn("Failed set results of test: {} for user: {}", testUid, userUid);
            throw GRpcExceptionUtil.convertByQuiz(e);
        }
    }
}
