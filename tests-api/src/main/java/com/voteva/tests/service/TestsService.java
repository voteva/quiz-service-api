package com.voteva.tests.service;

import com.voteva.tests.model.entity.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TestsService {

    List<String> getCategories();

    Page<TestEntity> getAllTests(Pageable pageable);

    Page<TestEntity> getTestsByCategory(String category, Pageable pageable);

    TestEntity getTest(UUID testUid);

    UUID addTest(TestEntity entity);

    void removeTest(UUID testUid);
}
