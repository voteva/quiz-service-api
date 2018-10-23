package com.voteva.users.service.impl;

import com.voteva.users.exception.NotFoundUserException;
import com.voteva.users.model.entity.ObjUserEntity;
import com.voteva.users.repository.UserRepository;
import com.voteva.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<ObjUserEntity> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public ObjUserEntity getUserByUid(UUID userUid) {
        return getUserByUidInternal(userUid);
    }

    @Override
    public ObjUserEntity getUserByEmail(String email) {
        return userRepository.findObjUserEntityByUserEmail(email)
                .orElseThrow(() -> new NotFoundUserException("Not found user with email=" + email));
    }

    @Override
    public ObjUserEntity addUser(ObjUserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public ObjUserEntity updateUser(ObjUserEntity user) {
        UUID userUid = Optional.ofNullable(user.getUserUid())
                .orElseThrow(() -> new IllegalArgumentException("User UID is not set"));

        ObjUserEntity updatedUserEntity = user.setUserId(getUserByUidInternal(userUid).getUserId());

        return userRepository.save(updatedUserEntity);
    }

    @Override
    public void removeUser(UUID userUid) {
        userRepository.deleteById(getUserByUidInternal(userUid).getUserId());
    }

    private ObjUserEntity getUserByUidInternal(UUID userUid) {
        return userRepository.findObjUserEntityByUserUid(userUid)
                .orElseThrow(() -> new NotFoundUserException("Not found user with uid=" + userUid));
    }
}
