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
package cdx.opencdx.logistics.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.EmailAddress;
import cdx.opencdx.grpc.common.EmailType;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.inventory.Manufacturer;
import cdx.opencdx.grpc.inventory.ManufacturerListRequest;
import cdx.opencdx.logistics.model.OpenCDXManufacturerModel;
import cdx.opencdx.logistics.repository.OpenCDXDeviceRepository;
import cdx.opencdx.logistics.repository.OpenCDXManufacturerRepository;
import cdx.opencdx.logistics.repository.OpenCDXTestCaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class OpenCDXRestManufacturerControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    private OpenCDXManufacturerRepository openCDXManufacturerRepository;

    @MockBean
    private OpenCDXDeviceRepository openCDXDeviceRepository;

    @MockBean
    private OpenCDXTestCaseRepository openCDXTestCaseRepository;

    @BeforeEach
    public void setup() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        Mockito.when(openCDXManufacturerRepository.save(Mockito.any(OpenCDXManufacturerModel.class)))
                .thenAnswer(new Answer<OpenCDXManufacturerModel>() {
                    @Override
                    public OpenCDXManufacturerModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXManufacturerModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXManufacturerRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXManufacturerModel>>() {
                    @Override
                    public Optional<OpenCDXManufacturerModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXManufacturerModel.builder().id(argument).build());
                    }
                });
        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerId(Mockito.any(ObjectId.class)))
                .thenReturn(false);
        Mockito.when(this.openCDXTestCaseRepository.existsByManufacturerId(Mockito.any(ObjectId.class)))
                .thenReturn(false);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getManufacturerById() throws Exception {
        Manufacturer manufacturer = Manufacturer.newBuilder(Manufacturer.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setManufacturerEmail(EmailAddress.newBuilder()
                        .setEmail("manufacturerEmail@email.com")
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(get("/manufacturer/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addVendor() throws Exception {
        Manufacturer manufacturer = Manufacturer.newBuilder(Manufacturer.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setManufacturerEmail(EmailAddress.newBuilder()
                        .setEmail("manufacturerEmail@email.com")
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/manufacturer")
                        .content(this.objectMapper.writeValueAsString(manufacturer))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateVendor() throws Exception {
        Manufacturer manufacturer = Manufacturer.newBuilder(Manufacturer.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setManufacturerEmail(EmailAddress.newBuilder()
                        .setEmail("manufacturerEmail@email.com")
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(put("/manufacturer")
                        .content(this.objectMapper.writeValueAsString(manufacturer))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteVendor() throws Exception {
        Manufacturer manufacturer = Manufacturer.newBuilder(Manufacturer.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setManufacturerEmail(EmailAddress.newBuilder()
                        .setEmail("manufacturerEmail@email.com")
                        .setType(EmailType.EMAIL_TYPE_WORK)
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(delete("/manufacturer/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void listManufacturer() throws Exception {
        Mockito.when(this.openCDXManufacturerRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXManufacturerModel.builder().name("USA").build()), PageRequest.of(1, 10), 1));
        MvcResult result = this.mockMvc
                .perform(post("/manufacturer/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ManufacturerListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSort("true")
                                        .setSortAscending(false)
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("Received\n {}", content);
    }

    @Test
    void listManufacturerElseCase() throws Exception {
        Mockito.when(this.openCDXManufacturerRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXManufacturerModel.builder().name("USA").build()), PageRequest.of(1, 10), 1));
        MvcResult result2 = this.mockMvc
                .perform(post("/manufacturer/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(ManufacturerListRequest.newBuilder()
                                .setPagination(Pagination.newBuilder()
                                        .setPageNumber(1)
                                        .setPageSize(10)
                                        .setSort("true")
                                        .setSortAscending(true)
                                        .build())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content2 = result2.getResponse().getContentAsString();
        log.info("Received\n {}", content2);
    }
}
