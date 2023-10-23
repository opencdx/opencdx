/*
 * Copyright 2023 Safe Health Systems, Inc.
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

import cdx.media.v2alpha.Media;
import cdx.media.v2alpha.MediaStatus;
import cdx.media.v2alpha.MediaType;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
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
    private ObjectId id;

    Instant created;
    Instant updated;
    String organization;
    String workspace;
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

    /**
     * Constructor taking a Media and generating the Model
     * @param media Media to generate model for.
     */
    public OpenCDXMediaModel(Media media) {
        if (media.hasId()) {
            this.id = new ObjectId(media.getId());
        }

        if (media.hasCreatedAt()) {
            this.created = Instant.ofEpochSecond(
                    media.getCreatedAt().getSeconds(), media.getCreatedAt().getNanos());
        } else {
            this.created = Instant.now();
        }

        if (media.hasUpdatedAt()) {
            this.updated = Instant.ofEpochSecond(
                    media.getUpdatedAt().getSeconds(), media.getUpdatedAt().getNanos());
        } else {
            this.updated = Instant.now();
        }

        this.organization = media.getOrganizationSlug();
        this.workspace = media.getWorkspaceSlug();
        this.name = media.getName();
        this.shortDescription = media.getShortDescription();
        this.description = media.getDescription();
        this.mediaType = media.getType();
        this.labels = new ArrayList<>(media.getLabelsList());
        this.mimeType = media.getMimeType();
        this.size = media.getSize();
        this.location = media.getLocation();
        this.endpoint = media.getEndpoint();
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

        if (this.created != null) {
            builder.setCreatedAt(Timestamp.newBuilder()
                    .setSeconds(this.created.getEpochSecond())
                    .setNanos(this.created.getNano())
                    .build());
        }

        if (this.updated != null) {
            builder.setCreatedAt(Timestamp.newBuilder()
                    .setSeconds(this.updated.getEpochSecond())
                    .setNanos(this.updated.getNano())
                    .build());
        }
        if (this.organization != null) {
            builder.setOrganizationSlug(this.organization);
        }
        if (this.workspace != null) {
            builder.setWorkspaceSlug(this.workspace);
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
        return builder.build();
    }
}
