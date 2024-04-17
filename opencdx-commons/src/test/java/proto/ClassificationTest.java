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
import cdx.opencdx.grpc.data.Duration;
import cdx.opencdx.grpc.data.Location;
import cdx.opencdx.grpc.data.Symptom;
import cdx.opencdx.grpc.data.UserAnswer;
import cdx.opencdx.grpc.service.classification.ClassificationRequest;
import cdx.opencdx.grpc.types.DurationType;
import cdx.opencdx.grpc.types.Gender;
import cdx.opencdx.grpc.types.SeverityLevel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class ClassificationTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testClassificationRequest() throws JsonProcessingException {
        ClassificationRequest request = ClassificationRequest.newBuilder()
                .setUserAnswer(UserAnswer.newBuilder()
                        .setConnectedTestId(OpenCDXIdentifier.get().toHexString())
                        .addAllSymptoms(List.of(Symptom.newBuilder()
                                .setName("Fever")
                                .setSeverity(SeverityLevel.SEVERITY_LEVEL_HIGH)
                                .setOnsetDate(Timestamp.newBuilder().setSeconds(1696732104))
                                .setDuration(Duration.newBuilder()
                                        .setDuration(4)
                                        .setType(DurationType.DURATION_TYPE_DAYS)
                                        .build())
                                .setAdditionalDetails("Fever 101")
                                .build()))
                        .setTextResult("Positive")
                        .setAge(25)
                        .setGender(Gender.GENDER_MALE)
                        .setMedicalConditions("Medical Conditions")
                        .setPregnancyStatus(false)
                        .setLanguagePreference("english")
                        .setUserLocation(Location.newBuilder()
                                .setLongitude(0.1)
                                .setLatitude(0.1)
                                .build())
                        .build())
                .build();
        log.info(
                "EmailTemplate: {}",
                this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
    }
}
