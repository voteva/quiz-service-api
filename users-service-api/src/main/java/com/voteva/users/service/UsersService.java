package com.voteva.users.service;

import com.voteva.users.model.entity.ObjUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UsersService {

    Page<ObjUserEntity> getUsers(Pageable pageable);

    ObjUserEntity getUserByUid(UUID userUid);

    ObjUserEntity getUserByEmail(String email);

    ObjUserEntity addUser(ObjUserEntity userInfo);

    ObjUserEntity updateUser(ObjUserEntity userInfo);

    void removeUser(UUID userUid);
}
