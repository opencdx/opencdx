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
package cdx.opencdx.questionnaire.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.Questionnaire;
import cdx.opencdx.grpc.data.QuestionnaireItem;
import cdx.opencdx.grpc.types.QuestionnaireStatus;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents a questionnaire model.
 * <p>
 * The QuestionnaireModel class is an entity class that represents a questionnaire
 * in an application. It is used to store and retrieve data related to a questionnaire.
 * <p>
 * It is decorated with the following annotations:
 * <p>
 * - @Data: This annotation is provided by the Lombok library, and it automatically generates
 *         getters, setters, toString, equals, and hashCode methods for all fields of the class.
 * <p>
 * - @Builder: This annotation is provided by the Lombok library and is used to generate
 *         a builder pattern for the class. It allows the easy creation of instances of the class
 *         with a fluent API.
 * <p>
 * - @AllArgsConstructor: This annotation is provided by the Lombok library and generates a constructor
 *         with parameters for all fields of the class.
 * <p>
 * - @RequiredArgsConstructor: This annotation is provided by the Lombok library and generates
 *         a constructor with parameters only for the final fields.
 * <p>
 * - @Document: This annotation is used to indicate that instances of this class should be persisted
 *         to a database collection named "audit".
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("questionnaire")
public class OpenCDXQuestionnaireModel {
    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private String resourceType;
    private String title;
    private QuestionnaireStatus status;
    private String description;

    private List<QuestionnaireItem> items;
    private String ruleId;
    private List<String> ruleQuestionId;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
    private OpenCDXIdentifier modifier;

    /**
     * Creates a new instance of QuestionnaireModel based on the given Questionnaire object.
     *
     * @param questionnaire the Questionnaire object to initialize the QuestionnaireModel
     */
    public OpenCDXQuestionnaireModel(Questionnaire questionnaire) {
        if (questionnaire.hasId()) {
            this.id = new OpenCDXIdentifier(questionnaire.getId());
        }

        this.resourceType = questionnaire.getResourceType();
        this.title = questionnaire.getTitle();
        this.status = QuestionnaireStatus.active;
        this.description = questionnaire.getDescription();
        if (questionnaire.hasRuleId()) {
            this.ruleId = questionnaire.getRuleId();
        }
        this.items = questionnaire.getItemList();

        this.ruleQuestionId = questionnaire.getRuleQuestionIdList();

        if (questionnaire.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    questionnaire.getCreated().getSeconds(),
                    questionnaire.getCreated().getNanos());
        }
        if (questionnaire.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    questionnaire.getModified().getSeconds(),
                    questionnaire.getModified().getNanos());
        }
        if (questionnaire.hasCreator()) {
            this.creator = new OpenCDXIdentifier(questionnaire.getCreator());
        }
        if (questionnaire.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(questionnaire.getModifier());
        }
    }

    /**
     * Returns a protobuf message representation of the Questionnaire.
     *
     * @return a protobuf message representation of the Questionnaire
     */
    public Questionnaire getProtobufMessage() {
        Questionnaire.Builder builder = Questionnaire.newBuilder();

        builder.setId(this.id.toHexString());

        if (this.resourceType != null) {
            builder.setResourceType(this.resourceType);
        }
        if (this.title != null) {
            builder.setTitle(this.title);
        }
        if (this.status != null) {
            builder.setStatus(this.status);
        }
        if (this.description != null) {
            builder.setDescription(this.description);
        }
        if (this.ruleId != null) {
            builder.setRuleId(this.ruleId);
        }
        if (this.ruleQuestionId != null && !this.ruleQuestionId.isEmpty()) {
            builder.addAllRuleQuestionId(this.ruleQuestionId);
        }
        if (this.items != null && !this.items.isEmpty()) {
            builder.addAllItem(this.items);
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
     * Updates the OpenCDXQuestionnaireModel with the information from the given Questionnaire object.
     *
     * @param questionnaire the Questionnaire object to update the OpenCDXQuestionnaireModel with
     * @return the updated OpenCDXQuestionnaireModel
     */
    public OpenCDXQuestionnaireModel update(Questionnaire questionnaire) {
        this.resourceType = questionnaire.getResourceType();
        this.title = questionnaire.getTitle();
        this.status = QuestionnaireStatus.active;
        this.description = questionnaire.getDescription();
        if (questionnaire.hasRuleId()) {
            this.ruleId = questionnaire.getRuleId();
        }
        this.items = questionnaire.getItemList();

        this.ruleQuestionId = questionnaire.getRuleQuestionIdList();

        return this;
    }
}
