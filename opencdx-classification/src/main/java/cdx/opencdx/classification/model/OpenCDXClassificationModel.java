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
package cdx.opencdx.classification.model;

import cdx.opencdx.grpc.connected.ConnectedTest;
import cdx.opencdx.grpc.media.Media;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.UserAnswer;
import cdx.opencdx.grpc.questionnaire.UserQuestionnaireData;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for OpecCDXClassification in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("classification")
public class OpenCDXClassificationModel {
    @Id
    private ObjectId id;

    private UserAnswer userAnswer;
    private ClassificationResponse classificationResponse;
    private Media media;
    private ConnectedTest connectedTest;
    private UserQuestionnaireData userQuestionnaireData;

    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;
}
