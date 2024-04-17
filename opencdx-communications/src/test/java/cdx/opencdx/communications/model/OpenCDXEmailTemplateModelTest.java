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

import static org.junit.jupiter.api.Assertions.assertEquals;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.grpc.data.EmailTemplate;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXEmailTemplateModelTest {

    @Test
    void getProtobufMessage_1() {
        EmailTemplate emailTemplate = EmailTemplate.newBuilder(EmailTemplate.getDefaultInstance())
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .setCreated(Timestamp.getDefaultInstance())
                .setModified(Timestamp.getDefaultInstance())
                .setCreator(OpenCDXIdentifier.get().toHexString())
                .setModifier(OpenCDXIdentifier.get().toHexString())
                .build();

        OpenCDXEmailTemplateModel model = new OpenCDXEmailTemplateModel(emailTemplate);

        assertEquals(emailTemplate, model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        EmailTemplate emailTemplate = EmailTemplate.newBuilder(EmailTemplate.getDefaultInstance())
                .setTemplateId(OpenCDXIdentifier.get().toHexString())
                .build();

        OpenCDXEmailTemplateModel model = new OpenCDXEmailTemplateModel(emailTemplate);

        assertEquals(emailTemplate, model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {
        OpenCDXEmailTemplateModel model = new OpenCDXEmailTemplateModel();
        Assertions.assertDoesNotThrow(() -> model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_4() {
        EmailTemplate emailTemplate =
                EmailTemplate.newBuilder(EmailTemplate.getDefaultInstance()).build();

        OpenCDXEmailTemplateModel model = new OpenCDXEmailTemplateModel(emailTemplate);

        assertEquals(emailTemplate, model.getProtobufMessage());
    }
}
