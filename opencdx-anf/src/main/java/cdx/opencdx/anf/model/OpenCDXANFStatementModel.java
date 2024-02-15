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

import cdx.opencdx.grpc.anf.AnfStatement;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
    private ObjectId id;

    private AnfStatement.Measure time;
    private AnfStatement.Participant subjectOfRecord;
    private List<AnfStatement.Practitioner> authors;
    private AnfStatement.LogicalExpression subjectOfInformation;
    private List<AnfStatement.AssociatedStatement> associatedStatements;
    private AnfStatement.LogicalExpression topic;
    private AnfStatement.LogicalExpression type;
    private AnfStatement.RequestCircumstance requestCircumstance;
    private AnfStatement.PerformanceCircumstance performanceCircumstance;
    private AnfStatement.NarrativeCircumstance narrativeCircumstance;

    private Instant created;
    private Instant modified;
    private ObjectId creator;
    private ObjectId modifier;

    private AnfStatement.Status status;

    /**
     * Constructs a new OpenCDXANFStatementModel based on an instance of AnfStatement.ANFStatement.
     * The method initializes the attributes of the OpenCDXANFStatementModel with the corresponding values
     * from the AnfStatement instance.
     *
     * @param anfStatement the AnfStatement instance to create the OpenCDXANFStatementModel from
     */
    public OpenCDXANFStatementModel(AnfStatement.ANFStatement anfStatement) {
        log.trace("Creating OpenCDXANFStatementModel from ANFStatement");
        if (anfStatement.hasId()) {
            this.id = new ObjectId(anfStatement.getId().getId());
        }

        this.time = anfStatement.getTime();
        this.subjectOfRecord = anfStatement.getSubjectOfRecord();
        this.authors = anfStatement.getAuthorList();
        this.subjectOfInformation = anfStatement.getSubjectOfInformation();
        this.associatedStatements = anfStatement.getAssociatedStatementList();
        this.topic = anfStatement.getTopic();
        this.type = anfStatement.getType();
        this.requestCircumstance = anfStatement.getRequestCircumstance();
        this.performanceCircumstance = anfStatement.getPerformanceCircumstance();
        this.narrativeCircumstance = anfStatement.getNarrativeCircumstance();

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
            this.creator = new ObjectId(anfStatement.getCreator());
        }
        if (anfStatement.hasModifier()) {
            this.modifier = new ObjectId(anfStatement.getModifier());
        }

        this.status = AnfStatement.Status.STATUS_ACTIVE;
    }

    /**
     * Returns the Protobuf message representation of the AnfStatement.
     *
     * @return The Protobuf message representation
     */
    public AnfStatement.ANFStatement getProtobufMessage() {
        log.trace("Creating ANFStatement from OpenCDXANFStatementModel");
        AnfStatement.ANFStatement.Builder builder = AnfStatement.ANFStatement.newBuilder();

        builder.setId(AnfStatement.Identifier.newBuilder()
                .setId(this.id.toHexString())
                .build());

        if (this.time != null) {
            builder.setTime(this.time);
        }
        if (this.subjectOfRecord != null) {
            builder.setSubjectOfRecord(this.subjectOfRecord);
        }
        if (this.authors != null) {
            builder.addAllAuthor(this.authors);
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
        if (this.requestCircumstance != null) {
            builder.setRequestCircumstance(this.requestCircumstance);
        }
        if (this.performanceCircumstance != null) {
            builder.setPerformanceCircumstance(this.performanceCircumstance);
        }
        if (this.narrativeCircumstance != null) {
            builder.setNarrativeCircumstance(this.narrativeCircumstance);
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
