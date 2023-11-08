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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.test.model.OpenCDXCountryModel;
import cdx.opencdx.connected.test.repository.*;
import cdx.opencdx.grpc.inventory.Country;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Optional;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class RestCountryControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private OpenCDXVendorRepository openCDXVendorRepository;
    @MockBean
    private OpenCDXCountryRepository openCDXCountryRepository;
    @MockBean
    private OpenCDXManufacturerRepository openCDXManufacturerRepository;
    @MockBean
    private OpenCDXDeviceRepository openCDXDeviceRepository;
    @MockBean
    private OpenCDXTestCaseRepository openCDXTestCaseRepository;



    private MockMvc mockMvc;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @BeforeEach
    public void setup() {
        Mockito.when(openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .thenAnswer(new Answer<OpenCDXCountryModel>() {
                                @Override
                                public OpenCDXCountryModel answer(InvocationOnMock invocation) throws Throwable {
                                    OpenCDXCountryModel argument = invocation.getArgument(0);
                                    if (argument.getId() == null) {
                                        argument.setId(ObjectId.get());
                                    }
                                    return argument;
                                }
                            });
        Mockito.when(openCDXCountryRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXCountryModel>>() {
                    @Override
                    public Optional<OpenCDXCountryModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXCountryModel.builder()
                                .id(argument)
                                .name("USA")
                                .build());
                    }
                });
        Mockito.when(this.openCDXManufacturerRepository.existsByAddress_Country(Mockito.anyString())).thenReturn(false);
        Mockito.when(this.openCDXVendorRepository.existsByAddress_Country(Mockito.anyString())).thenReturn(false);
        Mockito.when(this.openCDXDeviceRepository.existsByVendorCountryId(Mockito.any(ObjectId.class))).thenReturn(false);
        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerCountryId(Mockito.any(ObjectId.class))).thenReturn(false);

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getCountryById() throws Exception {
        Country country = Country.newBuilder(Country.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setName("countryName")
                .build();

        MvcResult result = this.mockMvc
                .perform(get("/country/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addCountry() throws Exception {
        Country country = Country.newBuilder(Country.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setName("countryName")
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/country")
                        .content(this.objectMapper.writeValueAsString(country))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateCountry() throws Exception {
        Country country = Country.newBuilder(Country.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setName("countryName")
                .build();

        MvcResult result = this.mockMvc
                .perform(put("/country")
                        .content(this.objectMapper.writeValueAsString(country))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteCountry() throws Exception {
        Country country = Country.newBuilder(Country.getDefaultInstance())
                .setId(ObjectId.get().toHexString())
                .setName("countryName")
                .build();

        MvcResult result = this.mockMvc
                .perform(delete("/country/" + ObjectId.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
}
