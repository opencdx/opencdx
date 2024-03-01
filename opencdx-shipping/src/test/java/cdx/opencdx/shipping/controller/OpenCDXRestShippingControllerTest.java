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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.Address;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.inventory.TestCase;
import cdx.opencdx.grpc.shipping.CreateOrderRequest;
import cdx.opencdx.grpc.shipping.ListOrdersRequest;
import cdx.opencdx.grpc.shipping.Order;
import cdx.opencdx.shipping.model.OpenCDXOrderModel;
import cdx.opencdx.shipping.repository.OpenCDXOrderRepository;
import cdx.opencdx.shipping.service.OpenCDXShippingService;
import cdx.opencdx.shipping.service.impl.OpenCDXShippingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestShippingControllerTest {
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

    @MockBean
    OpenCDXShippingService openCDXShippingService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

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

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getOrder() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/order/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void createOrder() throws Exception {
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
                        .setTestCase(TestCase.newBuilder(TestCase.getDefaultInstance())
                                .setId(ObjectId.get().toHexString())
                                .build())
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/order")
                        .content(this.objectMapper.writeValueAsString(createOrderRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateOrder() throws Exception {
        CreateOrderRequest createOrderRequest = CreateOrderRequest.newBuilder()
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
                        .setTestCase(TestCase.newBuilder(TestCase.getDefaultInstance())
                                .setId(ObjectId.get().toHexString())
                                .build())
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(put("/order")
                        .content(this.objectMapper.writeValueAsString(createOrderRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void cancelOrder() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/order/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void listOrders() throws Exception {
        ListOrdersRequest listOrdersRequest = ListOrdersRequest.newBuilder()
                .setPagination(
                        Pagination.newBuilder().setPageNumber(1).setPageSize(10).build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/order/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(listOrdersRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
    }
}
