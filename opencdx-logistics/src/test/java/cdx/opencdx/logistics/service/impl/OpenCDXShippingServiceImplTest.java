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
package cdx.opencdx.logistics.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.common.ShippingStatus;
import cdx.opencdx.grpc.shipping.*;
import cdx.opencdx.logistics.model.OpenCDXOrderModel;
import cdx.opencdx.logistics.repository.OpenCDXOrderRepository;
import cdx.opencdx.logistics.service.OpenCDXShippingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXShippingServiceImplTest {
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

    @BeforeEach
    void beforeEach() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
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
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(argument)
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXOrderRepository.save(Mockito.any(OpenCDXOrderModel.class)))
                .thenAnswer(new Answer<OpenCDXOrderModel>() {
                    @Override
                    public OpenCDXOrderModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXOrderModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        Mockito.when(this.openCDXOrderRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXOrderModel>>() {
                    @Override
                    public Optional<OpenCDXOrderModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);

                        return Optional.of(OpenCDXOrderModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .created(Instant.now())
                                .modified(Instant.now())
                                .creator(OpenCDXIdentifier.get())
                                .modifier(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXOrderRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXOrderModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .patientId(OpenCDXIdentifier.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));

        Mockito.when(this.openCDXOrderRepository.findAllByPatientId(
                        Mockito.any(OpenCDXIdentifier.class), Mockito.any(Pageable.class)))
                .thenAnswer(new Answer<PageImpl<OpenCDXOrderModel>>() {
                    @Override
                    public PageImpl<OpenCDXOrderModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return new PageImpl<>(
                                List.of(OpenCDXOrderModel.builder()
                                        .id(OpenCDXIdentifier.get())
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
    }

    @Test
    void createOrder() {
        CreateOrderRequest createOrderRequest = CreateOrderRequest.newBuilder()
                .setOrder(Order.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setShippingAddress(Address.newBuilder()
                                .setAddress1("1001 Main St")
                                .setState("CA")
                                .setCity("San Francisco")
                                .setPostalCode("94105")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .setCreated(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setModified(Timestamp.newBuilder().setSeconds(1000000).build())
                        .setCreator(OpenCDXIdentifier.get().toHexString())
                        .setModifier(OpenCDXIdentifier.get().toHexString())
                        .setStatus(ShippingStatus.PENDING)
                        .setTestCaseId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertDoesNotThrow(() -> this.openCDXShippingService.createOrder(createOrderRequest));
    }

    @Test
    void getOrder() {
        GetOrderRequest getOrderRequest = GetOrderRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertDoesNotThrow(() -> this.openCDXShippingService.getOrder(getOrderRequest));
    }

    @Test
    void updateOrder() {
        UpdateOrderRequest updateOrderRequest = UpdateOrderRequest.newBuilder()
                .setOrder(Order.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setShippingAddress(Address.newBuilder()
                                .setAddress1("1001 Main St")
                                .setState("CA")
                                .setCity("San Francisco")
                                .setPostalCode("94105")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .setTestCaseId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertDoesNotThrow(() -> this.openCDXShippingService.updateOrder(updateOrderRequest));
    }

    @Test
    void cancelOrder() {
        CancelOrderRequest cancelOrderRequest = CancelOrderRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertDoesNotThrow(() -> this.openCDXShippingService.cancelOrder(cancelOrderRequest));
    }

    @Test
    void listOrders() {
        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertDoesNotThrow(() -> this.openCDXShippingService.listOrders(listOrdersRequest));
    }

    @Test
    void listOrders_SortAsc() {
        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(true)
                        .setSort("id")
                        .build())
                .build();

        Assertions.assertDoesNotThrow(() -> this.openCDXShippingService.listOrders(listOrdersRequest));
    }

    @Test
    void listOrders_SortDesc() {
        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(Pagination.newBuilder()
                        .setPageNumber(1)
                        .setPageSize(10)
                        .setSortAscending(false)
                        .setSort("id")
                        .build())
                .build();

        Assertions.assertDoesNotThrow(() -> this.openCDXShippingService.listOrders(listOrdersRequest));
    }

    @Test
    void listOrders_None() {
        Mockito.when(this.openCDXOrderRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList(), PageRequest.of(1, 10), 1));

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        Assertions.assertDoesNotThrow(() -> this.openCDXShippingService.listOrders(listOrdersRequest));
    }

    @Test
    void listOrders_PatientId() {
        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertDoesNotThrow(() -> this.openCDXShippingService.listOrders(listOrdersRequest));
    }

    @Test
    void createOrder_OpenCDXNotAcceptable() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXOrderModel.class)))
                .thenThrow(JsonProcessingException.class);
        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository, openCDXCurrentUser, openCDXAuditService, mapper, openCDXProfileRepository);

        CreateOrderRequest createOrderRequest = CreateOrderRequest.newBuilder()
                .setOrder(Order.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setShippingAddress(Address.newBuilder()
                                .setAddress1("1001 Main St")
                                .setState("CA")
                                .setCity("San Francisco")
                                .setPostalCode("94105")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .setTestCaseId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXShippingService.createOrder(createOrderRequest));
    }

    @Test
    void getOrder_OpenCDXNotAcceptable() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXOrderModel.class)))
                .thenThrow(JsonProcessingException.class);
        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository, openCDXCurrentUser, openCDXAuditService, mapper, openCDXProfileRepository);

        GetOrderRequest getOrderRequest = GetOrderRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXShippingService.getOrder(getOrderRequest));
    }

    @Test
    void updateOrder_OpenCDXNotAcceptable() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXOrderModel.class)))
                .thenThrow(JsonProcessingException.class);
        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository, openCDXCurrentUser, openCDXAuditService, mapper, openCDXProfileRepository);

        UpdateOrderRequest updateOrderRequest = UpdateOrderRequest.newBuilder()
                .setOrder(Order.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setShippingAddress(Address.newBuilder()
                                .setAddress1("1001 Main St")
                                .setState("CA")
                                .setCity("San Francisco")
                                .setPostalCode("94105")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .setTestCaseId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXShippingService.updateOrder(updateOrderRequest));
    }

    @Test
    void cancelOrder_OpenCDXNotAcceptable() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXOrderModel.class)))
                .thenThrow(JsonProcessingException.class);
        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository, openCDXCurrentUser, openCDXAuditService, mapper, openCDXProfileRepository);

        CancelOrderRequest cancelOrderRequest = CancelOrderRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXShippingService.cancelOrder(cancelOrderRequest));
    }

    @Test
    void listOrders_OpenCDXNotAcceptable() throws JsonProcessingException {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any(OpenCDXOrderModel.class)))
                .thenThrow(JsonProcessingException.class);
        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository, openCDXCurrentUser, openCDXAuditService, mapper, openCDXProfileRepository);

        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXShippingService.listOrders(listOrdersRequest));
    }

    @Test
    void createOrder_OpenCDXNotFound() throws JsonProcessingException {
        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        CreateOrderRequest createOrderRequest = CreateOrderRequest.newBuilder()
                .setOrder(Order.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setShippingAddress(Address.newBuilder()
                                .setAddress1("1001 Main St")
                                .setState("CA")
                                .setCity("San Francisco")
                                .setPostalCode("94105")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .setTestCaseId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXShippingService.createOrder(createOrderRequest));
    }

    @Test
    void getOrder_OpenCDXNotFound() throws JsonProcessingException {
        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        GetOrderRequest getOrderRequest = GetOrderRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXShippingService.getOrder(getOrderRequest));
    }

    @Test
    void updateOrder_OpenCDXNotFound() throws JsonProcessingException {
        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        UpdateOrderRequest updateOrderRequest = UpdateOrderRequest.newBuilder()
                .setOrder(Order.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setShippingAddress(Address.newBuilder()
                                .setAddress1("1001 Main St")
                                .setState("CA")
                                .setCity("San Francisco")
                                .setPostalCode("94105")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .setTestCaseId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXShippingService.updateOrder(updateOrderRequest));
    }

    @Test
    void cancelOrder_OpenCDXNotFound() throws JsonProcessingException {
        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        CancelOrderRequest cancelOrderRequest = CancelOrderRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXShippingService.cancelOrder(cancelOrderRequest));
    }

    @Test
    void listOrders_OpenCDXNotFound() throws JsonProcessingException {
        this.openCDXProfileRepository = Mockito.mock(OpenCDXProfileRepository.class);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .setPatientId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXShippingService.listOrders(listOrdersRequest));
    }

    @Test
    void getOrder_OpenCDXNotFound_2() throws JsonProcessingException {
        this.openCDXOrderRepository = Mockito.mock(OpenCDXOrderRepository.class);
        Mockito.when(this.openCDXOrderRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        GetOrderRequest getOrderRequest = GetOrderRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXShippingService.getOrder(getOrderRequest));
    }

    @Test
    void updateOrder_OpenCDXNotFound_2() throws JsonProcessingException {
        this.openCDXOrderRepository = Mockito.mock(OpenCDXOrderRepository.class);
        Mockito.when(this.openCDXOrderRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        UpdateOrderRequest updateOrderRequest = UpdateOrderRequest.newBuilder()
                .setOrder(Order.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setShippingAddress(Address.newBuilder()
                                .setAddress1("1001 Main St")
                                .setState("CA")
                                .setCity("San Francisco")
                                .setPostalCode("94105")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .build())
                        .setTestCaseId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXShippingService.updateOrder(updateOrderRequest));
    }

    @Test
    void cancelOrder_OpenCDXNotFound_2() throws JsonProcessingException {
        this.openCDXOrderRepository = Mockito.mock(OpenCDXOrderRepository.class);
        Mockito.when(this.openCDXOrderRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());

        this.openCDXShippingService = new OpenCDXShippingServiceImpl(
                openCDXOrderRepository,
                openCDXCurrentUser,
                openCDXAuditService,
                objectMapper,
                openCDXProfileRepository);

        CancelOrderRequest cancelOrderRequest = CancelOrderRequest.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .build();

        Assertions.assertThrows(
                OpenCDXNotFound.class, () -> this.openCDXShippingService.cancelOrder(cancelOrderRequest));
    }
}
