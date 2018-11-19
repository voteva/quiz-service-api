package com.voteva.quiz.service;

import com.voteva.quiz.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UsersService {

    Page<UserEntity> getAllUsers(Pageable pageable);

    UserEntity getUser(UUID userUid);

    UserEntity addUser(UserEntity entity);

    void updateUser(UserEntity entity);

    void setAdmin(UUID userUid);

    void resetAdmin(UUID userUid);

    void blockUser(UUID userUid);

    void unblockUser(UUID userUid);
}
