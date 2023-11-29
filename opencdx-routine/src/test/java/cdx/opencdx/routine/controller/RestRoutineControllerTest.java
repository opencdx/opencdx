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
package cdx.opencdx.routine.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXIAMUserRepository;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.profile.ContactInfo;
import cdx.opencdx.grpc.profile.FullName;
import cdx.opencdx.grpc.profile.PhoneNumber;
import cdx.opencdx.grpc.profile.PhoneType;
import cdx.opencdx.grpc.routine.Routine;
import cdx.opencdx.grpc.routine.RoutineRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class RestRoutineControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    Connection connection;

    @MockBean
    OpenCDXCurrentUser openCDXCurrentUser;

    @MockBean
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    @BeforeEach
    public void setup() {
        Mockito.when(this.openCDXIAMUserRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXIAMUserModel>>() {
                    @Override
                    public Optional<OpenCDXIAMUserModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXIAMUserModel.builder()
                                .id(argument)
                                .password("{noop}pass")
                                .fullName(FullName.newBuilder()
                                        .setFirstName("bob")
                                        .setLastName("bob")
                                        .build())
                                .username("ab@safehealth.me")
                                .primaryContactInfo(ContactInfo.newBuilder()
                                        .setEmail("ab@safehealth.me")
                                        .setMobileNumber(PhoneNumber.newBuilder()
                                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                                .setNumber("1234567890")
                                                .build())
                                        .build())
                                .emailVerified(true)
                                .build());
                    }
                });
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.connection);
    }

    @Test
    void checkMockMvc() throws Exception { // Assertions.assertNotNull(greetingController);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void testGetRoutine() throws Exception {
        this.mockMvc
                .perform(get("/routine/2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.routine.routineId").value("2"));
    }

    @Test
    void testCreateRoutine() throws Exception {
        this.mockMvc
                .perform(post("/routine")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(RoutineRequest.newBuilder()
                                .setRoutine(
                                        Routine.newBuilder().setRoutineId("2").build())
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.routine.routineId").value("2"));
    }
}
