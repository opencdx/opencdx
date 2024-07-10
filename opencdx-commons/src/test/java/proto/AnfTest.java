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
import cdx.opencdx.grpc.data.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class AnfTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testIdentifier() throws JsonProcessingException {
        Identifier identifier = Identifier.newBuilder()
                .setIdentifier(OpenCDXIdentifier.get().toHexString())
                .build();

        log.info(
                "Identifier: \n{}", this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(identifier));
    }

    @Test
    void testAnfStatement() throws JsonProcessingException {
        ANFStatement anfStatement = ANFStatement.newBuilder()
                .setId("UUID")
                .setTime(Measure.newBuilder()
                        .setUpperBound("1562631151")
                        .setLowerBound("1562631151")
                        .setIncludeLowerBound(true)
                        .setIncludeUpperBound(true)
                        .setResolution("seconds")
                        .setSemantic("Seconds | 1562631151")
                        .build())
                .setSubjectOfRecord(Participant.newBuilder()
                        .setId("UUID (PatientId)")
                        .setCode("UUID (EncounterID)")
                        .build())
                .addAllAuthors(List.of(Practitioner.newBuilder()
                        .setPractitionerValue("practitioner")
                        .setCode("expression")
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build()))
                .setSubjectOfInformation("UUID  (PatientId)")
                .addAllAssociatedStatement(List.of(AssociatedStatement.newBuilder()
                        .setSemantic("Associated description")
                        .build()))
                .setTopic("255585003 |Malignant neoplasm of liver |finding site|624008003 |Bone structure| ")
                .setType("PERFORMANCE")
                .setPerformanceCircumstance(PerformanceCircumstance.newBuilder()
                        .addAllPurpose(List.of("purpose"))
                        .setStatus(
                                "{\"expressionType\":\"simple\",\"expressionLanguage\":\"local\",\"expressionValue\":\"performed\",\"expressionDescription\":\"Measurement action has been performed.\"}")
                        .setHealthRisk("${{rules.engine.calculated[circumstanceChoice.result]}}")
                        .setResult(Measure.newBuilder()
                                .setLowerBound("90")
                                .setIncludeLowerBound(false)
                                .setSemantic("")
                                .setResolution("mmHg / cm / in / kg / m / s / L / % / etc.")
                                .setUpperBound("120")
                                .setIncludeUpperBound(false)
                                .build())
                        .setNormalRange(Measure.getDefaultInstance())
                        .setTiming(Measure.getDefaultInstance())
                        .addAllParticipant(List.of(Participant.getDefaultInstance()))
                        .build())
                .build();

        log.info(
                "ANFStatement: \n{}",
                this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement));
    }
}
