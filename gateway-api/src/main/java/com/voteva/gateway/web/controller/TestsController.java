package com.voteva.gateway.web.controller;

import com.voteva.gateway.service.TestsService;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddTestRequest;
import com.voteva.gateway.web.to.out.AddTestInfo;
import com.voteva.gateway.web.to.out.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestsController {

    private static final Logger logger = LoggerFactory.getLogger(TestsController.class);

    private final TestsService testsService;

    @Autowired
    public TestsController(TestsService testsService) {
        this.testsService = testsService;
    }

    @GetMapping
    public ResponseEntity<PagedResult<TestInfo>> getTests(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        logger.debug("Getting all tests for page: {} and page size: {} by category: {}", page, size, category);

        return ResponseEntity.ok(testsService.getTests(category, page, size));
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<TestInfo> getTestInfo(@PathVariable UUID uuid) {
        logger.debug("Getting test info by uid: {}", uuid);

        return ResponseEntity.ok(testsService.getTestInfo(uuid));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddTestInfo> addTest(@RequestBody @Valid AddTestRequest request) {
        logger.debug("Adding test with name: {}", request.getTestName());

        return ResponseEntity.ok(new AddTestInfo(testsService.addTest(request)));
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<Void> deleteTest(@PathVariable UUID uuid) {
        logger.debug("Deleting test with uid: {}", uuid);

        testsService.deleteTest(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/categories")
    public ResponseEntity<List<String>> getCategories() {
        logger.debug("Getting test categories");

        return ResponseEntity.ok(testsService.getTestCategories());
    }
}
