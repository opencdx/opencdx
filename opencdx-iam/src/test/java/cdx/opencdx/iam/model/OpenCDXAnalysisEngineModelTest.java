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
package cdx.opencdx.iam.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.AnalysisEngine;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXAnalysisEngineModelTest {

    @Test
    void getProtobufMessage() {
        AnalysisEngine analysisEngine = AnalysisEngine.newBuilder()
                .setId(OpenCDXIdentifier.get().toHexString())
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXAnalysisEngineModel model = new OpenCDXAnalysisEngineModel(analysisEngine);
        Assertions.assertNotNull(model.getProtobufMessage());
    }

    @Test
    void getProtobufMessageElse() {
        AnalysisEngine analysisEngine = AnalysisEngine.newBuilder()
                .setCreated(Timestamp.getDefaultInstance())
                .setOrganizationId(OpenCDXIdentifier.get().toHexString())
                .setWorkspaceId(OpenCDXIdentifier.get().toHexString())
                .build();
        OpenCDXAnalysisEngineModel model = new OpenCDXAnalysisEngineModel(analysisEngine);
        Assertions.assertNotNull(model.getProtobufMessage());
    }
}
