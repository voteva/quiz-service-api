package com.voteva.users.grpc.service.v1.impl;

import com.voteva.auth.grpc.service.v1.impl.UsersServiceV1GrpcImpl;
import com.voteva.users.grpc.model.v1.GAddUserAuthRequest;
import com.voteva.users.grpc.model.v1.GAddUserAuthResponse;
import com.voteva.auth.service.UsersService;
import io.grpc.stub.StreamObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsersServiceV1GrpcImplTest {

    private static final UUID USER_UID = UUID.fromString("21793aac-0171-42c1-9c66-7284ec24a330");
    private static final String EMAIL = "user@example.com";
    private static final String PASSWORD = "$eCuReP@$$wOrD";

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersServiceV1GrpcImpl usersServiceV1Grpc;

    @Test
    public void testAddUser() {
        when(usersService.addUser(any(String.class), any(String.class))).thenReturn(USER_UID);

        GAddUserAuthRequest request = GAddUserAuthRequest.newBuilder()
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .build();

        StreamObserver<GAddUserAuthResponse> observer = mock(StreamObserver.class);

        usersServiceV1Grpc.addUser(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GAddUserAuthResponse> captor = ArgumentCaptor.forClass(GAddUserAuthResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GAddUserAuthResponse response = captor.getValue();
        assertEquals(response.getUserUid().getUuid(), USER_UID.toString());
    }
}
