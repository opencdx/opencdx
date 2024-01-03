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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.config.CommonsConfig;
import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"noop", "test"})
@SpringBootTest(classes = {CommonsConfig.class, ObservationAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
class NoOpOpenCDXMessageServiceImplTest {

    @Autowired
    OpenCDXMessageService openCDXMessageService;

    @Test
    void subscribe() {
        Assertions.assertDoesNotThrow(
                () -> this.openCDXMessageService.subscribe("SUBSCRIBE-TEST", new OpenCDXMessageHandler() {
                    @Override
                    public void receivedMessage(byte[] message) {}
                }));
    }

    @Test
    void unSubscribe() {
        Assertions.assertDoesNotThrow(() -> this.openCDXMessageService.unSubscribe("UN-SUBSCRIBE-TEST"));
    }

    @Test
    void send() {
        Assertions.assertDoesNotThrow(() -> this.openCDXMessageService.send("SEND-TEST", "TEST-DATA"));
    }
}
