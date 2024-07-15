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
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Analysis Engine in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("analysis-engine")
@SuppressWarnings({"java:S3776", "java:S1117", "java:S116"})
public class OpenCDXAnalysisEngineModel {
    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private String name;

    private OpenCDXIdentifier organizationId;
    private OpenCDXIdentifier workspaceId;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
    private OpenCDXIdentifier modifier;

    /**
     * Constructor from protobuf message AnalysisEngine
     * @param analysisEngine Protobuf message to generate from
     */
    public OpenCDXAnalysisEngineModel(AnalysisEngine analysisEngine) {
        if (analysisEngine.hasId()) {
            this.id = new OpenCDXIdentifier(analysisEngine.getId());
        }

        this.name = analysisEngine.getName();
        this.organizationId = new OpenCDXIdentifier(analysisEngine.getOrganizationId());
        this.workspaceId = new OpenCDXIdentifier(analysisEngine.getWorkspaceId());

        if (analysisEngine.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    analysisEngine.getCreated().getSeconds(),
                    analysisEngine.getCreated().getNanos());
        }
        if (analysisEngine.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    analysisEngine.getModified().getSeconds(),
                    analysisEngine.getModified().getNanos());
        }
        if (analysisEngine.hasCreator()) {
            this.creator = new OpenCDXIdentifier(analysisEngine.getCreator());
        }
        if (analysisEngine.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(analysisEngine.getModifier());
        }
    }

    /**
     * Method to get the protobuf analysisEngine object
     * @return protobuf temperature object
     */
    public AnalysisEngine getProtobufMessage() {
        AnalysisEngine.Builder builder = AnalysisEngine.newBuilder();

        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }

        builder.setName(this.name);
        builder.setOrganizationId(this.organizationId.toHexString());
        builder.setWorkspaceId(this.workspaceId.toHexString());

        if (this.created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(this.created.getEpochSecond())
                    .setNanos(this.created.getNano())
                    .build());
        }
        if (this.modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(this.modified.getEpochSecond())
                    .setNanos(this.modified.getNano())
                    .build());
        }
        if (this.creator != null) {
            builder.setCreator(this.creator.toHexString());
        }
        if (this.modifier != null) {
            builder.setModifier(this.modifier.toHexString());
        }
        return builder.build();
    }

    /**
     * Updates the OpenCDXAnalysisEngineModel with the given OpenCDXAnalysisEngine object.
     *
     * @param analysisEngine The analysisEngine object to update the model with.
     * @return The updated OpenCDXTemperatureMeasurementModel instance.
     */
    public OpenCDXAnalysisEngineModel update(AnalysisEngine analysisEngine) {
        this.id = new OpenCDXIdentifier(analysisEngine.getId());
        this.name = analysisEngine.getName();
        this.organizationId = new OpenCDXIdentifier(analysisEngine.getOrganizationId());
        this.workspaceId = new OpenCDXIdentifier(analysisEngine.getWorkspaceId());

        return this;
    }
}
