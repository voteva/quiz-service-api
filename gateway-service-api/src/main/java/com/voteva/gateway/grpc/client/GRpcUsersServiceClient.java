package com.voteva.gateway.grpc.client;

import com.voteva.users.grpc.model.v1.GAddUserRequest;
import com.voteva.users.grpc.model.v1.GAddUserResponse;
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

    public GAddUserResponse addUser(GAddUserRequest request) {
        return usersServiceV1BlockingStub.addUser(request);
    }
}
