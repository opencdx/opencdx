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
import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.data.Country;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.logistics.CountryListRequest;
import cdx.opencdx.logistics.repository.OpenCDXDeviceRepository;
import cdx.opencdx.logistics.repository.OpenCDXManufacturerRepository;
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
class OpenCDXRestCountryControllerTest {
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

    private MockMvc mockMvc;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

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

        Mockito.when(openCDXCountryRepository.save(Mockito.any(OpenCDXCountryModel.class)))
                .thenAnswer(new Answer<OpenCDXCountryModel>() {
                    @Override
                    public OpenCDXCountryModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXCountryModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXCountryRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXCountryModel>>() {
                    @Override
                    public Optional<OpenCDXCountryModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXCountryModel.builder()
                                .id(argument)
                                .name("USA")
                                .build());
                    }
                });
        Mockito.when(this.openCDXManufacturerRepository.existsByAddress_CountryId(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(false);
        Mockito.when(this.openCDXVendorRepository.existsByAddress_CountryId(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(false);
        Mockito.when(this.openCDXDeviceRepository.existsByVendorCountryId(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(false);
        Mockito.when(this.openCDXDeviceRepository.existsByManufacturerCountryId(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(false);

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getCountryById() throws Exception {

        MvcResult result = this.mockMvc
                .perform(get("/country/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addCountry() throws Exception {
        Country country = Country.newBuilder(Country.getDefaultInstance())
                .setId(OpenCDXIdentifier.get().toHexString())
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
                .setId(OpenCDXIdentifier.get().toHexString())
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
        MvcResult result = this.mockMvc
                .perform(delete("/country/" + OpenCDXIdentifier.get().toHexString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void listCountries() throws Exception {
        Mockito.when(this.openCDXCountryRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXCountryModel.builder().name("USA").build()), PageRequest.of(1, 10), 1));
        MvcResult result = this.mockMvc
                .perform(post("/country/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(CountryListRequest.newBuilder()
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
    void listCountriesIfSortTrue() throws Exception {
        Mockito.when(this.openCDXCountryRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(OpenCDXCountryModel.builder().name("USA").build()), PageRequest.of(1, 10), 1));
        MvcResult result = this.mockMvc
                .perform(post("/country/list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(CountryListRequest.newBuilder()
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
        String content2 = result.getResponse().getContentAsString();
        log.info("Received\n {}", content2);
    }
}
