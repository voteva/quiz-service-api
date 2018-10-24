package com.voteva.tests.grpc.service.impl.v1;

import com.voteva.tests.converter.ModelConverter;
import com.voteva.tests.exception.NotFoundTestException;
import com.voteva.tests.grpc.model.v1.GAddTestRequest;
import com.voteva.tests.grpc.model.v1.GAddTestResponse;
import com.voteva.tests.grpc.model.v1.GGetTestRequest;
import com.voteva.tests.grpc.model.v1.GGetTestResponse;
import com.voteva.tests.grpc.model.v1.GRemoveTestRequest;
import com.voteva.tests.grpc.model.v1.GRemoveTestResponse;
import com.voteva.tests.grpc.model.v1.GTestInfo;
import com.voteva.tests.grpc.service.v1.TestsServiceV1Grpc;
import com.voteva.tests.service.TestsService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GRpcService
public class TestsServiceGrpcV1 extends TestsServiceV1Grpc.TestsServiceV1ImplBase {

    private final TestsService testsService;

    @Autowired
    public TestsServiceGrpcV1(TestsService testsService) {
        this.testsService = testsService;
    }

    @Override
    public void getTest(GGetTestRequest request, StreamObserver<GGetTestResponse> responseObserver) {
        try {
            GTestInfo response = ModelConverter.convert(testsService.getTestByUid(UUID.fromString(request.getUuid())));

            responseObserver.onNext(GGetTestResponse.newBuilder()
                    .setTestInfo(response)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void addTest(GAddTestRequest request, StreamObserver<GAddTestResponse> responseObserver) {
        try {
            String response = String.valueOf(testsService.addTest(ModelConverter.convert(request)));

            responseObserver.onNext(GAddTestResponse.newBuilder().setUuid(response).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void removeTest(GRemoveTestRequest request, StreamObserver<GRemoveTestResponse> responseObserver) {
        try {
            testsService.removeTest(UUID.fromString(request.getUuid()));

            responseObserver.onNext(GRemoveTestResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    private void onError(StreamObserver<?> responseObserver, Exception e) {
        Status status;

        if (e instanceof IllegalArgumentException) {
            status = Status.INVALID_ARGUMENT;
        } else if (e instanceof NotFoundTestException) {
            status = Status.NOT_FOUND;
        } else {
            status = Status.INTERNAL;
        }

        responseObserver.onError(status
                .withDescription(e.getMessage())
                .asRuntimeException());
    }
}
