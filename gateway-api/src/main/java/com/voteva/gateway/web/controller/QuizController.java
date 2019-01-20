package com.voteva.gateway.web.controller;

import com.voteva.gateway.security.model.Principal;
import com.voteva.gateway.security.SecurityContextUtil;
import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.QuizInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/quiz", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(path = "/assign", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> assignTest(@RequestBody @Valid AssignTestRequest request) {
        Principal principal = SecurityContextUtil.getPrincipal();

        logger.debug("Assigning test: {} for user: {}", request.getTestUid(), principal.getExtId());

        quizService.assignTest(request, principal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/results")
    public ResponseEntity<List<QuizInfo>> getTestResults() {
        Principal principal = SecurityContextUtil.getPrincipal();

        logger.debug("Getting tests results for user with uid: {}", principal.getExtId());

        return ResponseEntity.ok(quizService.getTestResults(principal));
    }

    @PostMapping(path = "/results", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuizInfo> setTestResults(@RequestBody @Valid TestResultsRequest request) {
        Principal principal = SecurityContextUtil.getPrincipal();

        logger.debug("Setting results of test: {} for user: {}", request.getTestUid(), principal.getExtId());

        return ResponseEntity.ok(quizService.setTestResults(request, principal));
    }
}
