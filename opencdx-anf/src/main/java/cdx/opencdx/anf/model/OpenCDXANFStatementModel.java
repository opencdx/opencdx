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
package cdx.opencdx.anf.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.*;
import cdx.opencdx.grpc.types.Status;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a model for an ANF statement in OpenCDX.
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("anf-statement")
public class OpenCDXANFStatementModel {
    @Id
    private OpenCDXIdentifier id;

    private Measure time;
    private Participant subjectOfRecord;
    private List<Practitioner> authors;
    private String subjectOfInformation;
    private List<AssociatedStatement> associatedStatements;
    private String topic;
    private String type;
    private CircumstanceChoice circumstanceChoice;

    private Instant created;
    private Instant modified;
    private OpenCDXIdentifier creator;
    private OpenCDXIdentifier modifier;

    private Status status;

    /**
     * Constructs a new OpenCDXANFStatementModel based on an instance of ANFStatement.
     * The method initializes the attributes of the OpenCDXANFStatementModel with the corresponding values
     * from the AnfStatement instance.
     *
     * @param anfStatement the AnfStatement instance to create the OpenCDXANFStatementModel from
     */
    public OpenCDXANFStatementModel(ANFStatement anfStatement) {
        log.trace("Creating OpenCDXANFStatementModel from ANFStatement");
        if (anfStatement.hasId()) {
            this.id = new OpenCDXIdentifier(anfStatement.getId().getId());
        }

        this.time = anfStatement.getTime();
        this.subjectOfRecord = anfStatement.getSubjectOfRecord();
        this.authors = anfStatement.getAuthorsList();
        this.subjectOfInformation = anfStatement.getSubjectOfInformation();
        this.associatedStatements = anfStatement.getAssociatedStatementList();
        this.topic = anfStatement.getTopic();
        this.type = anfStatement.getType();
        this.circumstanceChoice = anfStatement.getCircumstanceChoice();

        if (anfStatement.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    anfStatement.getCreated().getSeconds(),
                    anfStatement.getCreated().getNanos());
        }
        if (anfStatement.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    anfStatement.getModified().getSeconds(),
                    anfStatement.getModified().getNanos());
        }
        if (anfStatement.hasCreator()) {
            this.creator = new OpenCDXIdentifier(anfStatement.getCreator());
        }
        if (anfStatement.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(anfStatement.getModifier());
        }

        this.status = Status.STATUS_ACTIVE;
    }

    /**
     * Returns the Protobuf message representation of the 
     *
     * @return The Protobuf message representation
     */
    public ANFStatement getProtobufMessage() {
        log.trace("Creating ANFStatement from OpenCDXANFStatementModel");
        ANFStatement.Builder builder = ANFStatement.newBuilder();

        builder.setId(ANFIdentifier.newBuilder()
                .setId(this.id.toHexString())
                .build());

        if (this.time != null) {
            builder.setTime(this.time);
        }
        if (this.subjectOfRecord != null) {
            builder.setSubjectOfRecord(this.subjectOfRecord);
        }
        if (this.authors != null) {
            builder.addAllAuthors(this.authors);
        }
        if (this.subjectOfInformation != null) {
            builder.setSubjectOfInformation(this.subjectOfInformation);
        }
        if (this.associatedStatements != null) {
            builder.addAllAssociatedStatement(this.associatedStatements);
        }
        if (this.topic != null) {
            builder.setTopic(this.topic);
        }
        if (this.type != null) {
            builder.setType(this.type);
        }
        if (this.circumstanceChoice != null) {
            builder.setCircumstanceChoice(this.circumstanceChoice);
        }
        if (this.created != null) {
            builder.setCreated(Timestamp.newBuilder()
                    .setSeconds(this.created.getEpochSecond())
                    .setNanos(this.created.getNano()));
        }
        if (this.modified != null) {
            builder.setModified(Timestamp.newBuilder()
                    .setSeconds(this.modified.getEpochSecond())
                    .setNanos(this.modified.getNano()));
        }
        if (this.creator != null) {
            builder.setCreator(this.creator.toHexString());
        }
        if (this.modifier != null) {
            builder.setModifier(this.modifier.toHexString());
        }
        if (this.status != null) {
            builder.setStatus(this.status);
        }
        return builder.build();
    }
}
