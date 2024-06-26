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
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                .setTime(Measure.newBuilder()
                        .setUpperBound("100")
                        .setLowerBound("0.0")
                        .setIncludeLowerBound(true)
                        .setIncludeUpperBound(true)
                        .setResolution("1.0")
                        .setSemantic("expression")
                        .build())
                .setSubjectOfRecord(Participant.newBuilder()
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build())
                .addAllAuthors(List.of(Practitioner.newBuilder()
                        .setPractitionerValue("practitioner")
                        .setCode("expression")
                        .setId(OpenCDXIdentifier.get().toHexString())
                        .build()))
                .setSubjectOfInformation("expression")
                .addAllAssociatedStatement(List.of(AssociatedStatement.newBuilder()
                        .setSemantic("Associated description")
                        .build()))
                .setTopic("Topic")
                .setType("Type")
                .setPerformanceCircumstance(PerformanceCircumstance.newBuilder()
                        .setStatus(
                                "{\"expressionType\":\"simple\",\"expressionLanguage\":\"local\",\"expressionValue\":\"performed\",\"expressionDescription\":\"Measurement action has been performed.\"}")
                        .setHealthRisk("${{rules.engine.calculated[circumstanceChoice.result]}}")
                        .setResult(Measure.newBuilder()
                                .setLowerBound("90")
                                .setIncludeLowerBound(false)
                                .setSemantic("")
                                .setResolution("{{REPLACE_3079919224534}}")
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
