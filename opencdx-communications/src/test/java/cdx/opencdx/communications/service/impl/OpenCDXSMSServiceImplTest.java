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
package cdx.opencdx.communications.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenCDXSMSServiceImplTest {

    OpenCDXSMSService openCDXSMSService;

    @Test
    void sendSMS() {
        String message = "message";
        List<String> phoneNumbers = new ArrayList<>(List.of("1", "2"));
        this.openCDXSMSService = new OpenCDXSMSServiceImpl();
        Assertions.assertTrue(openCDXSMSService.sendSMS(message, phoneNumbers));
    }
}
