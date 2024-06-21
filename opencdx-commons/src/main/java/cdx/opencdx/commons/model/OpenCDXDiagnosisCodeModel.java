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

import cdx.opencdx.grpc.data.ANFStatement;
import cdx.opencdx.grpc.data.DiagnosisCode;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Model for DiagnosisCode. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXDiagnosisCodeModel {

    private ANFStatement anfStatement;
    private String tinkarCode;
    private Map<String, String> valueMap;
    private String providerId;

    /**
     * Constructor for OpenCDXDiagnosisCodeModel
     * @param diagnosisCode DiagnosisCode to use
     */
    public OpenCDXDiagnosisCodeModel(DiagnosisCode diagnosisCode) {
        this.anfStatement = diagnosisCode.getAnfStatement();
        this.tinkarCode = diagnosisCode.getTinkarCode();
        this.providerId = diagnosisCode.getProviderId();
        this.valueMap = diagnosisCode.getValueMapMap();
    }

    /**
     * Method to convert to protobuf message DiagnosisCode
     * @return Protobuf message DiagnosisCode
     */
    public DiagnosisCode getProtobufMessage() {
        DiagnosisCode.Builder builder = DiagnosisCode.newBuilder();
        if (this.anfStatement != null) {
            builder.setAnfStatement(this.anfStatement);
        }
        builder.setTinkarCode(this.tinkarCode);
        builder.setProviderId(this.providerId);
        if (this.valueMap != null) {
            builder.putAllValueMap(this.valueMap);
        }
        return builder.build();
    }
}
