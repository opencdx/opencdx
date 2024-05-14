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
package cdx.opencdx.media.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.Media;
import cdx.opencdx.grpc.types.MediaStatus;
import cdx.opencdx.grpc.types.MediaType;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * OpenCDXMediaModel for the Protobuf Media class, translation between types.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("media")
public class OpenCDXMediaModel {
    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    Instant updated;
    OpenCDXIdentifier organization;
    OpenCDXIdentifier workspace;
    String name;
    String shortDescription;
    String description;
    MediaType mediaType;
    List<String> labels;
    String mimeType;
    Long size;
    String location;

    String endpoint;
    MediaStatus status;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
    private OpenCDXIdentifier modifier;

    /**
     * Constructor taking a Media and generating the Model
     * @param media Media to generate model for.
     */
    public OpenCDXMediaModel(Media media) {
        if (media.hasId()) {
            this.id = new OpenCDXIdentifier(media.getId());
        }

        if (media.hasUpdatedAt()) {
            this.updated = Instant.ofEpochSecond(
                    media.getUpdatedAt().getSeconds(), media.getUpdatedAt().getNanos());
        } else {
            this.updated = Instant.now();
        }

        this.organization = new OpenCDXIdentifier(media.getOrganizationId());
        this.workspace = new OpenCDXIdentifier(media.getWorkspaceId());
        this.name = media.getName();
        this.shortDescription = media.getShortDescription();
        this.description = media.getDescription();
        this.mediaType = media.getType();
        this.labels = new ArrayList<>(media.getLabelsList());
        this.mimeType = media.getMimeType();
        this.size = media.getSize();
        this.location = media.getLocation();
        this.endpoint = media.getEndpoint();

        if (media.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    media.getCreated().getSeconds(), media.getCreated().getNanos());
        }
        if (media.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    media.getModified().getSeconds(), media.getModified().getNanos());
        }
        if (media.hasCreator()) {
            this.creator = new OpenCDXIdentifier(media.getCreator());
        }
        if (media.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(media.getModifier());
        }
    }

    /**
     * Method to generate an Protobuf equivalent message
     * @return Media as the protobuf message.
     */
    @SuppressWarnings("java:S3776")
    public Media getProtobufMessage() {
        Media.Builder builder = Media.newBuilder();

        if (this.id != null) {
            builder.setId(this.id.toHexString());
        }

        if (this.updated != null) {
            builder.setCreatedAt(Timestamp.newBuilder()
                    .setSeconds(this.updated.getEpochSecond())
                    .setNanos(this.updated.getNano())
                    .build());
        }
        if (this.organization != null) {
            builder.setOrganizationId(this.organization.toHexString());
        }
        if (this.workspace != null) {
            builder.setWorkspaceId(this.workspace.toHexString());
        }
        if (this.name != null) {
            builder.setName(this.name);
        }
        if (this.shortDescription != null) {
            builder.setShortDescription(this.shortDescription);
        }
        if (this.description != null) {
            builder.setDescription(this.description);
        }
        if (this.mediaType != null) {
            builder.setType(this.mediaType);
        }
        if (this.labels != null && !this.labels.isEmpty()) {
            builder.addAllLabels(this.labels);
        }
        if (this.mimeType != null) {
            builder.setMimeType(this.mimeType);
        }
        if (this.size != null) {
            builder.setSize(this.size);
        }
        if (this.location != null) {
            builder.setLocation(this.location);
        }
        if (this.endpoint != null) {
            builder.setEndpoint(this.endpoint);
        }
        if (this.status != null) {
            builder.setStatus(this.status);
        }
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
        if (this.modified != null) {
            builder.setModifier(this.modifier.toHexString());
        }
        return builder.build();
    }

    /**
     * Updates the OpenCDXMediaModel with the values from the given Media object.
     *
     * @param media The Media object containing the updated values.
     * @return The updated OpenCDXMediaModel.
     */
    public OpenCDXMediaModel update(Media media) {
        if (media.hasUpdatedAt()) {
            this.updated = Instant.ofEpochSecond(
                    media.getUpdatedAt().getSeconds(), media.getUpdatedAt().getNanos());
        } else {
            this.updated = Instant.now();
        }

        this.organization = new OpenCDXIdentifier(media.getOrganizationId());
        this.workspace = new OpenCDXIdentifier(media.getWorkspaceId());
        this.name = media.getName();
        this.shortDescription = media.getShortDescription();
        this.description = media.getDescription();
        this.mediaType = media.getType();
        this.labels = new ArrayList<>(media.getLabelsList());
        this.mimeType = media.getMimeType();
        this.size = media.getSize();
        this.location = media.getLocation();
        this.endpoint = media.getEndpoint();
        return this;
    }
}
