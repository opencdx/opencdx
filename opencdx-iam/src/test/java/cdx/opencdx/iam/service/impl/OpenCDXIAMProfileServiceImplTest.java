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
package cdx.opencdx.iam.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXNationalHealthIdentifier;
import cdx.opencdx.grpc.common.*;
import cdx.opencdx.grpc.profile.*;
import cdx.opencdx.iam.config.AppProperties;
import cdx.opencdx.iam.service.OpenCDXIAMProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXIAMProfileServiceImplTest {

    OpenCDXIAMProfileService openCDXIAMProfileService;

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    OpenCDXNationalHealthIdentifier openCDXNationalHealthIdentifier;

    @Autowired
    OpenCDXDocumentValidator openCDXDocumentValidator;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OpenCDXCurrentUser openCDXCurrentUser;

    @Mock
    ObjectMapper objectMapper1;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppProperties appProperties;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

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

        Mockito.when(this.openCDXProfileRepository.save(Mockito.any(OpenCDXProfileModel.class)))
                .thenAnswer(new Answer<OpenCDXProfileModel>() {
                    @Override
                    public OpenCDXProfileModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXProfileModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder().id(ObjectId.get()).build());
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.objectMapper);
    }

    @Test
    void getUserProfile_1() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        UserProfileRequest request = UserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.getUserProfile(request));
    }

    @Test
    void getUserProfile_2() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        UserProfileRequest request = UserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.getUserProfile(request));
    }

    @Test
    void updateUserProfile_1() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        UpdateUserProfileRequest request = UpdateUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.updateUserProfile(request));
    }

    @Test
    void updateUserProfile_2() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        UpdateUserProfileRequest request = UpdateUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.updateUserProfile(request));
    }

    @Test
    void deleteUserProfile_1() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        DeleteUserProfileRequest request = DeleteUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.deleteUserProfile(request));
    }

    @Test
    void deleteUserProfile_2() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        DeleteUserProfileRequest request = DeleteUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.deleteUserProfile(request));
    }

    @Test
    void updateUserProfile_3() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{}");

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);

        UserProfile.Builder builder = UserProfile.newBuilder();
        builder.setFullName(
                FullName.newBuilder().setFirstName("bob").setLastName("bob").build());
        builder.addAllAddress(List.of(
                Address.newBuilder().setCountryId(ObjectId.get().toHexString()).build(),
                Address.newBuilder().setCountryId(ObjectId.get().toHexString()).build(),
                Address.newBuilder().setCountryId(ObjectId.get().toHexString()).build()));
        builder.setEmergencyContact(EmergencyContact.newBuilder()
                .setContactInfo(ContactInfo.newBuilder()
                        .setUserId(ObjectId.get().toHexString())
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCity("City")
                                .setCountryId(ObjectId.get().toHexString())
                                .setState("CA")
                                .setPostalCode("12345")
                                .setAddress1("101 Main Street")
                                .build()))
                        .addAllEmails(List.of(EmailAddress.newBuilder()
                                .setEmail("email@email.com")
                                .setType(EmailType.EMAIL_TYPE_WORK)
                                .build()))
                        .addAllPhoneNumbers(List.of(PhoneNumber.newBuilder()
                                .setNumber("1234567890")
                                .setType(PhoneType.PHONE_TYPE_MOBILE)
                                .build()))
                        .build())
                .build());
        builder.setPharmacyDetails(Pharmacy.newBuilder()
                .setPharmacyAddress(Address.newBuilder()
                        .setCountryId(ObjectId.get().toHexString())
                        .build())
                .build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(ObjectId.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(ObjectId.get().toHexString())
                .setWorkspaceId(ObjectId.get().toHexString())
                .build());

        UpdateUserProfileRequest request = UpdateUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .setUpdatedProfile(builder.build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProfileService.updateUserProfile(request));
    }

    @Test
    void updateUserProfile_4() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{}");

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);

        UserProfile.Builder builder = UserProfile.newBuilder();
        builder.setFullName(
                FullName.newBuilder().setFirstName("bob").setLastName("bob").build());
        builder.addAllAddress(List.of(
                Address.newBuilder().setCountryId(ObjectId.get().toHexString()).build(),
                Address.newBuilder().setCountryId(ObjectId.get().toHexString()).build(),
                Address.newBuilder().setCountryId(ObjectId.get().toHexString()).build()));
        builder.setEmergencyContact(EmergencyContact.newBuilder().build());
        builder.setPharmacyDetails(Pharmacy.newBuilder().build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(ObjectId.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(ObjectId.get().toHexString())
                .setWorkspaceId(ObjectId.get().toHexString())
                .build());

        UpdateUserProfileRequest request = UpdateUserProfileRequest.newBuilder()
                .setUserId(ObjectId.get().toHexString())
                .setUpdatedProfile(builder.build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProfileService.updateUserProfile(request));
    }
}
