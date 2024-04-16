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

import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCommunicationService;
import cdx.opencdx.grpc.data.DeliveryTracking;
import cdx.opencdx.grpc.data.Order;
import cdx.opencdx.grpc.service.logistics.DeliveryTrackingRequest;
import cdx.opencdx.grpc.service.logistics.ShippingRequest;
import cdx.opencdx.logistics.repository.OpenCDXShippingRepository;
import cdx.opencdx.logistics.service.OpenCDXShippingVendorService;
import cdx.opencdx.logistics.service.impl.OpenCDXShippingVendorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestShippingVendorControllerTest {

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    OpenCDXRestShippingVendorController openCDXRestShippingVendorController;

    @MockBean
    OpenCDXShippingVendorService openCDXShippingVendorService;

    @Autowired
    OpenCDXCommunicationService openCDXCommunicationService;

    @MockBean
    OpenCDXShippingRepository openCDXShippingRepository;

    @MockBean
    OpenCDXProfileRepository openCDXProfileRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void beforeEach() {
        this.openCDXShippingVendorService = new OpenCDXShippingVendorServiceImpl(
                openCDXShippingRepository, openCDXCommunicationService, openCDXProfileRepository);
        this.openCDXRestShippingVendorController =
                new OpenCDXRestShippingVendorController(openCDXShippingVendorService);

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXProfileRepository, this.openCDXShippingRepository);
    }

    @Test
    void getShippingVendors() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/vendors")
                        .content(this.objectMapper.writeValueAsString(ShippingRequest.newBuilder()
                                .setPackageDetails(Order.newBuilder().build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                //                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //                .andExpect(jsonPath("$.deliveryTracking.trackingId").value("789"))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void shipPackage() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/ship")
                        .content(this.objectMapper.writeValueAsString(ShippingRequest.newBuilder()
                                .setPackageDetails(Order.newBuilder().build())))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                //                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //                .andExpect(jsonPath("$.deliveryTracking.trackingId").value("789"))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void testCreateDeliveryTracking() throws Exception {
        //                OpenCDXShippingModel shippingModel = Mockito.mock(OpenCDXShippingModel.class);
        //                Order order = Mockito.mock(Order.class);
        //                Mockito.when(shippingModel.getPackageDetails()).thenReturn(order);
        //                Mockito.when(order.getTestCaseId()).thenReturn("testCaseId");
        //                Mockito.when(shippingModel.getTrackingNumber()).thenReturn("trackingNumber");
        //                Mockito.when(order.getPatientId()).thenReturn("60f1e6b1f075a911a94d3762");
        //                Mockito.when(this.openCDXShippingRepository.findById(new
        // OpenCDXIdentifier("60f1e6b1f075a901a94d3762")))
        //                        .thenAnswer(new Answer<Optional<OpenCDXShippingModel>>() {
        //                            @Override
        //                            public Optional<OpenCDXShippingModel> answer(InvocationOnMock invocation) throws
        // Throwable
        //         {
        //                                OpenCDXIdentifier argument = invocation.getArgument(0);
        //                                return Optional.of(OpenCDXShippingModel.builder()
        //                                        .id(argument)
        //                                        .trackingNumber("trackNumber")
        //                                        .packageDetails(Order.newBuilder().setTestCaseId("testCase").build())
        //                                        .build());
        //                            }
        //                        });
        //                Mockito.when(this.openCDXShippingRepository.findById(new
        // OpenCDXIdentifier("60f1e6b1f075a901a94d3762")))
        //                        .thenReturn(Optional.of(shippingModel));
        //        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
        //                .thenReturn(Optional.ofNullable(OpenCDXProfileModel.builder()
        //                        .id(OpenCDXIdentifier.get())
        //                        .fullName(FullName.newBuilder()
        //                                .setFirstName("first")
        //                                .setLastName("last")
        //                                .build())
        //                        .addresses(List.of(Address.newBuilder()
        //                                .setAddress1("address1")
        //                                .setAddress2("address2")
        //                                .setAddress3("address3")
        //                                .setCity("address3")
        //                                .setPostalCode("address4")
        //                                .setState("address5")
        //                                .setCountryId("countryId")
        //                                .build()))
        //                        .primaryContactInfo(ContactInfo.newBuilder()
        //                                .addAllEmails(List.of(EmailAddress.newBuilder()
        //                                        .setType(EmailType.EMAIL_TYPE_WORK)
        //                                        .setEmail("ab@safehealth.me")
        //                                        .build()))
        //                                .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
        //                                        .setType(PhoneType.PHONE_TYPE_MOBILE)
        //                                        .setNumber("1234567890")
        //                                        .build()))
        //                                .build())
        //                        .build()));
        //        DeliveryTrackingRequest deliveryTrackingRequest = DeliveryTrackingRequest.newBuilder()
        //                .setDeliveryTracking(DeliveryTracking.newBuilder()
        //                        .setTrackingId("60f1e6b1f075a901a94d3762")
        //                        .build())
        //                .build();
        MvcResult result = this.mockMvc
                .perform(post("/deliveryTracking")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(DeliveryTrackingRequest.newBuilder()
                                .setDeliveryTracking(DeliveryTracking.newBuilder())
                                .build())))
                //                .andExpect(status().isOk())
                //                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //
                // .andExpect(jsonPath("$.deliveryTracking.trackingId").value("60f1e6b1f075a901a94d3762"))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    // Test cases for getDeliveryTracking
    @Test
    void testGetDeliveryTrackingValid() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/deliveryTracking/789").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                //                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //                .andExpect(jsonPath("$.deliveryTracking.trackingId").value("789"))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
}
