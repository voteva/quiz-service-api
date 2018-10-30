package com.voteva.gateway.service;

import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.QuizInfo;

import java.util.UUID;

public interface QuizService {

    void assignTest(UUID userUid, UUID testUid, int attemptsAllowed);

    QuizInfo setTestResults(TestResultsRequest testResultsRequest);
}
