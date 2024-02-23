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
package cdx.opencdx.anf.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.anf.model.OpenCDXANFStatementModel;
import cdx.opencdx.anf.repository.OpenCDXANFStatementRepository;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.anf.AnfStatement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import java.util.Optional;
import java.util.UUID;
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
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestANFControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private OpenCDXANFStatementRepository openCDXANFStatementRepository;

    @MockBean
    private OpenCDXProfileRepository openCDXProfileRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
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

        Mockito.when(this.openCDXProfileRepository.findByUserId(Mockito.any(ObjectId.class)))
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
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        Mockito.when(openCDXANFStatementRepository.save(Mockito.any(OpenCDXANFStatementModel.class)))
                .thenAnswer(new Answer<OpenCDXANFStatementModel>() {
                    @Override
                    public OpenCDXANFStatementModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXANFStatementModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(openCDXANFStatementRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXANFStatementModel>>() {
                    @Override
                    public Optional<OpenCDXANFStatementModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXANFStatementModel.builder()
                                .id(argument)
                                .subjectOfRecord(AnfStatement.Participant.newBuilder()
                                        .setPatientId(ObjectId.get().toHexString())
                                        .build())
                                .build());
                    }
                });

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @Test
    void getANFStatement() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void createANFStatement() throws Exception {
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .setCreated(Timestamp.newBuilder().setSeconds(1696732104))
                .setCreator(ObjectId.get().toHexString())
                .setModified(Timestamp.newBuilder().setSeconds(1696733104))
                .setModifier(ObjectId.get().toHexString())
                .setSubjectOfRecord(AnfStatement.Participant.newBuilder()
                        .setPatientId(ObjectId.get().toHexString())
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/")
                        .content(this.objectMapper.writeValueAsString(anfStatement))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateANFStatement() throws Exception {
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .setId(AnfStatement.Identifier.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build())
                .setSubjectOfRecord(AnfStatement.Participant.newBuilder()
                        .setPatientId(ObjectId.get().toHexString())
                        .build())
                .build();

        MvcResult result = this.mockMvc
                .perform(put("/").content(this.objectMapper.writeValueAsString(anfStatement))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteANFStatement() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
}
