package com.voteva.gateway.service;

import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.out.AddUserResponse;
import com.voteva.gateway.web.to.out.QuizInfo;
import com.voteva.gateway.web.to.out.UserInfo;
import com.voteva.gateway.web.to.in.AddUserRequest;

import java.util.List;
import java.util.UUID;

public interface UsersService {

    PagedResult<UserInfo> getUsers(int page, int size);

    UserInfo getUserByUid(UUID userUid);

    List<QuizInfo> getUserTests(UUID userUid);

    AddUserResponse addUser(AddUserRequest addUserRequest);

    void setAdminGrants(UUID userUid);

    void removeAdminGrants(UUID userUid);

    void blockUser(UUID userUid);

    void unblockUser(UUID userUid);
}
