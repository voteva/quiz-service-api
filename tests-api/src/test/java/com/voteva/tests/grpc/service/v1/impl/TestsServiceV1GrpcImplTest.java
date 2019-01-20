package com.voteva.tests.grpc.service.v1.impl;

import com.voteva.common.grpc.model.Empty;
import com.voteva.common.grpc.model.GPageable;
import com.voteva.common.grpc.model.GUuid;
import com.voteva.tests.grpc.client.GRpcAccessServiceClient;
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
import com.voteva.tests.model.entity.TestEntity;
import com.voteva.tests.service.TestsService;
import io.grpc.stub.StreamObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestsServiceV1GrpcImplTest {

    private static final UUID TEST_UID = UUID.fromString("517df602-4ffb-4e08-9626-2a0cf2db4849");
    private static final String TEST_NAME = "Java Test";
    private static final String CATEGORY = "Java";
    private static final Empty EMPTY = Empty.newBuilder().build();

    @Mock
    private TestsService testsService;

    @Mock
    private GRpcAccessServiceClient accessServiceClient;

    @InjectMocks
    private TestsServiceV1GrpcImpl testsServiceV1Grpc;

    @Test
    public void testGetTestCategories() {
        List<String> serviceResponse = Collections.singletonList(CATEGORY);

        when(accessServiceClient.checkAccess(any())).thenReturn(EMPTY);
        when(testsService.getCategories()).thenReturn(serviceResponse);

        GGetTestCategoriesRequest request = GGetTestCategoriesRequest.newBuilder().build();

        StreamObserver<GGetTestCategoriesResponse> observer = mock(StreamObserver.class);

        testsServiceV1Grpc.getTestCategories(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GGetTestCategoriesResponse> captor = ArgumentCaptor.forClass(GGetTestCategoriesResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GGetTestCategoriesResponse response = captor.getValue();
        assertEquals(response.getCategoriesList().size(), serviceResponse.size());
        assertEquals(response.getCategoriesList().get(0), serviceResponse.get(0));
    }

    @Test
    public void testAddUser() {
        Page<TestEntity> serviceResponse = new PageImpl<>(Collections.singletonList(getMockObjTestEntity()));

        when(accessServiceClient.checkAccess(any())).thenReturn(EMPTY);
        when(testsService.getAllTests(any(Pageable.class))).thenReturn(serviceResponse);

        GGetAllTestsRequest request = GGetAllTestsRequest.newBuilder()
                .setPageable(GPageable.newBuilder().setPage(0).setSize(20).build())
                .build();

        StreamObserver<GGetAllTestsResponse> observer = mock(StreamObserver.class);

        testsServiceV1Grpc.getAllTests(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GGetAllTestsResponse> captor = ArgumentCaptor.forClass(GGetAllTestsResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GGetAllTestsResponse response = captor.getValue();
        assertEquals(response.getTestsList().size(), serviceResponse.getTotalElements());
        assertEquals(response.getTestsList().get(0).getTestName(), serviceResponse.getContent().get(0).getTestName());
        assertEquals(response.getTestsList().get(0).getTestCategory(), serviceResponse.getContent().get(0).getTestCategory());
    }

    @Test
    public void testGetTestsByCategory() {
        Page<TestEntity> serviceResponse = new PageImpl<>(Collections.singletonList(getMockObjTestEntity()));

        when(accessServiceClient.checkAccess(any())).thenReturn(EMPTY);
        when(testsService.getTestsByCategory(any(String.class), any(Pageable.class))).thenReturn(serviceResponse);

        GGetTestsByCategoryRequest request = GGetTestsByCategoryRequest.newBuilder()
                .setPageable(GPageable.newBuilder().setPage(0).setSize(20).build())
                .setCategory(CATEGORY)
                .build();

        StreamObserver<GGetTestsByCategoryResponse> observer = mock(StreamObserver.class);

        testsServiceV1Grpc.getTestsByCategory(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GGetTestsByCategoryResponse> captor = ArgumentCaptor.forClass(GGetTestsByCategoryResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GGetTestsByCategoryResponse response = captor.getValue();
        assertEquals(response.getTestsList().size(), serviceResponse.getTotalElements());
        assertEquals(response.getTestsList().get(0).getTestName(), serviceResponse.getContent().get(0).getTestName());
        assertEquals(response.getTestsList().get(0).getTestCategory(), serviceResponse.getContent().get(0).getTestCategory());
    }

    @Test
    public void testGetTest() {
        TestEntity serviceResponse = getMockObjTestEntity();

        when(accessServiceClient.checkAccess(any())).thenReturn(EMPTY);
        when(testsService.getTest(any(UUID.class))).thenReturn(serviceResponse);

        GGetTestRequest request = GGetTestRequest.newBuilder()
                .setTestUid(GUuid.newBuilder().setUuid(TEST_UID.toString()).build())
                .build();

        StreamObserver<GGetTestResponse> observer = mock(StreamObserver.class);

        testsServiceV1Grpc.getTest(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GGetTestResponse> captor = ArgumentCaptor.forClass(GGetTestResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GGetTestResponse response = captor.getValue();
        assertEquals(response.getTestInfo().getTestUid().getUuid(), serviceResponse.getTestUid().toString());
        assertEquals(response.getTestInfo().getTestName(), serviceResponse.getTestName());
        assertEquals(response.getTestInfo().getTestCategory(), serviceResponse.getTestCategory());
    }

    @Test
    public void testAddTest() {
        when(accessServiceClient.checkAccess(any())).thenReturn(EMPTY);
        when(testsService.addTest(any(TestEntity.class))).thenReturn(TEST_UID);

        GAddTestRequest request = GAddTestRequest.newBuilder()
                .setTestName(TEST_NAME)
                .setTestCategory(CATEGORY)
                .build();

        StreamObserver<GAddTestResponse> observer = mock(StreamObserver.class);

        testsServiceV1Grpc.addTest(request, observer);
        verify(observer, times(1)).onCompleted();

        ArgumentCaptor<GAddTestResponse> captor = ArgumentCaptor.forClass(GAddTestResponse.class);
        verify(observer, times(1)).onNext(captor.capture());

        GAddTestResponse response = captor.getValue();
        assertEquals(response.getTestUid().getUuid(), TEST_UID.toString());
    }

    private TestEntity getMockObjTestEntity() {
        return new TestEntity()
                .setTestUid(TEST_UID)
                .setTestName(TEST_NAME)
                .setTestCategory(CATEGORY)
                .setQuestions(new ArrayList<>());
    }
}
