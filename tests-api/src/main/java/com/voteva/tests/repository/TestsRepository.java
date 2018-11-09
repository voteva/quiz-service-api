package com.voteva.tests.repository;

import com.voteva.tests.model.entity.ObjTestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TestsRepository extends MongoRepository<ObjTestEntity, Integer> {

    Optional<ObjTestEntity> findByTestUid(UUID testUid);

    Page<ObjTestEntity> findByTestCategory(String testCategory, Pageable pageable);

    void deleteByTestUid(UUID testUid);
}
