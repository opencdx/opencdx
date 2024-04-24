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
package cdx.opencdx.health.service.impl;

import static org.mockito.ArgumentMatchers.any;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXConflict;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.commons.repository.OpenCDXProfileRepository;
import cdx.opencdx.commons.security.JwtTokenUtil;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.commons.service.OpenCDXNationalHealthIdentifier;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.service.health.CreateUserProfileRequest;
import cdx.opencdx.grpc.service.health.DeleteUserProfileRequest;
import cdx.opencdx.grpc.service.health.UpdateUserProfileRequest;
import cdx.opencdx.grpc.service.health.UserProfileRequest;
import cdx.opencdx.grpc.types.EmailType;
import cdx.opencdx.grpc.types.IamUserType;
import cdx.opencdx.grpc.types.PhoneType;
import cdx.opencdx.health.service.OpenCDXIAMProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Mock
    OpenCDXProfileRepository openCDXProfileRepository;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
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
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(argument)
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.save(Mockito.any(OpenCDXProfileModel.class)))
                .thenAnswer(new Answer<OpenCDXProfileModel>() {
                    @Override
                    public OpenCDXProfileModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXProfileModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(OpenCDXIdentifier.get());
                        }
                        return argument;
                    }
                });

        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
        Mockito.when(this.openCDXCurrentUser.getCurrentUser(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .build());
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
                .setUserId(OpenCDXIdentifier.get().toHexString())
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
                .setUserId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.getUserProfile(request));
    }

    @Test
    void getUserProfile_OpenCDXNotFound() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeleteUserProfileRequest request = DeleteUserProfileRequest.newBuilder()
                .setUserId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMProfileService.deleteUserProfile(request));
    }

    @Test
    void deleteUserProfile_OpenCDXNotFound() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        DeleteUserProfileRequest request = DeleteUserProfileRequest.newBuilder()
                .setUserId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMProfileService.deleteUserProfile(request));
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
                .setUserId(OpenCDXIdentifier.get().toHexString())
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
                .setUserId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.updateUserProfile(request));
    }

    @Test
    void createUserProfile_1() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        CreateUserProfileRequest request = CreateUserProfileRequest.newBuilder()
                .setUserProfile(UserProfile.newBuilder()
                        .setUserId(OpenCDXIdentifier.get().toHexString())
                        .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.createUserProfile(request));
    }

    @Test
    void createUserProfile_2() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        CreateUserProfileRequest request = CreateUserProfileRequest.newBuilder()
                .setUserProfile(UserProfile.newBuilder()
                        .setUserId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.createUserProfile(request));
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
                .setUserId(OpenCDXIdentifier.get().toHexString())
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
                .setUserId(OpenCDXIdentifier.get().toHexString())
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
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build()));
        builder.setEmergencyContact(EmergencyContact.newBuilder()
                .setContactInfo(ContactInfo.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCity("City")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
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
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(OpenCDXIdentifier.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build());

        UpdateUserProfileRequest request = UpdateUserProfileRequest.newBuilder()
                .setUserId(OpenCDXIdentifier.get().toHexString())
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
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build()));
        builder.setEmergencyContact(EmergencyContact.newBuilder().build());
        builder.setPharmacyDetails(Pharmacy.newBuilder().build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(OpenCDXIdentifier.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build());

        UpdateUserProfileRequest request = UpdateUserProfileRequest.newBuilder()
                .setUserId(OpenCDXIdentifier.get().toHexString())
                .setUpdatedProfile(builder.build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProfileService.updateUserProfile(request));
    }

    @Test
    void createUserProfile_3() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{}");

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);

        UserProfile.Builder builder = UserProfile.newBuilder();
        builder.setUserId(OpenCDXIdentifier.get().toHexString());
        builder.setFullName(
                FullName.newBuilder().setFirstName("bob").setLastName("bob").build());
        builder.addAllAddress(List.of(
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build()));
        builder.setEmergencyContact(EmergencyContact.newBuilder()
                .setContactInfo(ContactInfo.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCity("City")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
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
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(OpenCDXIdentifier.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build());

        CreateUserProfileRequest request = CreateUserProfileRequest.newBuilder()
                .setUserProfile(builder.build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProfileService.createUserProfile(request));
    }

    @Test
    void createUserProfile_4() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{}");

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);

        UserProfile.Builder builder = UserProfile.newBuilder();
        builder.setUserId(OpenCDXIdentifier.get().toHexString());
        builder.setNationalHealthId(OpenCDXIdentifier.get().toHexString());
        builder.setFullName(
                FullName.newBuilder().setFirstName("bob").setLastName("bob").build());
        builder.addAllAddress(List.of(
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build()));
        builder.setPharmacyDetails(Pharmacy.newBuilder()).build();
        builder.setEmergencyContact(EmergencyContact.newBuilder().build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(OpenCDXIdentifier.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build());

        CreateUserProfileRequest request = CreateUserProfileRequest.newBuilder()
                .setUserProfile(builder.build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProfileService.createUserProfile(request));
    }

    @Test
    void createUserProfile_5() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{}");

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);

        UserProfile.Builder builder = UserProfile.newBuilder();
        builder.setUserId(OpenCDXIdentifier.get().toHexString());
        builder.setNationalHealthId(OpenCDXIdentifier.get().toHexString());
        builder.setFullName(
                FullName.newBuilder().setFirstName("bob").setLastName("bob").build());
        builder.addAllAddress(List.of(
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build()));
        builder.setEmergencyContact(EmergencyContact.newBuilder().build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(OpenCDXIdentifier.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build());

        Mockito.when(this.openCDXProfileRepository.findByUserId(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(OpenCDXIdentifier.get())
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });

        Mockito.when(this.openCDXProfileRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXProfileRepository.existsByNationalHealthId(Mockito.any(String.class)))
                .thenReturn(true);
        CreateUserProfileRequest request = CreateUserProfileRequest.newBuilder()
                .setUserProfile(builder.build())
                .build();
        Assertions.assertThrows(OpenCDXConflict.class, () -> this.openCDXIAMProfileService.createUserProfile(request));
    }

    @Test
    void createUserProfile_6() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{}");

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);

        UserProfile.Builder builder = UserProfile.newBuilder();
        builder.setUserId(OpenCDXIdentifier.get().toHexString());
        builder.setNationalHealthId(OpenCDXIdentifier.get().toHexString());
        builder.setFullName(
                FullName.newBuilder().setFirstName("bob").setLastName("bob").build());
        builder.addAllAddress(List.of(
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build()));
        builder.setEmergencyContact(EmergencyContact.newBuilder().build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(OpenCDXIdentifier.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build());

        Mockito.when(this.openCDXProfileRepository.existsById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(true);
        Mockito.when(this.openCDXProfileRepository.existsByNationalHealthId(Mockito.any(String.class)))
                .thenReturn(false);
        CreateUserProfileRequest request = CreateUserProfileRequest.newBuilder()
                .setUserProfile(builder.build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProfileService.createUserProfile(request));
    }

    @Test
    void getUserProfile_OpenCDXNotFound_notFound() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        Mockito.when(this.openCDXProfileRepository.findById(Mockito.any(OpenCDXIdentifier.class)))
                .thenReturn(Optional.empty());
        UserProfileRequest request = UserProfileRequest.newBuilder()
                .setUserId(OpenCDXIdentifier.get().toHexString())
                .build();
        Assertions.assertThrows(OpenCDXNotFound.class, () -> this.openCDXIAMProfileService.getUserProfile(request));
    }

    @Test
    void createUserProfile_7() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                        .build());
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);
        CreateUserProfileRequest request = CreateUserProfileRequest.newBuilder()
                .setUserProfile(UserProfile.newBuilder()
                        .setUserId(OpenCDXIdentifier.get().toHexString())
                        .setNationalHealthId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build();
        Assertions.assertThrows(
                OpenCDXNotAcceptable.class, () -> this.openCDXIAMProfileService.createUserProfile(request));
    }

    @Test
    void createUserProfile_8() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.openCDXProfileRepository.findByUserId(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                        .build());
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{}");

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);

        UserProfile.Builder builder = UserProfile.newBuilder();
        builder.setUserId(OpenCDXIdentifier.get().toHexString());
        builder.setFullName(
                FullName.newBuilder().setFirstName("bob").setLastName("bob").build());
        builder.addAllAddress(List.of(
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build()));
        builder.setEmergencyContact(EmergencyContact.newBuilder()
                .setContactInfo(ContactInfo.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCity("City")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
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
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(OpenCDXIdentifier.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build());

        CreateUserProfileRequest request = CreateUserProfileRequest.newBuilder()
                .setUserProfile(builder.build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProfileService.createUserProfile(request));
    }

    @Test
    void createUserProfile_9() throws JsonProcessingException {
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(this.openCDXProfileRepository.findByUserId(Mockito.any(OpenCDXIdentifier.class)))
                .thenAnswer(new Answer<Optional<OpenCDXProfileModel>>() {
                    @Override
                    public Optional<OpenCDXProfileModel> answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIdentifier argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXProfileModel.builder()
                                .id(argument)
                                .nationalHealthId(UUID.randomUUID().toString())
                                .userId(OpenCDXIdentifier.get())
                                .build());
                    }
                });
        Mockito.when(this.openCDXCurrentUser.getCurrentUser())
                .thenReturn(OpenCDXIAMUserModel.builder()
                        .id(OpenCDXIdentifier.get())
                        .type(IamUserType.IAM_USER_TYPE_SYSTEM)
                        .build());
        Mockito.when(this.objectMapper.writeValueAsString(Mockito.any())).thenReturn("{}");

        this.openCDXIAMProfileService = new OpenCDXIAMProfileServiceImpl(
                this.objectMapper,
                this.openCDXAuditService,
                this.openCDXProfileRepository,
                this.openCDXCurrentUser,
                this.openCDXDocumentValidator);

        UserProfile.Builder builder = UserProfile.newBuilder();
        builder.setUserId(OpenCDXIdentifier.get().toHexString());
        builder.setFullName(
                FullName.newBuilder().setFirstName("bob").setLastName("bob").build());
        builder.addAllAddress(List.of(
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build(),
                Address.newBuilder()
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build()));
        builder.setEmergencyContact(EmergencyContact.newBuilder()
                .setContactInfo(ContactInfo.newBuilder()
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .addAllAddresses(List.of(Address.newBuilder()
                                .setCity("City")
                                .setCountryId(OpenCDXIdentifier.get().toHexString())
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
                        .setCountryId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .build());
        builder.setPlaceOfBirth(PlaceOfBirth.newBuilder()
                .setCountry(OpenCDXIdentifier.get().toHexString())
                .build());
        builder.setEmployeeIdentity(EmployeeIdentity.newBuilder()
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build());

        CreateUserProfileRequest request = CreateUserProfileRequest.newBuilder()
                .setUserProfile(UserProfile.newBuilder().build())
                .build();
        Assertions.assertDoesNotThrow(() -> this.openCDXIAMProfileService.createUserProfile(request));
    }
}
