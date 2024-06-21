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
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Data
@RequiredArgsConstructor
@Document("classification")
public class OpenCDXClassificationResponseModel {

    @Id
    private OpenCDXIdentifier id;

    private Classification classification;

    public OpenCDXClassificationResponseModel(ClassificationResponse classificationResponse) {
        this.classification = classificationResponse.getClassification();
    }

    public ClassificationResponse getProtobuf() {
        return ClassificationResponse.newBuilder()
                .setClassification(classification)
                .build();
    }
}
