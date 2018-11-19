package com.voteva.gateway.service;

import com.voteva.gateway.security.model.User;
import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.QuizInfo;

import java.util.List;

public interface QuizService {

    void assignTest(AssignTestRequest request, User user);

    List<QuizInfo> getTestResults(User user);

    QuizInfo setTestResults(TestResultsRequest request, User user);
}
