/*
 * Copyright 2024 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.shipping.controller;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.shipping.*;
import cdx.opencdx.shipping.model.OpenCDXOrderModel;
import cdx.opencdx.shipping.repository.OpenCDXOrderRepository;
import cdx.opencdx.shipping.service.OpenCDXShippingService;
import cdx.opencdx.shipping.service.impl.OpenCDXShippingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXGrpcShippingControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Mock
    OpenCDXOrderRepository openCDXOrderRepository;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    OpenCDXShippingService openCDXShippingService;

    OpenCDXGrpcShippingController openCDXGrpcShippingController;

    @BeforeEach
    void beforeEach() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(argument)
                                .build());
                    }
                });
        Mockito.when(this.openCDXProfileRepository.findByNationalHealthId(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        String argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(ObjectId.get())
                                .nationalHealthId(argument)
                                .userId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXOrderRepository.save(Mockito.any(OpenCDXOrderModel.class)))
                .thenAnswer(new Answer<OpenCDXOrderModel>() {
                    @Override
                    public OpenCDXOrderModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXOrderModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXOrderRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXOrderModel>>() {
                    @Override
                    public Optional<OpenCDXOrderModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXOrderModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXOrderRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXOrderModel.builder()
                                .id(ObjectId.get())
                                .patientId(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXOrderRepository.findAllByPatientId(
                        Mockito.any(ObjectId.class), Mockito.any(Pageable.class)))
                .thenAnswer(new Answer<PageImpl<OpenCDXOrderModel>>() {
                    @Override
                    public PageImpl<OpenCDXOrderModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return new PageImpl<>(
                                List.of(OpenCDXOrderModel.builder()
                                        .id(ObjectId.get())
                                        .patientId(argument)
                                        .build()),
                                PageRequest.of(1, 10),
                                1);
                    }
                });

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        this.openCDXGrpcShippingController = new OpenCDXGrpcShippingController(openCDXShippingService);
    }

    @Test
    void createOrder() {
        StreamObserver<CreateOrderResponse> responseObserver = Mockito.mock(StreamObserver.class);

        CreateOrderRequest createOrderRequest = CreateOrderRequest.newBuilder()
                .setOrder(Order.newBuilder()
                        .setPatientId(ObjectId.get().toHexString())
                        .setShippingAddress(Address.newBuilder()
                                .setAddress1("1001 Main St")
                                .setState("CA")
                                .setCity("San Francisco")
                                .setPostalCode("94105")
                                .setCountryId(ObjectId.get().toHexString())
                                .build())
                        .setTestCaseId(ObjectId.get().toHexString())
                        .build())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcShippingController.createOrder(createOrderRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getOrder() {
        StreamObserver<GetOrderResponse> responseObserver = Mockito.mock(StreamObserver.class);

        GetOrderRequest getOrderRequest =
                GetOrderRequest.newBuilder().setId(ObjectId.get().toHexString()).build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcShippingController.getOrder(getOrderRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateOrder() {
        StreamObserver<UpdateOrderResponse> responseObserver = Mockito.mock(StreamObserver.class);

        UpdateOrderRequest updateOrderRequest = UpdateOrderRequest.newBuilder()
                .setOrder(Order.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .setPatientId(ObjectId.get().toHexString())
                        .setShippingAddress(Address.newBuilder()
                                .setAddress1("1001 Main St")
                                .setState("CA")
                                .setCity("San Francisco")
                                .setPostalCode("94105")
                                .setCountryId(ObjectId.get().toHexString())
                                .build())
                        .setTestCaseId(ObjectId.get().toHexString())
                        .build())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcShippingController.updateOrder(updateOrderRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void cancelOrder() {
        StreamObserver<CancelOrderResponse> responseObserver = Mockito.mock(StreamObserver.class);

        CancelOrderRequest cancelOrderRequest = CancelOrderRequest.newBuilder()
                .setId(ObjectId.get().toHexString())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcShippingController.cancelOrder(cancelOrderRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void listOrders() {
        StreamObserver<ListOrdersResponse> responseObserver = Mockito.mock(StreamObserver.class);

        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertDoesNotThrow(
                () -> this.openCDXGrpcShippingController.listOrders(listOrdersRequest, responseObserver));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}
