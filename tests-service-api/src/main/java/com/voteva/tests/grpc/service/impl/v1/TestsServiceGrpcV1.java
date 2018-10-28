package com.voteva.tests.grpc.service.impl.v1;

import com.voteva.common.grpc.model.Empty;
import com.voteva.tests.converter.ModelConverter;
import com.voteva.tests.exception.NotFoundTestException;
import com.voteva.tests.grpc.model.v1.GAddTestRequest;
import com.voteva.tests.grpc.model.v1.GAddTestResponse;
import com.voteva.tests.grpc.model.v1.GGetAllTestsRequest;
import com.voteva.tests.grpc.model.v1.GGetAllTestsResponse;
import com.voteva.tests.grpc.model.v1.GGetTestCategoriesRequest;
import com.voteva.tests.grpc.model.v1.GGetTestCategoriesResponse;
import com.voteva.tests.grpc.model.v1.GGetTestRequest;
import com.voteva.tests.grpc.model.v1.GGetTestResponse;
import com.voteva.tests.grpc.model.v1.GGetTestsByCategoryRequest;
import com.voteva.tests.grpc.model.v1.GGetTestsByCategoryResponse;
import com.voteva.tests.grpc.model.v1.GRemoveTestRequest;
import com.voteva.tests.grpc.service.v1.TestsServiceV1Grpc;
import com.voteva.tests.model.entity.ObjTestEntity;
import com.voteva.tests.service.TestsService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@GRpcService
public class TestsServiceGrpcV1 extends TestsServiceV1Grpc.TestsServiceV1ImplBase {

    private final TestsService testsService;

    @Autowired
    public TestsServiceGrpcV1(TestsService testsService) {
        this.testsService = testsService;
    }

    @Override
    public void getTestCategories(GGetTestCategoriesRequest request, StreamObserver<GGetTestCategoriesResponse> responseObserver) {
        try {
            List<String> categories = testsService.getCategories();

            responseObserver.onNext(GGetTestCategoriesResponse.newBuilder()
                    .addAllCategories(categories)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void getAllTests(GGetAllTestsRequest request, StreamObserver<GGetAllTestsResponse> responseObserver) {
        try {
            Page<ObjTestEntity> tests = testsService.getAllTests(ModelConverter.convert(request.getPageable()));

            responseObserver.onNext(GGetAllTestsResponse.newBuilder()
                    .setPage(ModelConverter.convert(tests))
                    .addAllTests(tests.getContent()
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
    public void getTestsByCategory(GGetTestsByCategoryRequest request, StreamObserver<GGetTestsByCategoryResponse> responseObserver) {
        try {
            Page<ObjTestEntity> tests = testsService.getTestsByCategory(
                    request.getCategory(), ModelConverter.convert(request.getPageable()));

            responseObserver.onNext(GGetTestsByCategoryResponse.newBuilder()
                    .setPage(ModelConverter.convert(tests))
                    .addAllTests(tests.getContent()
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
    public void getTest(GGetTestRequest request, StreamObserver<GGetTestResponse> responseObserver) {
        try {
            ObjTestEntity testEntity = testsService.getTest(ModelConverter.convert(request.getTestUid()));

            responseObserver.onNext(GGetTestResponse.newBuilder()
                    .setTestInfo(ModelConverter.convert(testEntity))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void addTest(GAddTestRequest request, StreamObserver<GAddTestResponse> responseObserver) {
        try {
            UUID testUid = testsService.addTest(ModelConverter.convert(request));

            responseObserver.onNext(GAddTestResponse.newBuilder()
                    .setTestUid(ModelConverter.convert(testUid))
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            onError(responseObserver, e);
        }
    }

    @Override
    public void removeTest(GRemoveTestRequest request, StreamObserver<Empty> responseObserver) {
        try {
            testsService.removeTest(ModelConverter.convert(request.getTestUid()));

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
