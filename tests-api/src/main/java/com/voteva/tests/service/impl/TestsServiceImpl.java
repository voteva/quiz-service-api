package com.voteva.tests.service.impl;

import com.voteva.tests.exception.NotFoundTestException;
import com.voteva.tests.model.entity.CategoryEntity;
import com.voteva.tests.model.entity.TestEntity;
import com.voteva.tests.repository.CategoryRepository;
import com.voteva.tests.repository.TestsRepository;
import com.voteva.tests.service.TestsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TestsServiceImpl implements TestsService {

    private static final Logger logger = LoggerFactory.getLogger(TestsServiceImpl.class);

    private final TestsRepository testsRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TestsServiceImpl(
            TestsRepository testsRepository,
            CategoryRepository categoryRepository) {
        this.testsRepository = testsRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<String> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryEntity::getCategoryName)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TestEntity> getAllTests(Pageable pageable) {
        return testsRepository.findAll(pageable);
    }

    @Override
    public Page<TestEntity> getTestsByCategory(String category, Pageable pageable) {
        return testsRepository.findByTestCategoryIgnoreCase(category, pageable);
    }

    @Override
    public TestEntity getTest(UUID testUid) {
        return testsRepository.findByTestUid(testUid)
                .orElseThrow(() -> {
                    logger.debug("Not found test with uid={}", testUid);

                    return new NotFoundTestException("Not found test with uid=" + testUid);
                });
    }

    @Override
    @Transactional
    public UUID addTest(TestEntity entity) {
        if (!categoryRepository.findByCategoryNameIgnoreCase(entity.getTestCategory()).isPresent()) {
            logger.debug("Add new category with name={}", entity.getTestCategory());

            categoryRepository.save(new CategoryEntity(entity.getTestCategory()));
        }

        return testsRepository.save(entity).getTestUid();
    }

    @Override
    public void removeTest(UUID testUid) {
        testsRepository.deleteByTestUid(testUid);
    }
}
