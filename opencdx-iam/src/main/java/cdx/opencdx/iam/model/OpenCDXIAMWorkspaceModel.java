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

import cdx.opencdx.grpc.organization.Department;
import cdx.opencdx.grpc.organization.Workspace;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Workspace in Mongo. Features conversions to/from Protobuf messages.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("workspace")
public class OpenCDXIAMWorkspaceModel {
    @Id
    private ObjectId id;

    private String name;
    private String description;
    private Instant createDate;
    private String location;
    private String manager;
    private Integer capacity;
    private List<String> facilities;
    private String workspaceType;
    private List<String> workspaceImageUrls;
    private String usagePolicy;
    private String availabilitySchedule;
    private List<Department> departments;
    private ObjectId organization;
    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;

    /**
     * Constructor from protobuf message Workspace
     * @param workspace Protobuf message to generate from
     */
    public OpenCDXIAMWorkspaceModel(Workspace workspace) {
        if (workspace.hasId()) {
            this.id = new ObjectId(workspace.getId());
        }
        this.name = workspace.getName();
        this.description = workspace.getDescription();
        if (workspace.hasCreatedDate()) {
            this.createDate = Instant.ofEpochSecond(
                    workspace.getCreatedDate().getSeconds(),
                    workspace.getCreatedDate().getNanos());
        }
        this.location = workspace.getLocation();
        this.manager = workspace.getManager();
        this.capacity = workspace.getCapacity();
        this.facilities = workspace.getFacilitiesList();
        this.workspaceType = workspace.getWorkspaceType();
        this.workspaceImageUrls = workspace.getWorkspaceImageUrlsList();
        this.usagePolicy = workspace.getUsagePolicy();
        this.availabilitySchedule = workspace.getAvailabilitySchedule();
        this.departments = workspace.getDepartmentsList();
        this.organization = new ObjectId(workspace.getOrganizationId());

        if (workspace.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    workspace.getCreated().getSeconds(), workspace.getCreated().getNanos());
        }
        if (workspace.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    workspace.getModified().getSeconds(),
                    workspace.getModified().getNanos());
        }
        if (workspace.hasCreator()) {
            this.creator = new ObjectId(workspace.getCreator());
        }
        if (workspace.hasModifier()) {
            this.modifier = new ObjectId(workspace.getModifier());
        }
    }

    /**
     * Method to get the protobuf workspace object
     * @return protobuf workspace object
     */
    @SuppressWarnings("java:S3776")
    public Workspace getProtobufMessage() {
        Workspace.Builder builder = Workspace.newBuilder();

        builder.setId(this.id.toHexString());

        if (this.name != null) {
            builder.setName(this.name);
        }
        if (this.description != null) {
            builder.setDescription(this.description);
        }
        if (this.createDate != null) {
            builder.setCreatedDate(Timestamp.newBuilder()
                    .setSeconds(this.createDate.getEpochSecond())
                    .setNanos(this.createDate.getNano())
                    .build());
        }
        if (this.location != null) {
            builder.setLocation(this.location);
        }
        if (this.manager != null) {
            builder.setManager(this.manager);
        }

        if (this.capacity != null) {
            builder.setCapacity(this.capacity);
        }
        if (this.organization != null) {
            builder.setOrganizationId(this.organization.toHexString());
        }
        if (this.facilities != null) {
            builder.addAllFacilities(this.facilities);
        }
        if (this.workspaceType != null) {
            builder.setWorkspaceType(this.workspaceType);
        }
        if (this.workspaceImageUrls != null) {
            builder.addAllWorkspaceImageUrls(this.workspaceImageUrls);
        }
        if (this.usagePolicy != null) {
            builder.setUsagePolicy(this.usagePolicy);
        }
        if (this.availabilitySchedule != null) {
            builder.setAvailabilitySchedule(this.availabilitySchedule);
        }
        if (this.departments != null) {
            builder.addAllDepartments(this.departments);
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
}
