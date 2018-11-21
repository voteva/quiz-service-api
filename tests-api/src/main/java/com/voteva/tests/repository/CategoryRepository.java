package com.voteva.tests.repository;

import com.voteva.tests.model.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findByCategoryNameIgnoreCase(String categoryName);
}
