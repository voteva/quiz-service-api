package com.voteva.tests.service.impl;

import com.voteva.tests.exception.NotFoundTestException;
import com.voteva.tests.model.entity.ObjTestEntity;
import com.voteva.tests.repository.TestsRepository;
import com.voteva.tests.service.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TestsServiceImpl implements TestsService {

    private final TestsRepository testsRepository;

    @Autowired
    public TestsServiceImpl(TestsRepository testsRepository) {
        this.testsRepository = testsRepository;
    }

    @Override
    public ObjTestEntity getTestByUid(UUID testUid) {
        return testsRepository.findByTestUid(testUid)
                .orElseThrow(() -> new NotFoundTestException("Not found test with uid=" + testUid));
    }

    @Override
    public UUID addTest(ObjTestEntity entity) {
        return testsRepository.save(entity).getTestUid();
    }

    @Override
    public void removeTest(UUID testUid) {
        testsRepository.deleteByTestUid(testUid);
    }
}
