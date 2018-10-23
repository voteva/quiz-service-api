package com.voteva.quiz.grpc.service.impl.v1;

import com.voteva.common.grpc.model.Empty;
import com.voteva.quiz.converter.GRpcConverter;
import com.voteva.quiz.exception.NotFoundUserException;
import com.voteva.quiz.grpc.model.v1.GUser2TestAssignRequest;
import com.voteva.quiz.grpc.model.v1.GUser2TestResponse;
import com.voteva.quiz.grpc.model.v1.GUser2TestResultRequest;
import com.voteva.quiz.grpc.model.v1.GUserResponse;
import com.voteva.quiz.grpc.model.v1.GUserUidRequest;
import com.voteva.quiz.grpc.service.v1.QuizServiceV1Grpc;
import com.voteva.quiz.model.entity.ObjUserEntity;
import com.voteva.quiz.model.entity.RelUser2TestEntity;
import com.voteva.quiz.service.QuizService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GRpcService
public class QuizServiceGrpcV1 extends QuizServiceV1Grpc.QuizServiceV1ImplBase {

    private final QuizService quizService;

    @Autowired
    public QuizServiceGrpcV1(QuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    public void addUser(GUserUidRequest request, StreamObserver<GUserResponse> responseObserver) {
        try {
            ObjUserEntity userEntity = quizService.addUser(
                    new ObjUserEntity().setUserUid(UUID.fromString(request.getUuid())));

            responseObserver.onNext(GRpcConverter.convert(userEntity));
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void assignTest(GUser2TestAssignRequest request, StreamObserver<GUser2TestResponse> responseObserver) {
        try {
            RelUser2TestEntity user2TestEntity = quizService.assignTest(
                    UUID.fromString(request.getUserUid()),
                    UUID.fromString(request.getTestUid()),
                    request.getAttemptsAllowed());

            responseObserver.onNext(GRpcConverter.convert(user2TestEntity));
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void setTestResults(GUser2TestResultRequest request, StreamObserver<GUser2TestResponse> responseObserver) {
        try {
            RelUser2TestEntity user2TestEntity = quizService.setTestResults(
                    UUID.fromString(request.getUserUid()),
                    UUID.fromString(request.getTestUid()),
                    request.getPercent());

            responseObserver.onNext(GRpcConverter.convert(user2TestEntity));
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void setAdminGrants(GUserUidRequest request, StreamObserver<Empty> responseObserver) {
        try {
            quizService.setAdmin(UUID.fromString(request.getUuid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void removeAdminGrants(GUserUidRequest request, StreamObserver<Empty> responseObserver) {
        try {
            quizService.resetAdmin(UUID.fromString(request.getUuid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void blockUser(GUserUidRequest request, StreamObserver<Empty> responseObserver) {
        try {
            quizService.blockUser(UUID.fromString(request.getUuid()));

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void unblockUser(GUserUidRequest request, StreamObserver<Empty> responseObserver) {
        try {
            quizService.unblockUser(UUID.fromString(request.getUuid()));

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
