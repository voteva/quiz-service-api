package com.voteva.gateway.service;

import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.common.TestInfo;
import com.voteva.gateway.web.to.in.AddTestRequest;

import java.util.List;
import java.util.UUID;

public interface TestsService {

    List<String> getTestCategories();

    PagedResult<TestInfo> getTests(String category, int page, int size);

    TestInfo getTestInfo(UUID testUid);

    UUID addTest(AddTestRequest request);

    void deleteTest(UUID testUid);
}
