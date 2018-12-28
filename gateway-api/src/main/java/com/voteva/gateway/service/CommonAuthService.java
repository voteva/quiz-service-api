package com.voteva.gateway.service;

import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.out.AddUserInfo;
import com.voteva.gateway.web.to.out.UserInfo;

import java.util.UUID;

public interface CommonAuthService {

    PagedResult<UserInfo> getUsers(int page, int size);

    UserInfo getUserByUid(UUID userUid);

    AddUserInfo addUser(AddUserRequest request);

    void setAdminGrants(UUID userUid);

    void removeAdminGrants(UUID userUid);

    void blockUser(UUID userUid);

    void unblockUser(UUID userUid);
}
