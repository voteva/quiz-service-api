package com.voteva.tests.service;

import com.voteva.tests.model.entity.ObjTestEntity;

import java.util.UUID;

public interface TestsService {

    ObjTestEntity getTestByUid(UUID testUid);

    UUID addTest(ObjTestEntity entity);

    void removeTest(UUID testUid);
}
