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
package cdx.opencdx.communications.model;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.SMSTemplate;
import cdx.opencdx.grpc.types.TemplateType;
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
 * Model for OpecCDXSMSTemplate in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("sms-template")
public class OpenCDXSMSTemplateModel {

    @Id
    private OpenCDXIdentifier id;

    @Version
    private long version;

    private TemplateType templateType;
    private String message;
    private List<String> variables;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant modified;

    @CreatedBy
    private OpenCDXIdentifier creator;

    @LastModifiedBy
    private OpenCDXIdentifier modifier;

    /**
     * Constructor to create based on an SMSTemplate
     * @param template SMSTemplate for this model
     */
    public OpenCDXSMSTemplateModel(SMSTemplate template) {
        if (template.hasTemplateId()) {
            this.id = new OpenCDXIdentifier(template.getTemplateId());
        }
        this.templateType = template.getTemplateType();
        this.message = template.getMessage();
        this.variables = new ArrayList<>(template.getVariablesList());

        if (template.hasCreated()) {
            this.created = Instant.ofEpochSecond(
                    template.getCreated().getSeconds(), template.getCreated().getNanos());
        }
        if (template.hasModified()) {
            this.modified = Instant.ofEpochSecond(
                    template.getModified().getSeconds(), template.getModified().getNanos());
        }
        if (template.hasCreator()) {
            this.creator = new OpenCDXIdentifier(template.getCreator());
        }
        if (template.hasModifier()) {
            this.modifier = new OpenCDXIdentifier(template.getModifier());
        }
    }

    /**
     * Converts this model into an SMSTemplate
     * @return SMSTemplate of this model.
     */
    public SMSTemplate getProtobufMessage() {
        SMSTemplate.Builder builder = SMSTemplate.newBuilder();

        if (id != null) {
            builder.setTemplateId(this.id.toHexString());
        }
        if (templateType != null) {
            builder.setTemplateType(this.templateType);
        }
        if (message != null) {
            builder.setMessage(this.message);
        }
        if (variables != null) {
            builder.addAllVariables(this.variables);
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
     * Updates the fields of the OpenCDXSMSTemplateModel object with the values from the provided
     * SMSTemplate object.
     *
     * @param template The SMSTemplate object containing the updated values.
     * @return The updated OpenCDXSMSTemplateModel object.
     */
    public OpenCDXSMSTemplateModel update(SMSTemplate template) {
        this.templateType = template.getTemplateType();
        this.message = template.getMessage();
        this.variables = new ArrayList<>(template.getVariablesList());
        return this;
    }
}
