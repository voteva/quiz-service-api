package com.voteva.gateway.web.controller;

import com.voteva.gateway.service.TestsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/tests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestsController {

    private static final Logger logger = LoggerFactory.getLogger(TestsController.class);

    private final TestsService testsService;

    @Autowired
    public TestsController(TestsService testsService) {
        this.testsService = testsService;
    }
}
