package com.voteva.gateway.web.controller;

import com.voteva.gateway.service.InterviewService;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddInterviewQuestionRequest;
import com.voteva.gateway.web.to.out.AddInterviewQuestionInfo;
import com.voteva.gateway.web.to.out.InterviewQuestionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/interviews", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class InterviewController {

    private static final Logger logger = LoggerFactory.getLogger(InterviewController.class);

    private final InterviewService interviewService;

    @Autowired
    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PagedResult<InterviewQuestionInfo>> getQuestions(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        logger.debug("Getting all questions for page: {} and page size: {} by category: {}", page, size, category);

        return ResponseEntity.ok(interviewService.getQuestions(category, page, size));
    }

    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<InterviewQuestionInfo> getQuestionInfo(@PathVariable UUID uuid) {
        logger.debug("Getting question info by uid: {}", uuid);

        return ResponseEntity.ok(interviewService.getQuestionInfo(uuid));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddInterviewQuestionInfo> addQuestion(@RequestBody @Valid AddInterviewQuestionRequest request) {
        logger.debug("Adding question with name: {}", request.getQuestionName());

        return ResponseEntity.ok(new AddInterviewQuestionInfo(interviewService.addQuestion(request)));
    }

    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID uuid) {
        logger.debug("Deleting question with uid: {}", uuid);

        interviewService.deleteQuestion(uuid);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getCategories() {
        logger.debug("Getting question categories");

        return ResponseEntity.ok(interviewService.getQuestionCategories());
    }
}
