package com.voteva.gateway.service;

import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.QuizInfo;

public interface QuizService {

    void assignTest(AssignTestRequest request);

    QuizInfo setTestResults(TestResultsRequest request);
}
