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
import cdx.opencdx.grpc.data.Location;
import cdx.opencdx.grpc.data.Symptom;
import cdx.opencdx.grpc.data.UserAnswer;
import cdx.opencdx.grpc.types.Gender;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OpenCDXUserAnswerModel {
    private OpenCDXIdentifier patientId;
    private OpenCDXIdentifier connectedTestId;
    private OpenCDXIdentifier questionnaireId;
    private List<Symptom> symptoms;
    private OpenCDXIdentifier mediaId;
    private String result;
    private Integer age;
    private Gender gender;
    private String medicalConditions;
    private boolean isPregnant;
    private String preferredLanguage;
    private Location userLocation;
    private OpenCDXIdentifier submittingUserId;

    public OpenCDXUserAnswerModel(UserAnswer userAnswer) {
        if (userAnswer.getPatientId() != null) {
            this.patientId = new OpenCDXIdentifier(userAnswer.getPatientId());
        }

        if (userAnswer.hasUserQuestionnaireId()) {
            this.questionnaireId = new OpenCDXIdentifier(userAnswer.getUserQuestionnaireId());
        }
        if (userAnswer.hasConnectedTestId()) {
            this.connectedTestId = new OpenCDXIdentifier(userAnswer.getConnectedTestId());
        }

        this.symptoms = userAnswer.getSymptomsList();
        if (userAnswer.hasMediaId()) {
            this.mediaId = new OpenCDXIdentifier(userAnswer.getMediaId());
        }
        if (userAnswer.hasTextResult()) {
            this.result = userAnswer.getTextResult();
        }
        this.age = userAnswer.getAge();
        this.gender = userAnswer.getGender();
        this.medicalConditions = userAnswer.getMedicalConditions();
        this.isPregnant = userAnswer.getPregnancyStatus();
        this.preferredLanguage = userAnswer.getLanguagePreference();
        this.userLocation = userAnswer.getUserLocation();
        if (userAnswer.getSubmittingUserId() != null) {
            this.submittingUserId = new OpenCDXIdentifier(userAnswer.getSubmittingUserId());
        }
    }
}
