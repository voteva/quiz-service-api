package com.voteva.quiz.service.impl;

import com.voteva.quiz.exception.NotFoundUserException;
import com.voteva.quiz.model.entity.ObjUserEntity;
import com.voteva.quiz.model.entity.RelUser2TestEntity;
import com.voteva.quiz.repository.User2TestRepository;
import com.voteva.quiz.repository.UsersRepository;
import com.voteva.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuizServiceImpl implements QuizService {

    private UsersRepository usersRepository;
    private User2TestRepository user2TestRepository;

    @Autowired
    public QuizServiceImpl(UsersRepository usersRepository,
                           User2TestRepository user2TestRepository) {
        this.usersRepository = usersRepository;
        this.user2TestRepository = user2TestRepository;
    }

    @Override
    public ObjUserEntity addUser(ObjUserEntity entity) {
        return usersRepository.save(entity);
    }

    @Override
    public RelUser2TestEntity assignTest(UUID userUid, UUID testUid, int attemptsAllowed) {
        checkUserExists(userUid);

        user2TestRepository.findById(new RelUser2TestEntity.User2TestId(userUid, testUid))
                .ifPresent(e -> {
                    throw new IllegalArgumentException(
                            String.format("Test=%s already assigned to user=%s", testUid, userUid));
                });

        return user2TestRepository.save(
                new RelUser2TestEntity()
                        .setUser2TestId(new RelUser2TestEntity.User2TestId(userUid, testUid))
                        .setAttemptsAllowed(attemptsAllowed));
    }

    @Override
    public RelUser2TestEntity setTestResults(UUID userUid, UUID testUid, int percent) {
        RelUser2TestEntity user2TestEntity = user2TestRepository
                .findById(new RelUser2TestEntity.User2TestId(userUid, testUid))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                String.format("Test=%s is not assigned to user=%s", testUid, userUid)));

        int attemptsAllowed = user2TestEntity.getAttemptsAllowed();

        if (attemptsAllowed > 1) {
            user2TestEntity.setAttemptsAllowed(--attemptsAllowed);
            user2TestEntity.setPercent(percent);
        } else {
            throw new IllegalArgumentException(
                    String.format("No attempts allowed for user=%s with test=%s", userUid, testUid));
        }

        return user2TestRepository.save(user2TestEntity);
    }

    @Override
    public void setAdmin(UUID userUid) {
        updateUserGrantsInternal(userUid, true);
    }

    @Override
    public void resetAdmin(UUID userUid) {
        updateUserGrantsInternal(userUid, false);
    }

    @Override
    public void blockUser(UUID userUid) {
        updateUserStatusInternal(userUid, true);
    }

    @Override
    public void unblockUser(UUID userUid) {
        updateUserStatusInternal(userUid, false);
    }

    private void checkUserExists(UUID userUid) {
        getUserByUidInternal(userUid);
    }

    private ObjUserEntity getUserByUidInternal(UUID userUid) {
        return usersRepository.findById(userUid)
                .orElseThrow(() -> new NotFoundUserException("Not found user with uid=" + userUid));
    }

    private void updateUserGrantsInternal(UUID userUid, boolean isAdmin) {
        usersRepository.save(getUserByUidInternal(userUid)
                .setAdmin(isAdmin));
    }

    private void updateUserStatusInternal(UUID userUid, boolean isBlocked) {
        usersRepository.save(getUserByUidInternal(userUid)
                .setBlocked(isBlocked));
    }
}
