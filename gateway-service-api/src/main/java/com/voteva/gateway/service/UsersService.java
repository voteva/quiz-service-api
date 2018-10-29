package com.voteva.gateway.service;

import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.common.UserInfo;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.UpdateUserRequest;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.List;
import java.util.UUID;

public interface UsersService {

    PagedResult<UserInfo> getUsers(int page, int size);

    UserInfo getUserByUid(UUID userUid);

    UserInfo addUser(AddUserRequest addUserRequest);
/*
    UserInfo updateUser(UpdateUserRequest updateUserRequest);
*/

    void setAdminGrants(UUID userUid);

    void removeAdminGrants(UUID userUid);

    void blockUser(UUID userUid);

    void unblockUser(UUID userUid);

}
