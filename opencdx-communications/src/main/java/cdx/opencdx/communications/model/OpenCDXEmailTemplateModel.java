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
package cdx.opencdx.communications.model;

import cdx.opencdx.grpc.communication.*;
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
 * Model for OpecCDXEmailTemplate in Mongo.  Features conversions
 * to Protobuf messages.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("email-template")
public class OpenCDXEmailTemplateModel {

    @Id
    private ObjectId id;

    private TemplateType templateType;
    private String subject;
    private String content;
    private List<String> variables;

    /**
     * Constructor to create this model based on an EmailTemplate
     * @param template EmailTemplate to base this model on.
     */
    public OpenCDXEmailTemplateModel(EmailTemplate template) {

        if (template.hasTemplateId()) {
            this.id = new ObjectId(template.getTemplateId());
        }
        this.templateType = template.getTemplateType();
        this.subject = template.getSubject();
        this.content = template.getContent();

        this.variables = new ArrayList<>(template.getVariablesList());
    }

    /**
     * Return this model as an EmailTemplate
     * @return EmailTemplate representing this model.
     */
    public EmailTemplate getProtobufMessage() {
        EmailTemplate.Builder builder = EmailTemplate.newBuilder();
        if (id != null) {
            builder.setTemplateId(this.id.toHexString());
        }
        if (templateType != null) {
            builder.setTemplateType(this.templateType);
        }
        if (subject != null) {
            builder.setSubject(this.subject);
        }
        if (content != null) {
            builder.setContent(this.content);
        }
        if (variables != null) {
            builder.addAllVariables(this.variables);
        }

        return builder.build();
    }
}
