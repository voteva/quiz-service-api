package com.voteva.gateway.grpc.client;

import com.voteva.users.grpc.model.v1.GAddUserRequest;
import com.voteva.users.grpc.model.v1.GUpdateUserRequest;
import com.voteva.users.grpc.model.v1.GUserEmailRequest;
import com.voteva.users.grpc.model.v1.GUserInfoResponse;
import com.voteva.users.grpc.model.v1.GUserUidRequest;
import com.voteva.users.grpc.service.v1.UsersServiceV1Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcUsersServiceClient {

    private UsersServiceV1Grpc.UsersServiceV1BlockingStub usersServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.UsersServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.UsersServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        usersServiceV1BlockingStub = UsersServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GUserInfoResponse getUserInfoByUid(GUserUidRequest request) {
        return usersServiceV1BlockingStub.getUserInfoByUid(request);
    }

    public GUserInfoResponse getUserInfoByEmail(GUserEmailRequest request) {
        return usersServiceV1BlockingStub.getUserInfoByEmail(request);
    }

    public GUserInfoResponse addUser(GAddUserRequest request) {
        return usersServiceV1BlockingStub.addUser(request);
    }

    public GUserInfoResponse updateUser(GUpdateUserRequest request) {
        return usersServiceV1BlockingStub.updateUser(request);
    }
}
