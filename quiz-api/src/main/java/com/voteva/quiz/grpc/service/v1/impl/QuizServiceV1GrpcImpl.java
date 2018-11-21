package com.voteva.quiz.grpc.service.v1.impl;

import com.voteva.common.grpc.model.Empty;
import com.voteva.quiz.converter.ModelConverter;
import com.voteva.quiz.exception.NotFoundUserException;
import com.voteva.quiz.grpc.model.v1.GAddUserRequest;
import com.voteva.quiz.grpc.model.v1.GAddUserResponse;
import com.voteva.quiz.grpc.model.v1.GAssignTestRequest;
import com.voteva.quiz.grpc.model.v1.GBlockUserRequest;
import com.voteva.quiz.grpc.model.v1.GDeleteResultsRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersRequest;
import com.voteva.quiz.grpc.model.v1.GGetAllUsersResponse;
import com.voteva.quiz.grpc.model.v1.GGetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GGetTestResultsResponse;
import com.voteva.quiz.grpc.model.v1.GGetUserRequest;
import com.voteva.quiz.grpc.model.v1.GGetUserResponse;
import com.voteva.quiz.grpc.model.v1.GRemoveAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetAdminGrantsRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsRequest;
import com.voteva.quiz.grpc.model.v1.GSetTestResultsResponse;
import com.voteva.quiz.grpc.model.v1.GUnblockUserRequest;
import com.voteva.quiz.grpc.model.v1.GUpdateUserRequest;
import com.voteva.quiz.grpc.service.v1.QuizServiceV1Grpc;
import com.voteva.quiz.model.entity.ResultEntity;
import com.voteva.quiz.model.entity.UserEntity;
import com.voteva.quiz.service.QuizService;
import com.voteva.quiz.service.UsersService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@GRpcService
public class QuizServiceV1GrpcImpl extends QuizServiceV1Grpc.QuizServiceV1ImplBase {

    private final QuizService quizService;
    private final UsersService usersService;

    @Autowired
    public QuizServiceV1GrpcImpl(
            QuizService quizService,
            UsersService usersService) {
        this.quizService = quizService;
        this.usersService = usersService;
    }

    @Override
    public void assignTest(
            GAssignTestRequest request,
            StreamObserver<Empty> responseObserver) {
        try {
            quizService.assignTest(
                    ModelConverter.convert(request.getUserUid()),
                    ModelConverter.convert(request.getTestUid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void getTestResults(
            GGetTestResultsRequest request,
            StreamObserver<GGetTestResultsResponse> responseObserver) {
        try {
            List<ResultEntity> testResults = quizService.getTestResults(
                    ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(GGetTestResultsResponse.newBuilder()
                    .addAllTestResults(testResults.stream()
                            .map(ModelConverter::convert)
                            .collect(Collectors.toList()))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void setTestResults(
            GSetTestResultsRequest request,
            StreamObserver<GSetTestResultsResponse> responseObserver) {
        try {
            ResultEntity resultEntity = quizService.setTestResults(
                    ModelConverter.convert(request.getUserUid()),
                    ModelConverter.convert(request.getTestUid()),
                    request.getPercent());

            responseObserver.onNext(GSetTestResultsResponse.newBuilder()
                    .setTestResultInfo(ModelConverter.convert(resultEntity))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void deleteResultsForTest(
            GDeleteResultsRequest request,
            StreamObserver<Empty> responseObserver) {
        try {
            quizService.deleteResultsForTest(ModelConverter.convert(request.getTestUid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void getAllUsers(
            GGetAllUsersRequest request,
            StreamObserver<GGetAllUsersResponse> responseObserver) {
        try {
            Page<UserEntity> users = usersService.getAllUsers(
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
    public void getUser(
            GGetUserRequest request,
            StreamObserver<GGetUserResponse> responseObserver) {
        try {
            UserEntity userEntity = usersService.getUser(
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
    public void addUser(
            GAddUserRequest request,
            StreamObserver<GAddUserResponse> responseObserver) {
        try {
            UserEntity userEntity = usersService.addUser(
                    new UserEntity()
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
    public void updateUser(
            GUpdateUserRequest request,
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
    public void setAdminGrants(
            GSetAdminGrantsRequest request,
            StreamObserver<Empty> responseObserver) {
        try {
            usersService.setAdmin(ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void removeAdminGrants(
            GRemoveAdminGrantsRequest request,
            StreamObserver<Empty> responseObserver) {
        try {
            usersService.resetAdmin(ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void blockUser(
            GBlockUserRequest request,
            StreamObserver<Empty> responseObserver) {
        try {
            usersService.blockUser(ModelConverter.convert(request.getUserUid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void unblockUser(
            GUnblockUserRequest request,
            StreamObserver<Empty> responseObserver) {
        try {
            usersService.unblockUser(ModelConverter.convert(request.getUserUid()));

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
