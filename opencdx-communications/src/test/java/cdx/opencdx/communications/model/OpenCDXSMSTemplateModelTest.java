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

import static org.junit.jupiter.api.Assertions.*;

import cdx.open_communication.v2alpha.SMSTemplate;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class OpenCDXSMSTemplateModelTest {

    @Test
    void getProtobufMessage_1() {
        SMSTemplate smsTemplate = SMSTemplate.newBuilder(SMSTemplate.getDefaultInstance())
                .setTemplateId(ObjectId.get().toHexString())
                .build();

        OpenCDXSMSTemplateModel model = new OpenCDXSMSTemplateModel(smsTemplate);

        assertEquals(smsTemplate, model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_2() {
        SMSTemplate smsTemplate = SMSTemplate.newBuilder(SMSTemplate.getDefaultInstance())
                .setTemplateId(ObjectId.get().toHexString())
                .build();

        OpenCDXSMSTemplateModel model = new OpenCDXSMSTemplateModel(smsTemplate);
        log.info(model.toString());
        assertEquals(smsTemplate, model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {
        OpenCDXSMSTemplateModel model = new OpenCDXSMSTemplateModel();
        Assertions.assertDoesNotThrow(() -> model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_4() {
        SMSTemplate smsTemplate =
                SMSTemplate.newBuilder(SMSTemplate.getDefaultInstance()).build();

        OpenCDXSMSTemplateModel model = new OpenCDXSMSTemplateModel(smsTemplate);
        log.info(model.toString());
        assertEquals(smsTemplate, model.getProtobufMessage());
    }
}
