package com.voteva.gateway.service;

import com.voteva.gateway.security.model.Principal;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.AssignTestRequest;
import com.voteva.gateway.web.to.in.TestResultsRequest;
import com.voteva.gateway.web.to.out.AddUserInfo;
import com.voteva.gateway.web.to.out.QuizInfo;
import com.voteva.gateway.web.to.out.UserFullInfo;

import java.util.List;
import java.util.UUID;

public interface QuizService {

    void assignTest(AssignTestRequest request, Principal principal);

    List<QuizInfo> getTestResults(Principal principal);

    QuizInfo setTestResults(TestResultsRequest request, Principal principal);

    PagedResult<UserFullInfo> getUsers(int page, int size);

    UserFullInfo getUserByUid(UUID userUid);

    AddUserInfo addUser(AddUserRequest request);

    void setAdminGrants(UUID userUid);

    void removeAdminGrants(UUID userUid);

    void blockUser(UUID userUid);

    void unblockUser(UUID userUid);
}
