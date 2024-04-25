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

import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.health.dto.npi.*;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXIAMProviderModelTest {

    @Test
    void test() {
        OpenCDXDtoNpiResult result = mock(OpenCDXDtoNpiResult.class);
        OpenCDXCountryRepository openCDXCountryRepository = mock(OpenCDXCountryRepository.class);
        OpenCDXDtoNpiAddress address = mock(OpenCDXDtoNpiAddress.class);
        when(address.getCountryCode()).thenReturn("US");
        when(result.getAddresses()).thenReturn(List.of(address));
        when(result.getBasic()).thenReturn(null);
        when(result.getEnumerationType()).thenReturn(null);
        when(result.getOtherNames()).thenReturn(null);
        when(result.getTaxonomies()).thenReturn(null);
        when(result.getAddresses()).thenReturn(List.of(address));
        when(result.getBasic()).thenReturn(null);
        when(result.getEnumerationType()).thenReturn(null);
        when(result.getOtherNames()).thenReturn(null);

        OpenCDXDtoNpiTaxonomy openCDXDtoNpiTaxonomy = mock(OpenCDXDtoNpiTaxonomy.class);
        when(openCDXDtoNpiTaxonomy.getState()).thenReturn("state");
        when(openCDXDtoNpiTaxonomy.getLicense()).thenReturn("license");
        when(result.getTaxonomies()).thenReturn(List.of(openCDXDtoNpiTaxonomy));
        when(result.getTaxonomies()).thenReturn(List.of(openCDXDtoNpiTaxonomy));

        OpenCDXDtoNpiIdentifier openCDXDtoNpiIdentifier = mock(OpenCDXDtoNpiIdentifier.class);
        when(openCDXDtoNpiIdentifier.getIssuer()).thenReturn("issuer");
        when(result.getIdentifiers()).thenReturn(List.of(openCDXDtoNpiIdentifier));
        when(result.getIdentifiers()).thenReturn(List.of(openCDXDtoNpiIdentifier));

        when(result.getOtherNames()).thenReturn(List.of("name"));
        when(result.getOtherNames()).thenReturn(List.of("name"));

        when(result.getEndpoints()).thenReturn(List.of("endpoint"));
        when(result.getEndpoints()).thenReturn(List.of("endpoint"));

        OpenCDXIAMProviderModel model = new OpenCDXIAMProviderModel(result, openCDXCountryRepository);
        Assertions.assertNotNull(model.getProtobufMessage());
    }

    @Test
    void test2() {
        OpenCDXDtoNpiResult result = mock(OpenCDXDtoNpiResult.class);
        OpenCDXCountryRepository openCDXCountryRepository = mock(OpenCDXCountryRepository.class);
        OpenCDXDtoNpiAddress address = mock(OpenCDXDtoNpiAddress.class);
        when(address.getCountryCode()).thenReturn("US");
        when(result.getAddresses()).thenReturn(List.of(address));
        when(result.getBasic()).thenReturn(null);
        when(result.getEnumerationType()).thenReturn(null);
        when(result.getOtherNames()).thenReturn(null);
        when(result.getTaxonomies()).thenReturn(null);
        when(result.getAddresses()).thenReturn(List.of(address));
        OpenCDXDtoNpiBasicInfo basicInfo = mock(OpenCDXDtoNpiBasicInfo.class);
        when(basicInfo.getFirstName()).thenReturn("firstName");
        when(basicInfo.getLastName()).thenReturn("lastName");
        when(basicInfo.getSoleProprietor()).thenReturn("soleProprietor");
        when(basicInfo.getCredential()).thenReturn("creds");
        when(basicInfo.getGender()).thenReturn("M");
        when(basicInfo.getNamePrefix()).thenReturn("prefix");
        when(basicInfo.getNameSuffix()).thenReturn("suffix");
        when(result.getBasic()).thenReturn(basicInfo);

        OpenCDXDtoNpiTaxonomy openCDXDtoNpiTaxonomy = mock(OpenCDXDtoNpiTaxonomy.class);
        when(openCDXDtoNpiTaxonomy.getState()).thenReturn("state");
        when(openCDXDtoNpiTaxonomy.getLicense()).thenReturn("license");
        when(result.getTaxonomies()).thenReturn(List.of(openCDXDtoNpiTaxonomy));
        when(result.getTaxonomies()).thenReturn(List.of(openCDXDtoNpiTaxonomy));

        OpenCDXDtoNpiIdentifier openCDXDtoNpiIdentifier = mock(OpenCDXDtoNpiIdentifier.class);
        when(openCDXDtoNpiIdentifier.getIssuer()).thenReturn("issuer");
        when(result.getIdentifiers()).thenReturn(List.of(openCDXDtoNpiIdentifier));
        when(result.getIdentifiers()).thenReturn(List.of(openCDXDtoNpiIdentifier));

        when(result.getOtherNames()).thenReturn(List.of("name"));
        when(result.getOtherNames()).thenReturn(List.of("name"));

        when(result.getEndpoints()).thenReturn(List.of("endpoint"));
        when(result.getEndpoints()).thenReturn(List.of("endpoint"));

        OpenCDXIAMProviderModel model = new OpenCDXIAMProviderModel(result, openCDXCountryRepository);
        Assertions.assertNotNull(model.getProtobufMessage());
    }
}
