package com.voteva.gateway.service;

import com.voteva.gateway.web.to.common.UserInfo;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.UpdateUserRequest;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.List;
import java.util.UUID;

public interface UsersService {

    /*UserInfo getUserByUid(UUID userUid);

    UserInfo getUserByEmail(String email);

    List<UserInfo> getUsers(SpringDataWebProperties.Pageable pageable);

    UserInfo addUser(AddUserRequest addUserRequest);

    UserInfo updateUser(UpdateUserRequest updateUserRequest);

    void blockUser(UUID userUid);

    void unblockUser(UUID userUid);*/

}
