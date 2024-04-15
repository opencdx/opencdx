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
package proto;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.BasicInfo;
import cdx.opencdx.grpc.data.LabFindings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class ConnectedLabTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testLabFindings() throws JsonProcessingException {
        LabFindings labFindings = LabFindings.newBuilder()
                .setBasicInfo(BasicInfo.newBuilder()
                        .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                        .setPatientId(OpenCDXIdentifier.get().toHexString())
                        .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                        .setType("lab")
                        .setVendorLabTestId(UUID.randomUUID().toString())
                        .setNationalHealthId(UUID.randomUUID().toString())
                        .setHealthServiceId(UUID.randomUUID().toString())
                        .setSource("lab")
                        .build())
                .build();
        log.info("LabFindings: {}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(labFindings));
    }
}
