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

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.model.OpenCDXProfileModel;
import cdx.opencdx.grpc.connected.ConnectedTest;
import cdx.opencdx.grpc.media.Media;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.UserAnswer;
import cdx.opencdx.grpc.questionnaire.UserQuestionnaireData;
import java.time.Instant;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model class for OpenCDX Classification Model.
 * This class represents a classification model used in OpenCDX.
 * It contains various properties such as user answer, classification response, media, connected test,
 * user questionnaire data, creation timestamp, modification timestamp, creator, and modifier.
 *
 * This class is used for conversions to Protobuf messages.
 */
@Data
@Builder
@AllArgsConstructor
@Document("classification")
public class OpenCDXClassificationModel {

    /**
     * Default Constructor
     */
    public OpenCDXClassificationModel() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Id
    private OpenCDXIdentifier id;

    private OpenCDXProfileModel patient;
    private UserAnswer userAnswer;
    private ClassificationResponse classificationResponse;
    private Media media;
    private ConnectedTest connectedTest;
    private UserQuestionnaireData userQuestionnaireData;
    private Media testDetailsMedia;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;
}
