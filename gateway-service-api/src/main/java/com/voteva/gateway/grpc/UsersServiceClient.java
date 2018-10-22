package com.voteva.gateway.grpc;

import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.users.grpc.model.v1.ObjUserInfo;
import com.voteva.users.grpc.model.v1.UserInfoRequest;
import com.voteva.users.grpc.model.v1.UserInfoResponse;
import com.voteva.users.grpc.model.v1.UserUidRequest;
import com.voteva.users.grpc.service.v1.UsersServiceV1Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class UsersServiceClient {

    private UsersServiceV1Grpc.UsersServiceV1BlockingStub usersServiceV1BlockingStub;

    @PostConstruct
    private void init() {
        // TODO move to props
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        usersServiceV1BlockingStub = UsersServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public ObjUserInfo getUserInfo(UUID uuid) {
        UserUidRequest request = UserUidRequest.newBuilder()
                .setUuid(String.valueOf(uuid))
                .build();

        UserInfoResponse response = usersServiceV1BlockingStub.getUserInfoByUid(request);

        return response.getObjUserInfo();
    }

    public ObjUserInfo addUser(AddUserRequest addUserRequest) {
        ObjUserInfo objUserInfo = ObjUserInfo.newBuilder()
                .setEmail(addUserRequest.getEmail())
                .build();

        UserInfoRequest request = UserInfoRequest.newBuilder()
                .setObjUserInfo(objUserInfo)
                .build();

        UserInfoResponse response = usersServiceV1BlockingStub.addUser(request);

        return response.getObjUserInfo();
    }
}
