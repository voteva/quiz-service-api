package com.voteva.tests.repository;

import com.voteva.tests.model.entity.ObjTestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TestsRepository extends MongoRepository<ObjTestEntity, Integer> {

    Optional<ObjTestEntity> findByTestUid(UUID testUid);

    Optional<ObjTestEntity> deleteByTestUid(UUID testUid);
}
