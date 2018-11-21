package com.voteva.tests.repository;

import com.voteva.tests.model.entity.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TestsRepository extends MongoRepository<TestEntity, Integer> {

    Optional<TestEntity> findByTestUid(UUID testUid);

    Page<TestEntity> findByTestCategoryIgnoreCase(String testCategory, Pageable pageable);

    void deleteByTestUid(UUID testUid);
}
