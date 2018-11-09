package com.voteva.tests.service;

import com.voteva.tests.model.entity.ObjTestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TestsService {

    List<String> getCategories();

    Page<ObjTestEntity> getAllTests(Pageable pageable);

    Page<ObjTestEntity> getTestsByCategory(String category , Pageable pageable);

    ObjTestEntity getTest(UUID testUid);

    UUID addTest(ObjTestEntity entity);

    void removeTest(UUID testUid);
}
