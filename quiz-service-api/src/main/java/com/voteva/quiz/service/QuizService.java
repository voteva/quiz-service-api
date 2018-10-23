package com.voteva.quiz.service;

import com.voteva.quiz.model.entity.ObjUserEntity;
import com.voteva.quiz.model.entity.RelUser2TestEntity;

import java.util.UUID;

public interface QuizService {

    ObjUserEntity addUser(ObjUserEntity entity);

    RelUser2TestEntity assignTest(UUID userUid, UUID testUid, int attemptsAllowed);

    RelUser2TestEntity setTestResults(UUID userUid, UUID testUid, int percent);

    void setAdmin(UUID userUid);

    void resetAdmin(UUID userUid);

    void blockUser(UUID userUid);

    void unblockUser(UUID userUid);
}
