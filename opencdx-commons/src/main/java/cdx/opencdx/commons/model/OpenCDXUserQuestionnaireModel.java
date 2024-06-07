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
import cdx.opencdx.grpc.data.Questionnaire;
import cdx.opencdx.grpc.data.UserQuestionnaireData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The OpenCDXUserQuestionnaireModel class represents a user questionnaire data model in the OpenCDX system.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("questionnaire-user")
public class OpenCDXUserQuestionnaireModel {
    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private OpenCDXIdentifier patientId;

    private List<Questionnaire> list;

    /**
     * The OpenCDXUserQuestionnaireModel class represents a user questionnaire data model in the OpenCDX system.
     *
     * @param data The UserQuestionnaireData object used to initialize the model.
     */
    public OpenCDXUserQuestionnaireModel(UserQuestionnaireData data) {
        if (data.hasId()) {
            this.id = new OpenCDXIdentifier(data.getId());
        }

        this.patientId = new OpenCDXIdentifier(data.getPatientId());

        this.list = data.getQuestionnaireDataList();
    }

    /**
     * Returns a UserQuestionnaireData object representing this OpenCDXUserQuestionnaireModel instance.
     *
     * @return The UserQuestionnaireData object.
     */
    public UserQuestionnaireData getProtobufMessage() {
        UserQuestionnaireData.Builder builder = UserQuestionnaireData.newBuilder();

        builder.setId(this.id.toHexString());

        builder.setPatientId(this.patientId.toHexString());
        builder.addAllQuestionnaireData(this.list);

        return builder.build();
    }

    /**
     * Updates the OpenCDXUserQuestionnaireModel with the given UserQuestionnaireData.
     *
     * @param data The UserQuestionnaireData object containing the data to update the model.
     * @return The updated OpenCDXUserQuestionnaireModel instance.
     */
    public OpenCDXUserQuestionnaireModel update(UserQuestionnaireData data) {

        this.patientId = new OpenCDXIdentifier(data.getPatientId());

        this.list = data.getQuestionnaireDataList();

        return this;
    }
}
