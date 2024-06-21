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
package cdx.opencdx.commons.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.Classification;
import cdx.opencdx.grpc.service.classification.ClassificationResponse;
import cdx.opencdx.grpc.types.ClassificationType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Slf4j
@Data
@RequiredArgsConstructor
@Document("classification")
public class OpenCDXClassificationResponseModel {

    @Id
    private OpenCDXIdentifier id;

    private Float confidence;
    private Float positiveProbability;
    private String message;
    private String availability;
    private Float cost;
    private String furtherActions;
    private String feedbackUrl;
    private Instant timestamp;
    private OpenCDXIdentifier patientId;
    private Boolean notifyCdc;
    private OpenCDXDiagnosisCodeModel diagnosis;
    private ClassificationType type;

    public OpenCDXClassificationResponseModel(ClassificationResponse classificationResponse) {
        Classification classification = classificationResponse.getClassification();

        if (classification.getPatientId() != null) {
            this.patientId = new OpenCDXIdentifier(classification.getPatientId());
        }
        if (classification.hasDiagnosis()) {
            this.diagnosis = new OpenCDXDiagnosisCodeModel(classification.getDiagnosis());
        }
        this.confidence = classification.getConfidence();
        this.positiveProbability = classification.getPositiveProbability();
        this.message = classification.getMessage();
        this.availability = classification.getAvailability();
        this.cost = classification.getCost();
        this.furtherActions = classification.getFurtherActions();
        this.feedbackUrl = classification.getFeedbackUrl();
        if (classification.hasTimestamp()) {
            this.timestamp = Instant.ofEpochSecond(
                    classification.getTimestamp().getSeconds(),
                    classification.getTimestamp().getNanos());
        }
        this.notifyCdc = classification.getNotifyCdc();
        this.type = classification.getType();
    }

    public Classification getClassification() {
        Classification.Builder builder = Classification.newBuilder();

        if (patientId != null) {
            builder.setPatientId(patientId.toHexString());
        }
        if (diagnosis != null) {
            builder.setDiagnosis(diagnosis.getProtobufMessage());
        }
        if (confidence != null) {
            builder.setConfidence(confidence);
        }
        if (positiveProbability != null) {
            builder.setPositiveProbability(positiveProbability);
        }
        if (message != null) {
            builder.setMessage(message);
        }
        if (availability != null) {
            builder.setAvailability(availability);
        }
        if (cost != null) {
            builder.setCost(cost);
        }
        if (furtherActions != null) {
            builder.setFurtherActions(furtherActions);
        }
        if (feedbackUrl != null) {
            builder.setFeedbackUrl(feedbackUrl);
        }
        if (timestamp != null) {
            builder.setTimestamp(com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(timestamp.getEpochSecond())
                    .setNanos(timestamp.getNano())
                    .build());
        }
        if (notifyCdc != null) {
            builder.setNotifyCdc(notifyCdc);
        }
        if (type != null) {
            builder.setType(type);
        }

        return builder.build();
    }

    public ClassificationResponse getProtobuf() {

        return ClassificationResponse.newBuilder()
                .setClassification(this.getClassification())
                .build();
    }
}
