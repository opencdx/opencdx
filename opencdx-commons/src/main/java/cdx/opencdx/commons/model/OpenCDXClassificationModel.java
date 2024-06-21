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
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.*;

/**
 * Model class for OpenCDX Classification Model.
 * This class represents a classification model used in OpenCDX.
 * It contains various properties such as user answer, classification response, media, connected test,
 * user questionnaire data, creation timestamp, modification timestamp, creator, and modifier.
 * <p>
 * This class is used for conversions to Protobuf messages.
 */
@Data
@Builder
@AllArgsConstructor
public class OpenCDXClassificationModel {

    /**
     * Default Constructor
     */
    public OpenCDXClassificationModel() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private OpenCDXProfileModel patient;
    private OpenCDXUserAnswerModel userAnswer;
    private OpenCDXClassificationResponseModel classificationResponse;
    private OpenCDXMediaModel media;
    private OpenCDXConnectedTestModel connectedTest;
    private OpenCDXUserQuestionnaireModel userQuestionnaireData;
    private OpenCDXMediaModel testDetailsMedia;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
    private OpenCDXIdentifier modifier;

    /**
     * Returns a UserQuestionnaireData object representing this OpenCDXUserQuestionnaireModel instance.
     *
     * @return The UserQuestionnaireData object.
     */
    public Classification getProtobufMessage() {
        Classification.Builder builder = Classification.newBuilder();
        builder.setPatientId(this.patient.getId().toHexString());
        return builder.build();
    }
}
