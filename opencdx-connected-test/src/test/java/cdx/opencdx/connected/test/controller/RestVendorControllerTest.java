/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package cdx.opencdx.connected.test.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.model.OpenCDXVendorModel;
import cdx.opencdx.connected.test.repository.OpenCDXDeviceRepository;
import cdx.opencdx.connected.test.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.connected.test.repository.OpenCDXVendorRepository;
import cdx.opencdx.grpc.inventory.Vendor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class RestVendorControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    private OpenCDXVendorRepository openCDXVendorRepository;

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

        Mockito.when(openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .thenAnswer(new Answer<OpenCDXVendorModel>() {
                    @Override
                    public OpenCDXVendorModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXVendorModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXVendorRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXVendorModel>>() {
                    @Override
                    public Optional<OpenCDXVendorModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXVendorModel.builder().id(argument).build());
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
    void getVendorById() throws Exception {
        Vendor vendor = Vendor.newBuilder(Vendor.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setVendorContact("vendorAddress")
                .build();

        MvcResult result = this.mockMvc
                .perform(get("/vendor/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addVendor() throws Exception {
        Vendor vendor = Vendor.newBuilder(Vendor.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setVendorContact("vendorAddress")
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/vendor")
                        .content(this.objectMapper.writeValueAsString(vendor))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateVendor() throws Exception {
        Vendor vendor = Vendor.newBuilder(Vendor.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setVendorContact("vendorAddress")
                .build();

        MvcResult result = this.mockMvc
                .perform(put("/vendor")
                        .content(this.objectMapper.writeValueAsString(vendor))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteVendor() throws Exception {
        Vendor vendor = Vendor.newBuilder(Vendor.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setVendorContact("vendorAddress")
                .build();

        MvcResult result = this.mockMvc
                .perform(
                        delete("/vendor/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
}
