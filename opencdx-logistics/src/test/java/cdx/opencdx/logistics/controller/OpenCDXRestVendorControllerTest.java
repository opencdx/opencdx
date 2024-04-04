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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.inventory.Vendor;
import cdx.opencdx.grpc.inventory.VendorsListRequest;
import cdx.opencdx.logistics.model.OpenCDXVendorModel;
import cdx.opencdx.logistics.repository.OpenCDXDeviceRepository;
import cdx.opencdx.logistics.repository.OpenCDXTestCaseRepository;
import cdx.opencdx.logistics.repository.OpenCDXVendorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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
class OpenCDXRestVendorControllerTest {

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
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());

        Mockito.when(openCDXVendorRepository.save(Mockito.any(OpenCDXVendorModel.class)))
                .thenAnswer(new Answer<OpenCDXVendorModel>() {
                    @Override
                    public OpenCDXVendorModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXVendorModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXVendorRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXVendorModel>>() {
                    @Override
                    public Optional<OpenCDXVendorModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXVendorModel.builder().id(argument).build());
                    }
                });
        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerId(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(false);
        Mockito.when(this.openCDXTestCaseRepository.existsByManufacturerId(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(false);
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getVendorById() throws Exception {
        Vendor vendor = Vendor.newBuilder(Vendor.getDefaultInstance())
                .setId(OpenCDXIdentifier.get().toHexString())
                .setVendorContact(ContactInfo.newBuilder()
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .setAddress1("Test 1")
                                .build()))
                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_WORK)
                                .setNumber("1234567890")
                                .build()))
                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                .setType(EmailType.EMAIL_TYPE_WORK)
                                .setEmail("ab@safehealth.me")
                                .build()))
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(get("/vendor/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addVendor() throws Exception {
        Vendor vendor = Vendor.newBuilder(Vendor.getDefaultInstance())
                .setId(OpenCDXIdentifier.get().toHexString())
                .setVendorContact(ContactInfo.newBuilder()
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .setAddress1("Test 1")
                                .build()))
                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_WORK)
                                .setNumber("1234567890")
                                .build()))
                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                .setType(EmailType.EMAIL_TYPE_WORK)
                                .setEmail("ab@safehealth.me")
                                .build()))
                        .build())
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
                .setId(OpenCDXIdentifier.get().toHexString())
                .setVendorContact(ContactInfo.newBuilder()
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .setAddress1("Test 1")
                                .build()))
                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_WORK)
                                .setNumber("1234567890")
                                .build()))
                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                .setType(EmailType.EMAIL_TYPE_WORK)
                                .setEmail("ab@safehealth.me")
                                .build()))
                        .build())
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
                .setId(OpenCDXIdentifier.get().toHexString())
                .setVendorContact(ContactInfo.newBuilder()
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
                                .setAddress1("Test 1")
                                .build()))
                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                .setType(PhoneType.PHONE_TYPE_WORK)
                                .setNumber("1234567890")
                                .build()))
                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                .setType(EmailType.EMAIL_TYPE_WORK)
                                .setEmail("ab@safehealth.me")
                                .build()))
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(delete("/vendor/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void listVendors() throws Exception {
        Mockito.when(this.openCDXVendorRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXVendorModel.builder().name("USA").build()), PageRequest.of(1, 10), 1));
        MvcResult result = this.mockMvc
                .perform(post("/vendor/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(VendorsListRequest.newBuilder()
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
    void listVendorsElse() throws Exception {
        Mockito.when(this.openCDXVendorRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXVendorModel.builder().name("USA").build()), PageRequest.of(1, 10), 1));
        MvcResult result2 = this.mockMvc
                .perform(post("/vendor/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(VendorsListRequest.newBuilder()
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
