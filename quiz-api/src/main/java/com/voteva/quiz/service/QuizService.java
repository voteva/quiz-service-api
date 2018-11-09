package com.voteva.quiz.service;

import com.voteva.quiz.model.entity.ObjUserEntity;
import com.voteva.quiz.model.entity.RelUser2TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface QuizService {

    List<RelUser2TestEntity> getUserTests(UUID userUid);

    void assignTest(UUID userUid, UUID testUid, int attemptsAllowed);

    RelUser2TestEntity setTestResults(UUID userUid, UUID testUid, int percent);

    Page<ObjUserEntity> getAllUsers(Pageable pageable);

    ObjUserEntity getUser(UUID userUid);

    ObjUserEntity addUser(ObjUserEntity entity);

    void updateUser(ObjUserEntity entity);

    void setAdmin(UUID userUid);

    void resetAdmin(UUID userUid);

    void blockUser(UUID userUid);

    void unblockUser(UUID userUid);
}
