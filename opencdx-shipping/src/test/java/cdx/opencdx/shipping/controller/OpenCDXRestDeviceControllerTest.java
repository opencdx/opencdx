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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.inventory.Device;
import cdx.opencdx.grpc.inventory.DeviceListRequest;
import cdx.opencdx.shipping.model.OpenCDXDeviceModel;
import cdx.opencdx.shipping.repository.OpenCDXDeviceRepository;
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
class OpenCDXRestDeviceControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    private OpenCDXDeviceRepository openCDXDeviceRepository;

    @BeforeEach
    public void setup() {
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        Mockito.when(openCDXDeviceRepository.save(Mockito.any(OpenCDXDeviceModel.class)))
                .thenAnswer(new Answer<OpenCDXDeviceModel>() {
                    @Override
                    public OpenCDXDeviceModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXDeviceModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXDeviceRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXDeviceModel>>() {
                    @Override
                    public Optional<OpenCDXDeviceModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXDeviceModel.builder().id(argument).build());
                    }
                });
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getDeviceById() throws Exception {
        Device device = Device.newBuilder(Device.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setDeviceStatus("deviceStatus")
                .build();

        MvcResult result = this.mockMvc
                .perform(get("/device/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addDevice() throws Exception {
        Device device = Device.newBuilder(Device.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setDeviceStatus("deviceStatus")
                .setManufacturerId(ObjectId.get().toHexString())
                .setManufacturerCountryId(ObjectId.get().toHexString())
                .setVendorCountryId(ObjectId.get().toHexString())
                .setVendorId(ObjectId.get().toHexString())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/device")
                        .content(this.objectMapper.writeValueAsString(device))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateDevice() throws Exception {
        Device device = Device.newBuilder(Device.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setManufacturerId(ObjectId.get().toHexString())
                .setVendorId(ObjectId.get().toHexString())
                .setManufacturerCountryId(ObjectId.get().toHexString())
                .setVendorCountryId(ObjectId.get().toHexString())
                .setDeviceStatus("deviceStatus")
                .build();

        MvcResult result = this.mockMvc
                .perform(put("/device")
                        .content(this.objectMapper.writeValueAsString(device))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteDevice() throws Exception {
        Device device = Device.newBuilder(Device.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setDeviceStatus("deviceStatus")
                .build();

        MvcResult result = this.mockMvc
                .perform(
                        delete("/device/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void listDevices() throws Exception {
        Mockito.when(this.openCDXDeviceRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDeviceModel.builder()
                                .manufacturerId(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        MvcResult result2 = this.mockMvc
                .perform(post("/device/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeviceListRequest.newBuilder()
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

    @Test
    void listDevicesElse() throws Exception {
        Mockito.when(this.openCDXDeviceRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXDeviceModel.builder()
                                .manufacturerId(ObjectId.get())
                                .build()),
                        PageRequest.of(1, 10),
                        1));
        MvcResult result2 = this.mockMvc
                .perform(post("/device/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeviceListRequest.newBuilder()
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
        String content2 = result2.getResponse().getContentAsString();
        log.info("Received\n {}", content2);
    }
}
