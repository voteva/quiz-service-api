package com.voteva.users.service.impl;

import com.voteva.users.converter.UserInfoConverter;
import com.voteva.users.exception.NotFoundUserException;
import com.voteva.users.grpc.model.v1.ObjUserInfo;
import com.voteva.users.model.entity.ObjUserEntity;
import com.voteva.users.repository.UserRepository;
import com.voteva.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<ObjUserInfo> getUsers(Pageable pageable) {
        Page<ObjUserEntity> entities = userRepository.findAll(pageable);
        return new PageImpl<>(entities.stream()
                .map(UserInfoConverter::convert)
                .collect(Collectors.toList()),
                pageable,
                entities.getTotalElements());
    }

    @Override
    public ObjUserInfo getUserByUid(UUID userUid) {
        return UserInfoConverter.convert(getUserByUidInternal(userUid));
    }

    @Override
    public ObjUserInfo getUserByEmail(String email) {
        return UserInfoConverter.convert(
                Optional.ofNullable(userRepository.findObjUserEntityByUserEmail(email))
                        .orElseThrow(() -> new NotFoundUserException("Not found user with email=" + email)));
    }

    @Override
    public ObjUserInfo addUser(ObjUserInfo userInfo) {
        return UserInfoConverter.convert(
                userRepository.save(UserInfoConverter.convert(userInfo)));
    }

    @Override
    public ObjUserInfo updateUser(ObjUserInfo userInfo) {
        UUID userUid = Optional.ofNullable(userInfo.getUuid())
                .map(UUID::fromString)
                .orElseThrow(() -> new IllegalArgumentException("User UID is not set"));

        ObjUserEntity updatedUserEntity = UserInfoConverter.convert(userInfo)
                .setUserId(getUserByUidInternal(userUid).getUserId());

        return UserInfoConverter.convert(userRepository.save(updatedUserEntity));
    }

    @Override
    public void removeUser(UUID userUid) {
        userRepository.deleteById(getUserByUidInternal(userUid).getUserId());
    }

    @Override
    public void blockUser(UUID userUid) {
        updateUserStatusInternal(userUid, true);
    }

    @Override
    public void unblockUser(UUID userUid) {
        updateUserStatusInternal(userUid, false);
    }

    private ObjUserEntity getUserByUidInternal(UUID userUid) {
        return Optional.ofNullable(userRepository.findObjUserEntityByUserUid(userUid))
                .orElseThrow(() -> new NotFoundUserException("Not found user with uid=" + userUid));
    }

    private void updateUserStatusInternal(UUID userUid, boolean isBlocked) {
        userRepository.save(getUserByUidInternal(userUid)
                .setBlocked(isBlocked));
    }
}
