package com.voteva.gateway.web.controller;

import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.QuizInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/quiz", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @RequestMapping(path = "/assign", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> assignTest(@RequestBody @Valid AssignTestRequest assignTestRequest) {
        logger.debug("Assigning test={} to user={}", assignTestRequest.getTestUid(), assignTestRequest.getUserUid());

        quizService.assignTest(
                assignTestRequest.getUserUid(),
                assignTestRequest.getTestUid(),
                assignTestRequest.getAttemptsAllowed());

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/results", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuizInfo> setTestResults(@RequestBody @Valid TestResultsRequest testResultsRequest) {
        logger.debug("Setting results of test={} for user={}", testResultsRequest.getTestUid(), testResultsRequest.getUserUid());

        return ResponseEntity.ok(quizService.setTestResults(testResultsRequest));
    }
}
