package com.voteva.tests.repository;

import com.voteva.tests.model.entity.ObjCategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<ObjCategoryEntity, Integer> {

    Optional<ObjCategoryEntity> findByCategoryName(String categoryName);
}
