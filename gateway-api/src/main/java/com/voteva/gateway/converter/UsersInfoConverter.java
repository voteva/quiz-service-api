package com.voteva.gateway.converter;

import com.voteva.common.grpc.model.GPage;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.out.UserInfo;
import com.voteva.quiz.grpc.model.v1.GAddUserRequest;
import com.voteva.quiz.grpc.model.v1.GUserInfo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsersInfoConverter {

    public static PagedResult<UserInfo> convert(List<GUserInfo> userInfoList, GPage page) {
        return new PagedResult<>(
                page.getTotalElements(),
                userInfoList.stream()
                        .map(UsersInfoConverter::convert)
                        .collect(Collectors.toList()));
    }

    public static UserInfo convert(GUserInfo userInfo) {
        return new UserInfo()
                .setUserUid(CommonConverter.convert(userInfo.getUserUid()))
                .setFirstName(userInfo.getFirstName())
                .setLastName(userInfo.getLastName())
                .setCreatedDateTime(userInfo.getCreatedDtime())
                .setAdmin(userInfo.getIsAdmin())
                .setBlocked(userInfo.getIsBlocked());
    }

    public static GAddUserRequest convert(AddUserRequest addUserRequest, UUID userUid) {
        return GAddUserRequest.newBuilder()
                .setUserUid(CommonConverter.convert(userUid))
                .setFirstName(addUserRequest.getFirstName())
                .setLastName(addUserRequest.getLastName())
                .build();
    }
}
