package com.voteva.quiz.grpc.service.impl.v1;

import com.voteva.common.grpc.model.Empty;
import com.voteva.quiz.converter.ModelConverter;
import com.voteva.quiz.exception.NotFoundUserException;
import com.voteva.quiz.grpc.model.v1.GAddUserRequest;
import com.voteva.quiz.grpc.model.v1.GAddUserResponse;
import com.voteva.quiz.grpc.model.v1.GAssignTestRequest;
import com.voteva.quiz.grpc.model.v1.GBlockUserRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersResponse;
import com.voteva.quiz.grpc.model.v1.GGetUserRequest;
import com.voteva.quiz.grpc.model.v1.GGetUserResponse;
import com.voteva.quiz.grpc.model.v1.GGetUserTestsRequest;
import com.voteva.quiz.grpc.model.v1.GGetUserTestsResponse;
import com.voteva.quiz.grpc.model.v1.GRemoveAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsResponse;
import com.voteva.quiz.grpc.model.v1.GUnblockUserRequest;
import com.voteva.quiz.grpc.model.v1.GUpdateUserRequest;
import com.voteva.quiz.grpc.service.v1.QuizServiceV1Grpc;
import com.voteva.quiz.model.entity.ObjUserEntity;
import com.voteva.quiz.model.entity.RelUser2TestEntity;
import com.voteva.quiz.service.QuizService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@GRpcService
public class QuizServiceGrpcV1 extends QuizServiceV1Grpc.QuizServiceV1ImplBase {

    private final QuizService quizService;

    @Autowired
    public QuizServiceGrpcV1(QuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    public void getUserTests(GGetUserTestsRequest request,
                             StreamObserver<GGetUserTestsResponse> responseObserver) {
        try {
            List<RelUser2TestEntity> userTests = quizService.getUserTests(
                    ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(GGetUserTestsResponse.newBuilder()
                    .addAllTests(userTests.stream()
                            .map(ModelConverter::convert)
                            .collect(Collectors.toList()))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void assignTest(GAssignTestRequest request,
                           StreamObserver<Empty> responseObserver) {
        try {
            quizService.assignTest(
                    ModelConverter.convert(request.getUserUid()),
                    ModelConverter.convert(request.getTestUid()),
                    request.getAttemptsAllowed());

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void setTestResults(GSetTestResultsRequest request,
                               StreamObserver<GSetTestResultsResponse> responseObserver) {
        try {
            RelUser2TestEntity user2TestEntity = quizService.setTestResults(
                    ModelConverter.convert(request.getUserUid()),
                    ModelConverter.convert(request.getTestUid()),
                    request.getPercent());

            responseObserver.onNext(GSetTestResultsResponse.newBuilder()
                    .setTestResultsInfo(ModelConverter.convert(user2TestEntity))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void getAllUsers(GGetAllUsersRequest request,
                            StreamObserver<GGetAllUsersResponse> responseObserver) {
        try {
            Page<ObjUserEntity> users = quizService.getAllUsers(
                    ModelConverter.convert(request.getPageable()));

            responseObserver.onNext(GGetAllUsersResponse.newBuilder()
                    .setPage(ModelConverter.convert(users))
                    .addAllUsers(users.getContent()
                            .stream()
                            .map(ModelConverter::convert)
                            .collect(Collectors.toList()))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void getUser(GGetUserRequest request,
                        StreamObserver<GGetUserResponse> responseObserver) {
        try {
            ObjUserEntity userEntity = quizService.getUser(
                    ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(GGetUserResponse.newBuilder()
                    .setUserInfo(ModelConverter.convert(userEntity))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void addUser(GAddUserRequest request,
                        StreamObserver<GAddUserResponse> responseObserver) {
        try {
            ObjUserEntity userEntity = quizService.addUser(
                    new ObjUserEntity()
                            .setUserUid(ModelConverter.convert(request.getUserUid()))
                            .setFirstName(request.getFirstName())
                            .setLastName(request.getLastName()));

            responseObserver.onNext(GAddUserResponse.newBuilder()
                    .setUserInfo(ModelConverter.convert(userEntity))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void updateUser(GUpdateUserRequest request,
                           StreamObserver<Empty> responseObserver) {
        try {
            //quizService.addUser(ModelConverter.convert(request.getUserInfo())); TODO

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }


    @Override
    public void setAdminGrants(GSetAdminGrantsRequest request,
                               StreamObserver<Empty> responseObserver) {
        try {
            quizService.setAdmin(ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void removeAdminGrants(GRemoveAdminGrantsRequest request,
                                  StreamObserver<Empty> responseObserver) {
        try {
            quizService.resetAdmin(ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void blockUser(GBlockUserRequest request,
                          StreamObserver<Empty> responseObserver) {
        try {
            quizService.blockUser(ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void unblockUser(GUnblockUserRequest request,
                            StreamObserver<Empty> responseObserver) {
        try {
            quizService.unblockUser(ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    private void onError(StreamObserver<?> responseObserver, Exception e) {
        Status status;

        if (e instanceof IllegalArgumentException) {
            status = Status.INVALID_ARGUMENT;
        } else if (e instanceof NotFoundUserException) {
            status = Status.NOT_FOUND;
        } else {
            status = Status.INTERNAL;
        }

        responseObserver.onError(status
                .withDescription(e.getMessage())
                .asRuntimeException());
    }
}
