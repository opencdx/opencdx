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
package cdx.opencdx.health.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.model.OpenCDXCountryModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.grpc.common.AddressPurpose;
import cdx.opencdx.health.dto.npi.OpenCDXDtoNpiAddress;
import cdx.opencdx.health.dto.npi.OpenCDXDtoNpiBasicInfo;
import cdx.opencdx.health.dto.npi.OpenCDXDtoNpiIdentifier;
import cdx.opencdx.health.dto.npi.OpenCDXDtoNpiResult;
import com.google.protobuf.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class OpenCDXIAMProviderModelTest {

    OpenCDXIAMProviderModel model;

    @Mock
    OpenCDXDtoNpiResult result;

    @Mock
    OpenCDXDtoNpiAddress address;

    @Mock
    OpenCDXCountryRepository openCDXCountryRepository;

    @Test
    void modelTest() {
        when(this.openCDXCountryRepository.findByName(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXCountryModel>>() {
                    @Override
                    public Optional<OpenCDXCountryModel> answer(InvocationOnMock invocation) throws Throwable {
                        return Optional.of(OpenCDXCountryModel.builder()
                                .id(ObjectId.get())
                                .name("")
                                .build());
                    }
                });
        when(address.getCountryName()).thenReturn("India");
        when(address.getAddressPurpose()).thenReturn(String.valueOf(AddressPurpose.LOCATION));
        when(address.getAddress1()).thenReturn("address1");
        when(address.getCity()).thenReturn("city");
        when(address.getState()).thenReturn("state");
        when(address.getPostalCode()).thenReturn("post");
        OpenCDXDtoNpiBasicInfo basicInfo = mock(OpenCDXDtoNpiBasicInfo.class);
        when(basicInfo.getFirstName()).thenReturn("fname");
        when(basicInfo.getLastName()).thenReturn("lname");
        when(basicInfo.getCredential()).thenReturn("cre");
        when(basicInfo.getSoleProprietor()).thenReturn("sole");
        when(basicInfo.getGender()).thenReturn("male");
        when(basicInfo.getEnumerationDate()).thenReturn("enum");
        when(basicInfo.getNamePrefix()).thenReturn("pre");
        when(basicInfo.getNameSuffix()).thenReturn("suff");
        when(result.getBasic()).thenReturn(basicInfo);
        when(result.getAddresses()).thenReturn(Arrays.asList(address));
        OpenCDXDtoNpiIdentifier openCDXDtoNpiIdentifier = mock(OpenCDXDtoNpiIdentifier.class);
        List<OpenCDXDtoNpiIdentifier> list = new ArrayList<>();
        when(openCDXDtoNpiIdentifier.getCode()).thenReturn("code");
        when(openCDXDtoNpiIdentifier.getDesc()).thenReturn("desc");
        when(openCDXDtoNpiIdentifier.getIssuer()).thenReturn("issuer");
        when(openCDXDtoNpiIdentifier.getIdentifier()).thenReturn("iden");
        when(openCDXDtoNpiIdentifier.getState()).thenReturn("state");
        list.add(openCDXDtoNpiIdentifier);
        when(result.getIdentifiers()).thenReturn(list);
        OpenCDXIAMProviderModel providerModel = new OpenCDXIAMProviderModel(result, openCDXCountryRepository);

        ReflectionTestUtils.setField(providerModel, "userId", "USERID");

        ReflectionTestUtils.setField(
                providerModel,
                "created",
                Timestamp.newBuilder().setSeconds(1696732104).build());
        ReflectionTestUtils.setField(
                providerModel,
                "modified",
                Timestamp.newBuilder().setSeconds(1696732104).build());
        ReflectionTestUtils.setField(providerModel, "creator", "creator");
        ReflectionTestUtils.setField(providerModel, "modifier", "modifier");
        Assertions.assertNotNull(providerModel.getProtobufMessage());
    }

    @Test
    void modelTest_2() {
        when(this.openCDXCountryRepository.findByName(Mockito.any(String.class)))
                .thenAnswer(new Answer<Optional<OpenCDXCountryModel>>() {
                    @Override
                    public Optional<OpenCDXCountryModel> answer(InvocationOnMock invocation) throws Throwable {
                        return Optional.empty();
                    }
                });
        when(address.getCountryName()).thenReturn("Bob");
        when(address.getAddressPurpose()).thenReturn(String.valueOf(AddressPurpose.LOCATION));
        when(address.getAddress1()).thenReturn("address1");
        when(address.getCity()).thenReturn("city");
        when(address.getState()).thenReturn("state");
        when(address.getPostalCode()).thenReturn("post");
        OpenCDXDtoNpiBasicInfo basicInfo = mock(OpenCDXDtoNpiBasicInfo.class);
        when(basicInfo.getFirstName()).thenReturn("fname");
        when(basicInfo.getLastName()).thenReturn("lname");
        when(basicInfo.getCredential()).thenReturn("cre");
        when(basicInfo.getSoleProprietor()).thenReturn("sole");
        when(basicInfo.getGender()).thenReturn("male");
        when(basicInfo.getEnumerationDate()).thenReturn("enum");
        when(basicInfo.getNamePrefix()).thenReturn("pre");
        when(basicInfo.getNameSuffix()).thenReturn("suff");
        when(result.getBasic()).thenReturn(basicInfo);
        when(result.getAddresses()).thenReturn(Arrays.asList(address));
        OpenCDXDtoNpiIdentifier openCDXDtoNpiIdentifier = mock(OpenCDXDtoNpiIdentifier.class);
        List<OpenCDXDtoNpiIdentifier> list = new ArrayList<>();
        when(openCDXDtoNpiIdentifier.getCode()).thenReturn("code");
        when(openCDXDtoNpiIdentifier.getDesc()).thenReturn("desc");
        when(openCDXDtoNpiIdentifier.getIssuer()).thenReturn("issuer");
        when(openCDXDtoNpiIdentifier.getIdentifier()).thenReturn("iden");
        when(openCDXDtoNpiIdentifier.getState()).thenReturn("state");
        list.add(openCDXDtoNpiIdentifier);
        when(result.getIdentifiers()).thenReturn(list);
        OpenCDXIAMProviderModel providerModel = new OpenCDXIAMProviderModel(result, openCDXCountryRepository);

        ReflectionTestUtils.setField(providerModel, "userId", "USERID");

        ReflectionTestUtils.setField(
                providerModel,
                "created",
                Timestamp.newBuilder().setSeconds(1696732104).build());
        ReflectionTestUtils.setField(
                providerModel,
                "modified",
                Timestamp.newBuilder().setSeconds(1696732104).build());
        ReflectionTestUtils.setField(providerModel, "creator", "creator");
        ReflectionTestUtils.setField(providerModel, "modifier", "modifier");
        Assertions.assertNotNull(providerModel.getProtobufMessage());
    }
}
