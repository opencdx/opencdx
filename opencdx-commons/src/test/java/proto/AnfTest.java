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
package proto;

import cdx.opencdx.grpc.anf.AnfStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
    void testAnfStatement() throws JsonProcessingException {
        AnfStatement.ANFStatement anfStatement = AnfStatement.ANFStatement.newBuilder()
                .setId(AnfStatement.Identifier.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build())
                .setTime(AnfStatement.Measure.newBuilder()
                        .setUpperBound(100.0F)
                        .setLowerBound(0.0F)
                        .setIncludeLowerBound(true)
                        .setIncludeUpperBound(true)
                        .setResolution(1.0F)
                        .setSemantic(AnfStatement.LogicalExpression.newBuilder()
                                .setExpression("expression")
                                .build())
                        .build())
                .setSubjectOfRecord(AnfStatement.Participant.newBuilder()
                        .setId(ObjectId.get().toHexString())
                        .build())
                .addAllAuthor(List.of(AnfStatement.Practitioner.newBuilder()
                        .setPractitioner("practitioner")
                        .setCode(AnfStatement.LogicalExpression.newBuilder()
                                .setExpression("expression")
                                .build())
                        .setId(ObjectId.get().toHexString())
                        .build()))
                .setSubjectOfInformation(AnfStatement.LogicalExpression.newBuilder()
                        .setExpression("expression")
                        .build())
                .addAllAssociatedStatement(List.of(AnfStatement.AssociatedStatement.newBuilder()
                        .setDescription("Associated description")
                        .build()))
                .setTopic(AnfStatement.LogicalExpression.newBuilder()
                        .setExpression("Topic")
                        .build())
                .setType(AnfStatement.LogicalExpression.newBuilder()
                        .setExpression("Type")
                        .build())
                .setNarrativeCircumstance(AnfStatement.NarrativeCircumstance.newBuilder()
                        .setText("Narrative Circumstance")
                        .build())
                .build();

        log.info(
                "ANFStatement: \n{}",
                this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement));
    }
}
