package com.voteva.users.service;

import com.voteva.users.grpc.model.v1.ObjUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UsersService {

    Page<ObjUserInfo> getUsers(Pageable pageable);

    ObjUserInfo getUserByUid(UUID userUid);

    ObjUserInfo getUserByEmail(String email);

    ObjUserInfo addUser(ObjUserInfo userInfo);

    ObjUserInfo updateUser(ObjUserInfo userInfo);

    void removeUser(UUID userUid);

    void blockUser(UUID userUid);

    void unblockUser(UUID userUid);
}
